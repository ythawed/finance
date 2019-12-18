package com.ygq.finance.seller.configuration;

import com.ygq.finance.entity.Order;
import com.ygq.finance.seller.repositiorybackup.VerificationOrderRepository;
import com.ygq.finance.seller.repository.OrderRepository;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.SchemaManagement;
import org.springframework.boot.jdbc.SchemaManagementProvider;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据源配置类
 *
 * @author ythawed
 * @date 2019/12/15 0015
 */
@Configuration
public class DataAccessConfiguration {

    @Autowired
    private JpaProperties properties;
    @Autowired
    private ObjectProvider<List<SchemaManagementProvider>> providers;
    @Autowired
    private ObjectProvider<PhysicalNamingStrategy> physicalNamingStrategy;
    @Autowired
    private ObjectProvider<ImplicitNamingStrategy> implicitNamingStrategy;

    /**
     * 1. 配置数据库
     * <p>
     * 1.1配置主库
     *
     * @see ../application.yml
     * spring.datasource.primary就是主库配置的名称
     * primaryDataSource 自定义方法名称
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 1.2配置从库
     */
    @Bean
    @ConfigurationProperties("spring.datasource.backup")
    public DataSource backupDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 2. 配置Entity管理工厂
     * <p>
     * 2.1 主库Entity
     */
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("primaryDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages(Order.class)
                .properties(getVendorProperties(dataSource))
                .persistenceUnit("primary")
                .build();
    }

    /**
     * 2.1 备份库Entity
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean backupEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("backupDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages(Order.class)
                .properties(getVendorProperties(dataSource))
                .persistenceUnit("backup")
                .build();
    }

    /**
     * 3. 事务管理配置
     * 3.1 主库事务
     */
    @Bean
    public PlatformTransactionManager primaryTransactionManager(@Qualifier("primaryEntityManagerFactory") LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(primaryEntityManagerFactory.getObject());
        return transactionManager;
    }
    @Bean
    public PlatformTransactionManager backupTransactionManager(@Qualifier("backupEntityManagerFactory") LocalContainerEntityManagerFactoryBean backupEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(backupEntityManagerFactory.getObject());
        return transactionManager;
    }

    @EnableJpaRepositories(basePackageClasses = OrderRepository.class,
            entityManagerFactoryRef = "primaryEntityManagerFactory",transactionManagerRef = "primaryTransactionManager")
    @Primary
    public class PrimaryConfiguration {
    }

    @EnableJpaRepositories(basePackageClasses = VerificationOrderRepository.class,
            entityManagerFactoryRef = "backupEntityManagerFactory",transactionManagerRef = "backupTransactionManager")
    public class BackupConfiguration {
    }

    protected Map<String, Object> getVendorProperties(DataSource dataSource) {
        Map<String, Object> vendorProperties = new LinkedHashMap<>();
        String defaultDdlMode = new HibernateDefaultDdlAutoProvider(providers.getIfAvailable(Collections::emptyList)).getDefaultDdlAuto(dataSource);
        // FIXME defaultDdlMode报错，类型错误，导致工程编译错误
//        vendorProperties.putAll(
//                properties.getProperties(
//                        new HibernateSettings().ddlAuto(defaultDdlMode).physicalNamingStrategy(physicalNamingStrategy.getIfAvailable())
//                .implicitNamingStrategy(implicitNamingStrategy.getIfAvailable()))
//        );

        return vendorProperties;
    }

    class HibernateDefaultDdlAutoProvider implements SchemaManagementProvider {

        private final List<SchemaManagementProvider> providers;

        HibernateDefaultDdlAutoProvider(List<SchemaManagementProvider> providers) {
            this.providers = providers;
        }

        public String getDefaultDdlAuto(DataSource dataSource) {
            if (!EmbeddedDatabaseConnection.isEmbedded(dataSource)) {
                return "none";
            }
            SchemaManagement schemaManagement = getSchemaManagement(dataSource);
            if (SchemaManagement.MANAGED.equals(schemaManagement)) {
                return "none";
            }
            return "create-drop";

        }

        @Override
        public SchemaManagement getSchemaManagement(DataSource dataSource) {
            for (SchemaManagementProvider provider : this.providers) {
                SchemaManagement schemaManagement = provider.getSchemaManagement(dataSource);
                if (SchemaManagement.MANAGED.equals(schemaManagement)) {
                    return schemaManagement;
                }
            }
            return SchemaManagement.UNMANAGED;
        }

    }


}
