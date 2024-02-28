package com.example.b02.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// 기존 spring mvc 그대로 이용
@Controller
@Log4j2
public class SampleController {
    @Operation(summary = "hello")
    @GetMapping("/hello")
    public void hello(Model model){
        log.info("hello..........................");
        model.addAttribute("msg", "Hello world");
    }
    @Operation(summary = "/ex/ex1")
    @GetMapping("/ex/ex1")
    public void ex1(Model model) {
        List<String> list = Arrays.asList("AAA", "BBB", "CCC","DDD");
        model.addAttribute("list", list);
    }

    /** 인라인 처리 */
    class SampleDTO{
        private String p1,p2,p3;

        public String getP1() {
            return p1;
        }

        public String getP2() {
            return p2;
        }

        public String getP3() {
            return p3;
        }
    }
    @GetMapping("/ex/ex2")
    public void ex2(Model model){
        log.info("ex/ex2 ...........................");
        List<String> strList = IntStream.range(1,10)
                .mapToObj(i -> "Data" + i)
                .collect(Collectors.toList());
        model.addAttribute("list", strList);

        Map<String, String> map = new HashMap<>();
        map.put("A", "AAAA");
        map.put("B", "BBBB");
        model.addAttribute("map",map);

        SampleDTO sampleDTO = new SampleDTO();
        sampleDTO.p1 = "Value -- p1";
        sampleDTO.p2 = "Value -- p2";
        sampleDTO.p3 = "Value -- p3";
        model.addAttribute("dto", sampleDTO);
    }

    /** thymeleaf layout */
    @GetMapping("/ex/ex3")
    public void ex3(Model model){
        model.addAttribute("arr", new String[]{"AAA","BBB","CCC"});
    }
}
