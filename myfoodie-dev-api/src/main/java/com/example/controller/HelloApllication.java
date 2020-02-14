package com.example.controller;

import com.example.pojo.Stu;
import com.example.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class HelloApllication {

    @Autowired
    private StuService stuService;

    @GetMapping("/hello")
    public String Hello() {
        return "hello";
    }

    @GetMapping("/getstu")
    public Stu getStu(@RequestParam int id) {
        return stuService.getStuById(id);
    }

}