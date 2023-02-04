package com.ssafy.vieweongee.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.vieweongee.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
@Slf4j
@RequiredArgsConstructor
public class JwtFilter implements HandlerInterceptor {
    private final TokenService tokenService;
    private final ObjectMapper ob;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception{
        String accessToken=request.getHeader("ACCESS");
        ObjectMapper ob=new ObjectMapper();
        String jsonString="";

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        log.info("jwt 필터에 들어왔드아아아아아악 {}", accessToken);

        if (!StringUtils.isEmpty(accessToken)){
            try{
                if (!tokenService.checkTokenValid(accessToken) ){
                    PrintWriter out=response.getWriter();
                    response.setStatus(400);
                    log.info("토큰이 유효하지않드아아아아아");
//                    jsonString=ob.writeValueAsString(new )
                    return false;
                }
            }catch (ExpiredJwtException e){

            }
        }
        else{
            return false;
        }

        log.info("토큰이 유효하드아아아아아");
        return true;


    }



}
