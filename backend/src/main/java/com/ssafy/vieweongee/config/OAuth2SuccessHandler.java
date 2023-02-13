package com.ssafy.vieweongee.config;

import com.ssafy.vieweongee.dto.user.response.UserLoginResponse;
import com.ssafy.vieweongee.entity.User;

import com.ssafy.vieweongee.repository.UserRepository;
import com.ssafy.vieweongee.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public OAuth2SuccessHandler(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("oAuth2User : {}", oAuth2User.getAttributes());
//        User userDto=userRequestMapper.toDto(oAuth2User);

        var attributes = oAuth2User.getAttributes();
        String email=(String)attributes.get("email");

        log.info("controller, email / social : {} / {}", email, (String)attributes.get("id"));
        User user=userRepository.getUserByEmailandSocial(email, (String)attributes.get("id"));
        log.info("성공핸들러에서 불러온 user : {}, {}", user.getId(), user.getEmail());


        String AccessJwt =tokenService.createAccessToken(user.getId());
        String RefreshToken=tokenService.createRefreshToken();


        Long id=user.getId();
        tokenService.setRefreshToken(id,RefreshToken);
        List<String> tokens=new ArrayList<>();
        tokens.add(AccessJwt);
        tokens.add(RefreshToken);

        // 정보 response
        Map<String, Object> result = new HashMap<>();
        UserLoginResponse login=new UserLoginResponse();
        login.setId(user.getId());
        login.setName(user.getName());
        login.setEmail(user.getEmail());

        result.put("data",login);
        result.put("access", AccessJwt);
        result.put("refresh", RefreshToken);
        result.put("massage","SUCCESS");
//        response.addHeader("ACCESS", AccessJwt);
        log.info("ACCESS : {}",AccessJwt);
        log.info("REFRESH : {}", RefreshToken);
//        response.addHeader("refresh",refreshToken);
        response.setContentType("application/json;charset=UTF-8");
//        return ResponseEntity.ok().body(tokens);
    }

//    private void writeTokenResponse(HttpServletResponse response, Token token) throws IOException{
//        response.setContentType("text/html;charset=UTF-8");
//        log.info("{}", token.getRefreshToken());
//        response.addHeader("Auth", token.getToken());
//        response.addHeader("Refresh", token.getRefreshToken());
//        response.setContentType("application/json;charset=UTF-8");
//
//        var writer=response.getWriter();
//        writer.println(objectMapper.writeValueAsString(token));
//        writer.flush();
//    }
}

