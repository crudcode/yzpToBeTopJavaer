package com.yzp.cdp.继承多种实现方式;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @GetMapping("hello")
    public String hello(){
        return "hello";
    }
}
