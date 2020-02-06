package com.zhanghf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@Slf4j
@EnableScheduling
@SpringBootApplication
@MapperScan("com.zhanghf.mapper")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        System.out.println("server is start...");
    }
}
