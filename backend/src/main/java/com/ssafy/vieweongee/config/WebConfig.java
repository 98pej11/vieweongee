package com.ssafy.vieweongee.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.vieweongee.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@RequiredArgsConstructor
public class WebConfig extends WebMvcConfigurerAdapter {
    private final TokenService tokenService;
    private  final ObjectMapper objectMapper;

    @Override
    public void addInterceptors(InterceptorRegistry reg){
        reg.addInterceptor(new JwtFilter(tokenService, objectMapper))
                .order(1)
                .addPathPatterns("/users/signout", "/study/","/study/{study_id}");

    }

}
