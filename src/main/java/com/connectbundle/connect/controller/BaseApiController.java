package com.connectbundle.connect.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public abstract class BaseApiController{
    @RequestMapping("/test")
    public String test(){
        return "Server is OK!!!";
    }
}