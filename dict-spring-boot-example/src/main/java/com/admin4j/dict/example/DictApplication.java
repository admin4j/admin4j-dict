package com.admin4j.dict.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author andanyang
 * @since 2022/10/26 10:14
 */
@SpringBootApplication
@MapperScan("com.admin4j.dict.example.mapper")
public class DictApplication {
    public static void main(String[] args) {

        SpringApplication.run(DictApplication.class, args);
    }
}
