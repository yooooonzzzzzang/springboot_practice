package com.example.b02.repository;

import com.example.b02.domain.Board;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {
    @Autowired
    private BoardRepository boardRepository;
    @Test
    public void testInsert(){
        IntStream.rangeClosed(1,100).forEach(i -> {
            Board board = Board.builder()
                    .title("title..."  + i)
                    .content("content..." + i)
                    .writer("user" + (i % 10))
                    .build();

            Board result = boardRepository.save(board);
            log.info("BNO: " + result.getBno());
        });
    }

    @Test
    public void testSelect(){
        Long bno = 100L;
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        log.info(board);
    }

    @Test
    public void testUpdate(){
        Long bno = 100L;
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        board.change("changed title", "changed content");
        boardRepository.save(board);
    }

    @Test
    public void testDelete(){
        Long bno = 1L;
        boardRepository.deleteById(bno);
    }
    
    @Test
    public void testPaging(){
//      1 page order by bno desc
        Pageable bno = PageRequest.of(3, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.findAll(bno);

        log.info("total count: " + result.getTotalElements());
        log.info("total pages: " + result.getTotalPages());
        log.info("total number: " + result.getNumber());
        log.info("total size: " + result.getSize());

        List<Board> todoList = result.getContent();
        todoList.forEach(board -> log.info(board));
    }

//   querydsl test
    @Test
    public void testSearch1(){
//        2 page order by bno desc
        Pageable bno = PageRequest.of(3, 10, Sort.by("bno").descending());
        boardRepository.search1(bno);
    }
}
