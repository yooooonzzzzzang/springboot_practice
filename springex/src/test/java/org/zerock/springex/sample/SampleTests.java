package org.zerock.springex.sample;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Log4j2
// JUnit5 버전에서 spring-test 를 이용하기 위함, 4버전은 @Runwith
@ExtendWith(SpringExtension.class)
// 스프링의 설정 정보를 로딩, xml 일 경우 locations, 자바설정일경우 classes 속성
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
public class SampleTests {
    @Autowired // 스프링에서 사용하는 의존성 주입관련 어노테이션, 해당 타입의 빈이 존재하면 여기에 주입해주기를 원한다
    private SampleService sampleService;

    @Test
    public void testService1(){
        log.info(sampleService);
        Assertions.assertNotNull(sampleService);
    }

}
