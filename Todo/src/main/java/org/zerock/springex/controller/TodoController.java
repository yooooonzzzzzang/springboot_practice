package org.zerock.springex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.springex.dto.PageRequestDTO;
import org.zerock.springex.dto.TodoDTO;
import org.zerock.springex.service.TodoService;

import javax.validation.Valid;

@Controller
@RequestMapping("/todo")
@Log4j2
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

//    @RequestMapping("/list")
//    public void list(Model model){
//        log.info("todo list,,,,,,,,");
//        // model 을 jsp 까지 전달
//        model.addAttribute("dtoList",todoService.getAll());
//    }
    @GetMapping("/list")
    public void list(@Valid PageRequestDTO pageRequestDTO, BindingResult bindingResult, Model model){
        log.info(pageRequestDTO);
        if(bindingResult.hasErrors()){ // 잘못된 값이 들어왔을때 default 로 처리
            pageRequestDTO = pageRequestDTO.builder().build();
        }
        model.addAttribute("responseDTO", todoService.getList(pageRequestDTO));
    }

//    @RequestMapping(value = "/register", method = RequestMethod.GET)
//    public void register(){
//        log.info("todo register ..... ");
//    }
    @GetMapping("/register")
    public void  registerGET(){
        log.info("GET todo register ...... ");
    }

    @PostMapping("/register")
    public String register(@Valid TodoDTO todoDTO,
                            BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){ //post 처리 후 이동
        log.info("todo register ..... ");

        if(bindingResult.hasErrors()){
            log.info("has error. ......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/todo/register";
        }
        log.info(todoDTO);

        todoService.register(todoDTO);

        return "redirect:/todo/list";
    }

    @GetMapping({"/read","/modify"})
    public void read(Long tno, PageRequestDTO pageRequestDTO, Model model){
        TodoDTO todoDTO = todoService.getOne(tno);
        log.info(todoDTO);
        model.addAttribute("dto", todoDTO);
    }


    @PostMapping("/remove")
    public String remove(Long tno, PageRequestDTO pageRequestDTO,RedirectAttributes redirectAttributes){
        log.info("----------remove-----------");
        log.info("tno: ", tno);

        todoService.remove(tno);
        // 삭제 후 1페이지로 이동
//        redirectAttributes.addAttribute("page", 1);
//        redirectAttributes.addAttribute("size",pageRequestDTO.getSize());
        return "redirect:/todo/list"+ pageRequestDTO.getLink();
    }

    @PostMapping("/modify")
    public String modify(@Valid TodoDTO todoDTO,PageRequestDTO pageRequestDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            log.info("has error. ......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("tno", todoDTO.getTno());
            return "redirect:/todo/modify";
        }
        log.info(todoDTO);
        todoService.modify(todoDTO);
        // 수정 후 목록 이동
//        redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
//        redirectAttributes.addAttribute("size",pageRequestDTO.getSize());
        redirectAttributes.addAttribute("tno", todoDTO.getTno());
        return "redirect:/todo/list";
    }
}
