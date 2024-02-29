package com.example.b02.repository;

import com.example.b02.domain.Board;
import com.example.b02.domain.Reply;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;
    @Test
    public void testInsert(){
        Long bno = 101L;
        Board board = Board.builder().bno(bno).build();

        Reply reply = Reply.builder()
                .board(board)
                .replyText("댓글 test ...........")
                .replyer("replyer1")
                .build();

        replyRepository.save(reply);
    }

    @Test
//    @Transactional
    public void testBoardReplies(){
        Long bno = 100L;
        Pageable pageable = PageRequest.of(0,10, Sort.by("rno").descending());
        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);

        result.getContent().forEach(reply -> {
            log.info(reply);
//            log.info(reply.getBoard().getContent());
        });

    }

}
