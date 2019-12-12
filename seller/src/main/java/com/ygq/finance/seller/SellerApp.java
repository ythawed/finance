package com.ygq.finance.seller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ythawed
 * @date 2019/12/6 0006
 *
 * 1. 不连接数据库启动exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class}
 * 这样可以不连接数据库（前提是不需要数据库数据），而启动该工程。如果需要连接数据库，需要删除该属性
 * 2. 开启缓存， 需要在application.yml中指定缓存类型
 * 3. 这样对相应的方法加上@cache就可以对结果加入缓存
 * 4. 打开定时任务，本例为定时执行对账
 */
@SpringBootApplication
@EnableCaching
@EntityScan(basePackages = "com.ygq.finance.entity")
@EnableScheduling
public class SellerApp {

    public static void main(String[] args) {
        SpringApplication.run(SellerApp.class);
    }

}
