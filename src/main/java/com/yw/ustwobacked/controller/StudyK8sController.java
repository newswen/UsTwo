package com.yw.ustwobacked.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author: yw
 * @Date: 2025/4/22 11:32
 * @Description:
 **/
@RestController
@CrossOrigin("*")
@RequestMapping("/api")
@Slf4j
public class StudyK8sController {

    @GetMapping("/studyK8s")
    public String studyK8s(){
        String host = "unknown";
        String dbUrl = System.getenv("DB_URL");  // 获取环境变量 DB_URL
        try {
            host = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.error("测试k8s有问题",e);
        }
        return String.format("[v4] Hello, Kubernetes! From host: %s, Get Database Connect URL: %s", host, dbUrl);
    }
}
