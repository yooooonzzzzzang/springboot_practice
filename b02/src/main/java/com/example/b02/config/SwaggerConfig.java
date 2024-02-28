package com.example.b02.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
//  rest controller
    @Bean
    public GroupedOpenApi restApi(){
        return GroupedOpenApi.builder()
                .pathsToMatch("/api/**")
                .group("REST API")
                .build();
    }
//  controller
    @Bean
    public GroupedOpenApi commonApi(){
        return GroupedOpenApi.builder()
                .pathsToMatch("/**/*")
                .pathsToExclude("/api/**")
                .group("COMMON API")
                .build();

    }
}
