package com.yw.ustwobacked;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class UsTwoBackedApplication {

    public static void main(String[] args) {
        log.info("启动成功，机器时间：{}", System.currentTimeMillis());
        SpringApplication.run(UsTwoBackedApplication.class, args);
    }
}
