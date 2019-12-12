# 1 配置法，启动SwaggerConfiguration
- 1. SwaggerInfo需要作为属性注入，所以需要加@Component注解以被SwaggerConfiguration类扫描
- 2. SwaggerConfiguration需要扫描到SwaggerInfo
- 3. 在MainApplication中导入(@Import(SwaggerConfiguration.class))该配置
# 2 自定义注解类EnableMySwagger法，启动SwaggerConfiguration
- @EnableMySwagger
- public class MainApplication {    }
# 3 覆盖spring源码
- 在resources中添加META-INF/resources/spring.factories
- 添加映射org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.ygq.swagger.SwaggerConfiguration
- 将SwaggerInfo类注解@ConfigurationProperties(prefix = "swagger")
- 使得可以在manager工程中的application.yml的中动态配置Swagger文档中的属性