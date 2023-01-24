package com.yj.mini.service.impl;

import com.yj.mini.data.dto.BoardDto;
import com.yj.mini.data.dto.BoardResponseDto;
import com.yj.mini.data.entity.Board;
import com.yj.mini.data.entity.SiteUser;
import com.yj.mini.data.repository.BoardRepository;
import com.yj.mini.service.BoardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }

    @Override
    public BoardResponseDto getBoard(Long id) {
        Board board = boardRepository.getById(id);

        BoardResponseDto boardResponseDto = new BoardResponseDto();
        boardResponseDto.setId(board.getId());
        boardResponseDto.setTitle(board.getTitle());
        boardResponseDto.setDescription(board.getDescription());
        boardResponseDto.setAuthor(board.getAuthor());
        return boardResponseDto;
    }

    @Override
    public BoardResponseDto saveBoard(BoardDto boardDto, SiteUser author) {
        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setDescription(boardDto.getDescription());
        board.setAuthor(author);
        Board savedBoard = boardRepository.save(board);

        BoardResponseDto boardResponseDto = new BoardResponseDto();
        boardResponseDto.setId(savedBoard.getId());
        boardResponseDto.setTitle(savedBoard.getTitle());
        boardResponseDto.setDescription(savedBoard.getDescription());
        return boardResponseDto;
    }

    @Override
    public BoardResponseDto updateBoard(Long id, String title, String description) throws Exception {
        return null;
    }

    @Override
    public void deleteBoard(Long id) throws Exception {
        boardRepository.deleteById(id);
    }
}
