package com.ssafy.vieweongee.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.vieweongee.repository.UserRepository;
import com.ssafy.vieweongee.service.CustomOAuth2UserService;
import com.ssafy.vieweongee.service.TokenService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private final UserRepository userRepository;
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler successHandler;
    private final TokenService tokenService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().configurationSource(corsConfigurationSource());

        http
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()					//추가
                .oauth2Login().successHandler(successHandler)			// OAuth2기반의 로그인인 경우
                .userInfoEndpoint()			// 로그인 성공 후 사용자정보를 가져온다
                .userService(oAuth2UserService);	//사용자정보를 처리할 때 사용한다
//                .and();

//        http.addFilterBefore(new JwtAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
//                .anonymous()
//                    .oauth2Login().authorizeRequests()
//                    .successHandler(successHandler)

//
//                .antMatchers().permitAll();



//                .authorizeRequests()
//                .antMatchers("/api/users/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin();

//                .loginPage()
//                .loginProcessingUrl("loginProc")
//                .defaultSuccessUrl("/")
//                .and()
//                .logout()
//                .logoutSuccessUrl("/")
//                .invalidateHttpSession(true);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

//        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    //비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }


}

