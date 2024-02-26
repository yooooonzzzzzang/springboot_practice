package com.example.b02.controller;

import com.example.b02.dto.BoardDTO;
import com.example.b02.dto.PageRequestDTO;
import com.example.b02.dto.PageResponseDTO;
import com.example.b02.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        log.info(responseDTO);
        model.addAttribute("responseDTO", responseDTO);
    }

    @GetMapping("/register")
    public void registerGET(){

    }

    @PostMapping("/register")
    public String registerPOST(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        log.info("board POST register ...... ");

        if(bindingResult.hasErrors()){
            log.info("has errors ....... ");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/board/register";
        }

        log.info(boardDTO);
        Long bno = boardService.register(boardDTO);
        redirectAttributes.addFlashAttribute("result", bno);
        return "redirect:/board/list";
    }


    @GetMapping({"/read", "/modify"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model){
        BoardDTO boardDTO = boardService.readOne(bno);
        log.info(boardDTO);
        model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO,
                         @Valid BoardDTO boardDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        log.info("board modify post .........." +boardDTO);

        if(bindingResult.hasErrors()){
            log.info("has errors ....... ");
            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("bno", boardDTO.getBno());
//            기존의 모든 조건을 원래대로 붙여서 이동가능
            return "redirect:/board/modify?"+link;
        }

        boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("bno", boardDTO.getBno());
        // 수정완료시 검색, 페이징 조건 없이 이ㅖ
        return "redirect:/board/read";

    }

    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes){
        log.info("remove post .... " + bno);
        boardService.remove(bno);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/board/list";
    }
}
