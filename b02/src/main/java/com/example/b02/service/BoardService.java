package com.example.b02.service;

import com.example.b02.dto.BoardDTO;
import com.example.b02.dto.PageRequestDTO;
import com.example.b02.dto.PageResponseDTO;

public interface BoardService {
    Long register(BoardDTO boardDTO);
    BoardDTO readOne(Long bno);
    void modify(BoardDTO boardDTO);
    void remove(Long bno);
    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);
}
