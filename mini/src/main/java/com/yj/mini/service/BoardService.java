package com.yj.mini.service;

import com.yj.mini.data.dto.BoardDto;
import com.yj.mini.data.dto.BoardResponseDto;
import com.yj.mini.data.entity.Board;
import com.yj.mini.data.entity.SiteUser;

import java.util.List;

public interface BoardService {
    List<Board> getBoardList();
    // 게시글 조회
    BoardResponseDto getBoard(Long id);
    // 게시글 저장
    BoardResponseDto saveBoard(BoardDto boardDto, SiteUser author);
    // 게시글 수정
    BoardResponseDto updateBoard(Long id, String title, String description) throws Exception;
    // 게시글 삭제
    void deleteBoard(Long id) throws Exception;
}
