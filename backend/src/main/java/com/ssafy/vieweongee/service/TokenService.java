package com.ssafy.vieweongee.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

@Service
@Slf4j
//@RequiredArgsConstructor
public class TokenService {
    private static String secretKey="c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK";
    private final UserRepository userRepository;


    public TokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
//        this.userService = userService;
    }
//    public TokenService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @PostConstruct
    protected void init(){
        secretKey= Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createAccessToken(Long id){
        log.info("id : {}",id);
        User dbUser=userRepository.getUserById(id);
        log.info("name : {}", dbUser.getName());
        String token=JWT.create()
                .withSubject(JwtProperties.ACCESS_TOKEN)
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
                .withClaim("Id",id.toString())
                .withClaim("Name",dbUser.getName())
                .sign(Algorithm.HMAC512(secretKey));
        JWTVerifier verifier =
                JWT.require(Algorithm.HMAC512(secretKey))
                        .build();
        String nowId = verifier.verify(token).getClaim("Id").toString();
        String nowName=verifier.verify(token).getClaim("Name").toString();
        log.info(nowId);
        return token;
    }

    public static String createRefreshToken(){
        return JWT.create()
                .withSubject(JwtProperties.REFRESH_TOKEN)
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.REFRESH_EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public void checkHeaderValid(HttpServletRequest request, HttpServletResponse response){
        String accessJwt=request.getHeader(JwtProperties.HEADER_PREFIX);
        String refreshJwt=request.getHeader(JwtProperties.REFRESH_HEADER_PREFIX);

        if (accessJwt==null){
            throw new RuntimeException();
        }else if (refreshJwt==null){
            throw new RuntimeException();
        }
    }

    public boolean checkTokenValid(String token){
        log.info("token is {}", token);
        JWTVerifier verifier =
            JWT.require(Algorithm.HMAC512(secretKey))
                .build();
        log.info("체크토큰의 verifier는...{}",verifier);
        // 만료됐으면 에러남
        String id= verifier.verify(token).getClaim("Id").toString();
        log.info("체크토큰valid의 username은 지금... {}",id);

        // 유저네임이 널 아니면 = 잘 검증돼서 유저네임 뽑히면 트루 반환
        if (id!=null){
            log.info("TRUE야@@@@@@@@@@@@");
            return true;
        }else{
            return false;
        }
    }

    public boolean isExpiredToken(String token){
        log.info("isExpired's token : {}", token);

        try{
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
        }catch(TokenExpiredException e){
            log.info("만료된 토큰");
            return true;
        }
        return false;
    }
    @Transactional
    public void setRefreshToken(Long Id, String refreshJwt){
        log.info("여기는 serRefresh : {} / {}", Id,refreshJwt );
        userRepository.findById(Id)
                .ifPresent(user->user.setJwtToken(refreshJwt));
    }

    @Transactional
    public void removeRefreshToken(String token){
        userRepository.findByJwtToken(token)
                .ifPresent(m->m.setJwtToken(null));
    }

    public boolean isNeedToUpdateRefreshToken(String token){
        try{
            Date expiresAt = JWT.require(Algorithm.HMAC512((secretKey)))
                    .build()
                    .verify(token)
                    .getExpiresAt();
            Date current=new Date(System.currentTimeMillis());
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(current);
            calendar.add(Calendar.DATE, 7);

            Date after7dayFromToday=calendar.getTime();
            if(expiresAt.before(after7dayFromToday)){
                log.info("7일 이내에 만료!!");
                return true;
            }
        }catch (TokenExpiredException e){
            return true;
        }
        return false;
    }


    public String getUid(String token){
        JWTVerifier verifier =
                JWT.require(Algorithm.HMAC512(secretKey))
                        .build();
        log.info("getUid의 verifier는...{}",verifier);
        // 만료됐으면 에러남
        String id= String.valueOf(verifier.verify(token).getClaim("Id"));
        log.info("토큰서비스에서 id!!!! {}",Long.parseLong(id.replaceAll("\"","")));
        return id;
    }
}
