package com.rahat.blog.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Test {

    @GetMapping
    String index(){
        return "this is an index";
    }
    @GetMapping(path = "/show")
    String show(){
        return "this is a test";
    }
}
