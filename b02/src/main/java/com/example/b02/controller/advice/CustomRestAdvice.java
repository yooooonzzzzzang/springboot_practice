package com.example.b02.controller.advice;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

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

    // 없는 bno 호출
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>> handleFKException(Exception e){
        log.error(e);

        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("time",""+System.currentTimeMillis());
        errorMap.put("msg", "constraint fails");
        return ResponseEntity.badRequest().body(errorMap);
    }
/**
 * NoSuchElementException은 주로 자바의 기본 데이터 구조나 Optional 클래스와 같은 표준 라이브러리에서 사용됩니다.
 * EmptyResultDataAccessException은 스프링 프레임워크의 데이터 접근 계층에서 발생하며, 주로 데이터베이스 관련 작업에서 나타납니다.
 * */
    @ExceptionHandler({NoSuchElementException.class, EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>> handleNoSuchElement(Exception e){
        log.error(e);

        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("time",""+System.currentTimeMillis());
        errorMap.put("msg", "constraint fails");
        return ResponseEntity.badRequest().body(errorMap);
    }



}
