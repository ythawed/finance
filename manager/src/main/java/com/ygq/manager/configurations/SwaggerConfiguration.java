package com.ygq.manager.configurations;

import com.ygq.manager.controller.ProductController;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author ythawed
 * @date 2019/12/3 0003
 *
 * swagger 使用配置类,失效。使用swagger工程，然后再MainApplication启动类调用SwaggerConfiguration配置类
 *
 */
//@Configuration
//@EnableSwagger2
public class SwaggerConfiguration {


    /**
     * 分组测试
     * 本组是controller组
     *
     * 每个组只需创建一个Bean即可
     *
     */
    @Bean
    public Docket controllerApo() {
        return new Docket(DocumentationType.SWAGGER_2)
                //组名
                .groupName("controller")
                .apiInfo(apiInfo())
                // 测试方法选择  :根据类名
                .select().apis(RequestHandlerSelectors.basePackage(ProductController.class.getPackage().getName()))
                // 根据访问路径
//                .paths(PathSelectors.ant("/products/*"))
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("HTTP API")
                .description("管理端接口")
                .termsOfServiceUrl("http://springfox.io")
                .contact("ygq")
                .license("Apache License Version 2.0")
                .build();
    }
}
