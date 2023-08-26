package org.zerock.w2.filter;

import lombok.extern.log4j.Log4j2;
import org.zerock.w2.dto.MemberDTO;
import org.zerock.w2.service.MemberService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter(urlPatterns = {"/todo/*"})
@Log4j2
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Login check filter....");
        //filter 는 ServletRequest , ServletResponse 와 같은 상위 타입 파라미터 사용하므로 다운캐스팅
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession();

        if (session.getAttribute("loginInfo") != null){
            chain.doFilter(request,response);
            return;
        }

        // session에 loginInfo 없다면
        // 쿠키 체크
        Cookie cookie = findCookie(req.getCookies(), "remember-me");

        // session 에도 쿠키도 없으면 로그인으로 이동
        if (cookie == null ) {
            resp.sendRedirect("/login");
            return;
        }

        // 쿠키가 존재하는 상황
        log.info("cookie 는 존재함 , session x");
        String uuid = cookie.getValue();
        try {
            // db 확인
            MemberDTO memberDTO = MemberService.INSTANCE.getByUUID(uuid);

            if (memberDTO == null){
                throw new Exception("Cookie value is not valid");
            }
            // 회원정보 세션 추가
            session.setAttribute("loginInfo", memberDTO);
            chain.doFilter(request, response);
        }catch (Exception e){
            e.printStackTrace();
            resp.sendRedirect("/login");
        }
    }

    private Cookie findCookie(Cookie[] cookies, String name) {
        if (cookies == null || cookies.length == 0){
            return null;
        }
        Optional<Cookie> result = Arrays.stream(cookies)
                .filter(ck -> ck.getName().equals(name))
                .findFirst();
        // 삼항연산자
        // 조건식 ? 참일때 반환 : 거짓일때 반환
        return result.isPresent()?result.get():null;
    }
}
