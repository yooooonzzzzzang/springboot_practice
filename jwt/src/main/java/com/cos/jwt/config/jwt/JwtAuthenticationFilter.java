package com.cos.jwt.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.config.auth.PrincipalDetails;
import com.cos.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음
// /login 요청해서 username, password 전송하면(post)
// UsernamePasswordAuthenticationFilter 동작을 함 -> formlogin disable 해서 작동 안하는 중
// -> 이 필터를 다시 security config 에 등록해줘야함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter 로그인 시도 중 ");
        // 1. username, password 받아서
        try {
//            BufferedReader br = request.getReader();
//            String input = null;
//            while ((input = br.readLine() )!= null ){
//                System.out.println(input);
//            }
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            System.out.println(user);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            // PrincipalDetailsService 의 loadUserByUsername() 함수가 실행된 후 정상이면 authentication 이 리턴됨
            // DB 에 있는 username 과 password가 일치한다
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            // => 로그인이 되었다
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("로그인 완료됨: "+principalDetails.getUser().getUsername());  // 로그인이 정상적으로 되었다는 것
            // authentication 객체가 session 영역에 저장해야하고 그 방법이 return 해주면 됨
            // 리턴의 이유는 권한 관리를 security 가 대신 해주기 때문에 편하려고 하는 것
            // 굳이 jwt 토큰을 사용하면서 세션을 만들 이유가 없음 근데 단지 권한 처리 때문에 session 에 넣어준다
        } catch (IOException e) {
            e.printStackTrace();

        }
        System.out.println("===========================");
        // 2. 정상인지 로그인 시도를 해본다. authenticationManager로 로그인 시도를 하면
        // PrincipalDetailsService 가 호출 -> loadUserByUsername 이 자동으로 실행이 된다
        // 3. PrincipalDetails를 세션에 담고  (권한 관리를 위해 ) 없으면 안해도 됨
        // 4. JWT토큰을 만들어서 응답해주면 됨
        return null;
    }
    // attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행
    // JWT  토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response 해주면 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println(" successfulAuthentication 실행됨 : 인증이 완료되었다");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        // RSA 방식은 아니고 Hash 암호 방식, 시크릿키를 갖고 있음
        String jwtToken = JWT.create()
                .withSubject("cos토큰")
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000 * 10)))  //1000 -> 1초
                .withClaim("id", principalDetails.getUser().getId())
                .withClaim("username", principalDetails.getUser().getUsername())  // 내가 넣고 싶은거
                .sign(Algorithm.HMAC512("cos"));
        response.addHeader("Authorization","Bearer " + jwtToken );
    }
    /* 유저네임, 패스워드 로그인 정상
       JWT 토큰을 생성
       클라이언트 쪽으로 JWT 토큰을 응답

       요청할 때마다 JWT 토큰을 가지고 요청
       서버는 JWT 토큰이 유효한지를 판단 (필터 만들어야함) -> JwtAuthorizationFilter
    */

}
