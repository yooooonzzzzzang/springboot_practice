package org.zerock.springex.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.springex.dto.TodoDTO;

import java.time.LocalDate;

@Log4j2
@ExtendWith(SpringExtension.class) // JUnit5 버전에서 spring-test 를 이용하기 위함, 4버전은 @Runwith
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml") // 스프링의 설정 정보를 로딩, xml 일 경우 locations, 자바설정일경우 classes 속성
public class TodoServiceTest {
    // 스프링에서 사용하는 의존성 주입관련 어노테이션, 해당 타입의 빈이 존재하면 여기에 주입해주기를 원한다
    @Autowired  // required false : 해당 객체를 주입 받지 못해도 예외 발생 안함
    private TodoService todoService;

    @Test
    public void testRegister(){
        TodoDTO todoDTO = TodoDTO.builder()
                        .title("TEST,,,,")
                                .dueDate(LocalDate.now())
                                        .writer("user1")
                                                .build();
        todoService.register(todoDTO);
    }
}
