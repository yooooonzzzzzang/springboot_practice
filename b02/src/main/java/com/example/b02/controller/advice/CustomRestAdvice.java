package com.example.b02.controller.advice;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class CustomRestAdvice {
    @ExceptionHandler(BindException.class) //데이터 바인딩이 실패할 때 발생하는 예외처리
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED) // 어노테이션은 해당 예외가 발생했을 때 클라이언트에 반환될 HTTP 응답 상태
    public ResponseEntity<Map<String, String>> handleBindException(BindException e){
        log.error(e);
        HashMap<String, String> erroMap = new HashMap<>();
        if(e.hasErrors()){
            BindingResult bindingResult = e.getBindingResult();

            bindingResult.getFieldErrors().forEach(fieldError -> {
                erroMap.put(fieldError.getField(), fieldError.getCode());
            });
        }
    return ResponseEntity.badRequest().body(erroMap); //HTTP 상태 코드 400 (Bad Request)와 함께 에러 맵을 반환
    }
}
