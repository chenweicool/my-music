package com.mymusic;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.mymusic.mapper","com.mymusic.jwt.mapper"})
public class MymusicApplication {
    public static void main(String[] args) {
        SpringApplication.run(MymusicApplication.class, args);
    }
}
