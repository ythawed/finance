package com.ygq.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author ythawed
 * @date 2019/12/3
 */
@SpringBootApplication
@EntityScan(basePackages = "com.ygq.finance.entity")
public class ManagerApp {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApp.class, args);
    }
}
