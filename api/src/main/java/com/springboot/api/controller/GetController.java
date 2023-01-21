package com.springboot.api.controller;

import com.springboot.api.dto.MemberDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/get-api")
public class GetController {
    private final Logger LOGGER = LoggerFactory.getLogger(GetController.class);

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String getHello(){
        LOGGER.info("getHello 메서드가 호출되었습니다.");
        return "Hello World";
    }
//  매개변수가 없는 GET 메서드
    @GetMapping(value = "/name")
    public String getName(){
        LOGGER.info("getName 메서드가 호출되었습니다.");
        return "Flature";
    }
//   @PathVariable 을 활용한 GET 메서드
    @GetMapping(value = "/variable1/{variable}")
    public String getVariable(@PathVariable String variable) {
        LOGGER.info("@PathVariable을 통해 들어온 값: {}", variable);
        return variable;
    }

    @GetMapping(value = "/variable2/{variable}")
    public String getVariable2(@PathVariable("variable") String var){
        return var;
    }

    //스웨거 추가
    @ApiOperation(value = "Get 메서드 예제", notes="@RequestParam을 이용한 Get Method")
    @GetMapping(value = "/request1")
    public String getRequestParam1(
            @ApiParam(value = "이름", required=true)@RequestParam String name,
            @ApiParam(value = "이메일", required=true)@RequestParam String email,
            @ApiParam(value = "회사", required=true)@RequestParam String organization){
        return name + " " + email + " " + organization;
    }

//    쿼리스트링에 어떤 값이 들어올지 모를떄 map 객체
    @GetMapping(value = "/request2")
    public String getRequestParam2(@RequestParam Map<String, String> param){
        StringBuilder sb = new StringBuilder();
        param.entrySet().forEach(map -> {
            sb.append(map.getKey() + ":" + map.getValue() + "\n");
        });
        return  sb.toString();
    }

    @GetMapping(value = "/request3")
    public String getRequestParam3(MemberDto memberDto){
        return memberDto.toString();
    }


}
