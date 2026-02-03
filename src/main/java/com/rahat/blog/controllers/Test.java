package com.rahat.blog.controllers;

import org.springframework.web.bind.annotation.GetMapping;
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
