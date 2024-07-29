package com.example.b02.service;

import com.example.b02.dto.ReplyDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {
    @Autowired
    private ReplyService replyService;

    @Test
    public void testRegister(){

        ReplyDTO replyDTO = ReplyDTO.builder()
                .replyText("reply dto test")
                .replyer("replyer")
                .bno(100L)
                .build();
        log.info(replyService.register(replyDTO));
    }
}
