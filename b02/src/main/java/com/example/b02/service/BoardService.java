package com.example.b02.service;

import com.example.b02.dto.BoardDTO;
import com.example.b02.dto.BoardListReplyCountDTO;
import com.example.b02.dto.PageRequestDTO;
import com.example.b02.dto.PageResponseDTO;

public interface BoardService {
    Long register(BoardDTO boardDTO);
    BoardDTO readOne(Long bno);
    void modify(BoardDTO boardDTO);
    void remove(Long bno);
    // 구버전 list
    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);
    // 신버전 list , 댓글 숫자까지 처리
    PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);

}
