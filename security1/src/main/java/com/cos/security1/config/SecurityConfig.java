package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터(SecurityConfig)가 스프링 필터체인에 등록됨
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 패스워드 암호화
    @Bean // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http)throws Exception{
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and() // 권한이 없는 페이지에 접근하면 로그인 화면으로 이동
                .formLogin()
                .loginPage("/loginForm");
    }
}
