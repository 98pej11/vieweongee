//package com.ssafy.vieweongee.config;
//
//import antlr.StringUtils;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ssafy.vieweongee.entity.User;
//import com.ssafy.vieweongee.service.TokenService;
//import com.ssafy.vieweongee.service.UserService;
//import io.jsonwebtoken.ExpiredJwtException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.GenericFilterBean;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.http.HttpHeaders;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
////    private final UserDetailsService userDetailsService;
//    private final TokenService tokenService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        String jwt = request.getHeader("ACCESS");
//
//        if(jwt != null && tokenService.checkTokenValid(jwt)){
//            String id = tokenService.getInfo(jwt);
//            Authentication authentication = getAuthentication(id);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        filterChain.doFilter(request,response);
//    }
//
//    private Authentication getAuthentication(String id) {
//        UserService userService=null;
//        User userDetails = userService.getUserById(id);
//
//        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
//
//        return new IdPwAuthenticationToken(userDetails.get(), null, userDetails.getAuthorities());
//    }
//}
//
//
//
