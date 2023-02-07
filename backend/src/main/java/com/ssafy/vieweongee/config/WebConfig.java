package com.ssafy.vieweongee.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.vieweongee.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@RequiredArgsConstructor
public class WebConfig extends WebMvcConfigurerAdapter {
    private final TokenService tokenService;
    private  final ObjectMapper objectMapper;

    @Override
    public void addInterceptors(InterceptorRegistry reg){
        reg.addInterceptor(new JwtFilter(tokenService, objectMapper))
                .order(1)
                .addPathPatterns("/users/signout", "/users/password-find",
                        "/users/","/study/",
                        "/study/{study_id}","/study/{study_id}/member","study/{study_id}/comment",
                        "/study/{study_id}/comment/{comment_id}",
                        "/study/{study_id}/comment/{comment_id}/reply",
                        "/study/{study_id}/comment/{comment_id}/{reply_id}",
                        "/study/{study_id}/resume",
                        "/meeting/*","/sessions/{sessionID}/connection"
                        );

    }

}
