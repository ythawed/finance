package com.ygq.swagger;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ythawed
 * @date 2019/12/4 0004
 * 注解法启动SwaggerConfiguration类,定义本类好，在MainApplication中使用该注解即可
 * 此注解和SwaggerConfiguration只需要一方使用@EnableSwagger2即可
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Documented
@Import({SwaggerConfiguration.class})
public @interface EnableMySwagger {
}
