package com.yw.ustwobacked.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yw
 * @Date: 2025/4/22 11:32
 * @Description:
 **/
@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class StudyK8sController {

    @GetMapping("/studyK8s")
    public String studyK8s(){
        return "[v1]studyK8s";
    }
}
