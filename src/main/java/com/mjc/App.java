package com.mjc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 */
//开启spring 的自动化配置主要处理依赖
@SpringBootApplication(scanBasePackages = "com.mjc")
@RestController
//扫描dao
@MapperScan("com.mjc.dao")
public class App {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        SpringApplication.run(App.class);
    }
}
