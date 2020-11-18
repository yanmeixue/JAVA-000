package pers.ryan.spring.controller;

import org.springframework.stereotype.Controller;
import pers.ryan.spring.service.MyService;

import javax.annotation.Resource;

@Controller
public class MyController {
    @Resource
    private MyService myService;

    public void print() {
        myService.print();
    }
}
