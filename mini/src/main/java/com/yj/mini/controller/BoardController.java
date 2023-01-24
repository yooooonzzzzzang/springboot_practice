package com.yj.mini.controller;

import com.yj.mini.data.dto.BoardDto;
import com.yj.mini.data.dto.BoardResponseDto;
import com.yj.mini.data.entity.Board;
import com.yj.mini.data.entity.SiteUser;
import com.yj.mini.service.BoardService;
import com.yj.mini.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;

    @Autowired
    public BoardController(BoardService boardService, UserService userService) {
        this.boardService = boardService;
        this.userService = userService;
    }
    //게시판 목록 보기
    @GetMapping()
    public ResponseEntity<?> list(Model model){
        List<Board> list = boardService.getBoardList();
        model.addAttribute("boardList", list);
        return new ResponseEntity(model,HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<BoardResponseDto>getBoard(Long id){
        BoardResponseDto boardResponseDto = boardService.getBoard(id);
        return ResponseEntity.status(HttpStatus.OK).body(boardResponseDto);
    }

    @PostMapping()
    public ResponseEntity<BoardResponseDto>createBoard(@RequestBody BoardDto boardDto, Principal principal) throws Exception {
        SiteUser siteUser = null;
        try {
            siteUser = userService.getUser(principal.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        BoardResponseDto boardResponseDto = boardService.saveBoard(boardDto, siteUser);
        return ResponseEntity.status(HttpStatus.OK).body(boardResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteBoard(@PathVariable("id") Long id) throws Exception{
        boardService.deleteBoard(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("삭제 완료!");
    }

}
