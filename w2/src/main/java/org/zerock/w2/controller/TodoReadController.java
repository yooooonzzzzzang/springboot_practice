package org.zerock.w2.controller;

import lombok.extern.log4j.Log4j2;
import org.zerock.w2.dto.TodoDTO;
import org.zerock.w2.service.TodoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "todoReadController", value = "/todo/read")
@Log4j2
public class TodoReadController extends HttpServlet {
    TodoService todoService = TodoService.INSTANCE;
    /*
    * viewTodos 이름의 쿠키를 찾고, 쿠키의 내용물 검사 -> 조회한적이 없는 번호라면 쿠키의 내용물 갱신해서 브라우저로 보내기
    * */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long tno = Long.parseLong(req.getParameter("tno"));
            TodoDTO todoDTO = todoService.get(tno);
            // 모델 담기
            req.setAttribute("dto", todoDTO);
            // 쿠키 찾기
            Cookie viewTodoCookie = findCookie(req.getCookies(), "viewTodos");
            String todoListStr = viewTodoCookie.getValue();
            boolean exist = false;

            if (todoListStr != null && todoListStr.indexOf(tno+"-") >= 0){
                exist = true;
            }
            log.info("exist: " + exist);
            // 없으면 todocookie 생성
            if (!exist) {
                todoListStr += tno + "-";
                viewTodoCookie.setValue(todoListStr);
                viewTodoCookie.setMaxAge(60*60*24);
                viewTodoCookie.setPath("/");  //래의 모든 경로에서 이 쿠키를 사용할 수 있게 됩니다.
                resp.addCookie(viewTodoCookie);
            }

            req.getRequestDispatcher("/WEB-INF/todo/read.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new ServletException("read Error");
        }

    }

    private Cookie findCookie(Cookie[] cookies, String cookieName) {
        Cookie targetCookie = null;
        if (cookies != null && cookies.length > 0) {
            for (Cookie ck : cookies) {
                if (ck.getName().equals(cookieName)) {
                    targetCookie = ck;
                    break;
                }
            }
        }
        // 쿠키가 없다면
        if (targetCookie == null){
            // Cookie 클래스 생성자에 두 개의 인자를 넣는 것은 일반적인 경우이며, 쿠키의 이름과 값을 설정하기 위해 사용됩니다.
            // 쿠키의 이름과 값을 지정하여 쿠키를 생성하려면 다음과 같이 두 개의 인자를 제공합니다.
            // ex) Cookie cookie = new Cookie("쿠키의_이름", "쿠키의_값");
            targetCookie = new Cookie(cookieName, "");
            targetCookie.setPath("/");
            targetCookie.setMaxAge(60*60*24);
        }
        return targetCookie;
    }
}
