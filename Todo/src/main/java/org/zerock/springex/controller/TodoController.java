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
import org.zerock.springex.dto.TodoDTO;
import org.zerock.springex.service.TodoService;

import javax.validation.Valid;

@Controller
@RequestMapping("/todo")
@Log4j2
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @RequestMapping("/list")
    public void list(Model model){
        log.info("todo list,,,,,,,,");
        // model 을 jsp 까지 전달
        model.addAttribute("dtoList",todoService.getAll());
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


}
