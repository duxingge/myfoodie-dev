package com.example.controller;

import com.example.pojo.Stu;
import com.example.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloApllication {

    @Autowired
    private StuService stuService;

    @GetMapping("/hello")
    public String Hello() {
        return "hello";
    }

    @GetMapping("/getstu")
    public Stu getStu(int id) {
        return stuService.getStuById(id);
    }

}