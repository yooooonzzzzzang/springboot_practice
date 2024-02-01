package com.example.b02.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// 기존 spring mvc 그대로 이용
@Controller
@Log4j2
public class Samplecontroller {
    @GetMapping("/hello")
    public void hello(Model model){
        log.info("hello..........................");
        model.addAttribute("msg", "Hello world");
    }
}
