package com.inet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * @author HCY
 * @since 2020-10-10
 */
@SpringBootApplication
@MapperScan(basePackages = "com.inet.codebase.mapper")
public class HcyApplication {

    public static void main(String[] args) {
        SpringApplication.run(HcyApplication.class, args);
    }

}
