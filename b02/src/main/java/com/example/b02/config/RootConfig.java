package com.example.b02.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {
    @Bean
    public ModelMapper getMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true) //필드이름 기준으로 매핑
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE) // private 접근 가능
                .setMatchingStrategy(MatchingStrategies.LOOSE); // 필드이름이 완전히 일치하지 않아도 매핑 시도
        return modelMapper;
    }
}
