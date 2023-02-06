package com.ssafy.vieweongee.controller;

import com.nimbusds.oauth2.sdk.Message;
import com.ssafy.vieweongee.dto.study.CreateStudyRequest;
import com.ssafy.vieweongee.dto.user.request.*;
import com.ssafy.vieweongee.dto.user.response.UserInfoResponse;
import com.ssafy.vieweongee.dto.user.response.UserLoginResponse;
import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.service.EmailService;
import com.ssafy.vieweongee.service.TokenService;
import com.ssafy.vieweongee.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    @Autowired
    EmailService emailService;

    //로그인
    @ResponseBody
    @PostMapping("/signin")
    public ResponseEntity login(@RequestBody User user) {
//        log.info("나 로그인하러 왔옹 : {}", user.getEmail());
//        log.info("여긴 유저 컨트롤러.. 유저 이메일은 {} 유저 프로바이더는 : {}", user.getEmail(), "global");

        User loginUser = userService.login(user);
        Map<String, Object> result = new HashMap<>();
        if(loginUser != null){
            Long userId = userService.getUserId(user);
            String accessToken=tokenService.createAccessToken(userId);
            String refreshToken=tokenService.createRefreshToken();

            log.info("리프레쉬 앤 엑세스 : {} // {}", refreshToken, accessToken);
            tokenService.setRefreshToken(userId,refreshToken);

//            HttpHeaders headers = new HttpHeaders();
//            headers.add("ACCESS", accessToken);
//            headers.add("REFRESH", refreshToken);

            UserLoginResponse login=new UserLoginResponse();
            login.setId(loginUser.getId());
            login.setName(loginUser.getName());
            login.setEmail(loginUser.getEmail());

//            result.put("data",login); // 원래 주면 안 됨
            result.put("access", accessToken);
            result.put("refresh", refreshToken);
            result.put("massage","SUCCESS");

            return ResponseEntity.status(200).body(result);
        }
        result.put("data",null);
        result.put("massage","FAIL");
        return ResponseEntity.status(400).body(result);
    }

    //회원 가입
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody UserCreateRequest registInfo) {
        try {
            //email 중복 검사, email 인증번호 보내기

            //비밀번호 확인
            if(!registInfo.getPassword().equals(registInfo.getPasswordCheck()))
                return ResponseEntity.status(409).body("FAIL:PW");

            //닉네임 중복검사
            if(userService.checkDuplicatedNickname(registInfo.getName()))
                return ResponseEntity.status(409).body("FAIL:NAME");

            //회원 가입
            User user = userService.createUser(registInfo);
            Map<String, Object> result = new HashMap<>();
            result.put("data", null);
            result.put("message","SUCCESS");

            return ResponseEntity.status(200).body(result);
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("data", null);
            result.put("message","FAIL");
            return ResponseEntity.status(409).body(result);
        }
    }

    //email 중복 검사
    @GetMapping("/email-check/{email}")
    public ResponseEntity<?> emailCheck(@PathVariable("email") String email){
        Map<String, Object> result = new HashMap<>();
        result.put("data",null);

        if(userService.checkDuplicatedEmail(email)){
            result.put("message","SUCCESS");
            log.info("here");
            return ResponseEntity.status(200).body(result);
        }
        result.put("message","FAIL");
        return ResponseEntity.status(409).body(result);
    }

    //  인증 이메일 발송
    @PostMapping("/email-valid")
    public ResponseEntity<?> sendEmail(@RequestBody EmailInfo emailInfo) {
        try {
            String code = emailService.sendSimpleMessage(emailInfo.getEmail(), "code");
            Map<String, String> result = new HashMap<>();
            result.put("data", code);
            result.put("message","SUCCESS");
            return ResponseEntity.status(200).body(result);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> result = new HashMap<>();
            result.put("data", null);
            result.put("message", "FAIL");
            return ResponseEntity.status(500).body(result);
        }
    }


    //비밀번호 찾기 -> 임시 비밀번호 발급
    @PutMapping("/password-find")
    public ResponseEntity<?> findPassword(@RequestBody UserInfo userInfo){
        Map<String, Object> result = new HashMap<>();
        result.put("data",null);
        //일반 유저가 존재하는가
        User user = userService.getUserById(userInfo.getId());
        log.info("user controller 비번 찾는 즁 : {}",user);
        if(user == null){
            result.put("message","FAIL:USER");
            return ResponseEntity.status(400).body(result);
        }

        try {
            String email = userService.getEmail(userInfo.getId());
            String pw = emailService.sendSimpleMessage(email, "password");
            if(userService.saveTempPassword(email, pw)){
                result.put("message","SUCCESS");
                return ResponseEntity.status(200).body(result);
            }
            else{
                result.put("message","FAIL");
                return ResponseEntity.status(400).body(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message","FAIL:EMAIL");
            return ResponseEntity.status(500).body(result);
        }

    }

    //비밀번호 확인
    @PostMapping("/password")
    public ResponseEntity<?> verifyPassword(@RequestBody PasswordCheckRequest pwCheck){
        if(userService.checkPassword(pwCheck))
            return ResponseEntity.status(200).body("SUCCESS");
        return ResponseEntity.status(409).body("FAIL");
    }

    //회원 정보 조회
    @GetMapping("/")
    public ResponseEntity<?> getInfo (@RequestBody UserInfo userInfo){
        User user = userService.getUserById(userInfo.getId());
        Map<String, Object> result = new HashMap<>();
        if(user == null){
            result.put("data", null);
            result.put("message","FAIL");
            return ResponseEntity.status(500).body(result);
        }
        else {
            UserInfoResponse userInfoResponse = new UserInfoResponse(user.getEmail(), user.getName(), user.getProvider());
            result.put("data", userInfoResponse);
            result.put("message", "SUCCESS");
            return ResponseEntity.status(200).body(result);
        }
    }

    //회원 정보 수정
    @PutMapping("/")
    public ResponseEntity<?> editInfo(@RequestBody UserModifyRequest modifyInfo){
        Map<String, Object> result = new HashMap<>();
        result.put("data",null);
        if(!userService.checkDuplicatedNickname(modifyInfo.getName())){
            if(modifyInfo.getPassword().equals(modifyInfo.getPasswordCheck())){
                userService.modifyUser(modifyInfo);
                result.put("message","SUCCESS");
                return ResponseEntity.status(200).body(result);
            }
            result.put("message","FAIL");
            return ResponseEntity.status(409).body(result);
        }
        result.put("message","FAIL");
        return ResponseEntity.status(409).body(result);
    }

    //회원 탈퇴
    @DeleteMapping("/")
    public ResponseEntity<?> withdrawalUser(@RequestBody PasswordCheckRequest userInfo){
        if(userService.checkPassword(userInfo)){
            userService.deleteUser(userInfo.getId());
            return ResponseEntity.status(200).body("SUCCESS");
        }
        return ResponseEntity.status(409).body("FAIL");

    }

    // 액세스 토큰 확인
    @PostMapping("/check-access")
    public ResponseEntity checkAccess(@RequestBody UserCheckRequest userToken, HttpServletResponse response){
//        Map<String, Object> resultMap=new HashMap<>();
        // access 토큰을 확인
        boolean check=tokenService.checkTokenValid(userToken.getAccessToken());
        if (check==true){
            // 성공 응답
//            return ResponseEntity.status(200).body("access token이 만료돼지 않았습니당 패쓰!");
            return ResponseEntity.status(200).body("접근 허가");
        }
        // else라면 토큰 만료된거
        // refresh 토큰 달라고 요청 보내야함!!!
        return ResponseEntity.status(409).body("FAIL");
        // 원래 리프레쉬 토큰도 검증해야...ㄷㄷ
    }


    @PostMapping("/check-refresh")
    public ResponseEntity checkRefresh(@RequestBody UserCheckRequest userToken, HttpServletRequest response){
        log.info("리프레쉬 오긴 하나 ---> {}", userToken.getRefreshToken());
        log.info("DB의 리프레쉬 : {}", userService.getJwtToken(userToken.getId()));
        if (userToken.getRefreshToken().equals(userService.getJwtToken(userToken.getId()))){
            // 리프레쉬 토큰이 db와 일치하면 access 토큰 재발급해줄거야
            log.info("access 재발급 : {}, {}", userToken.getRefreshToken(), userToken.getId());
            String newAccessJwt=tokenService.createAccessToken(userToken.getId());
            HttpHeaders headers=new HttpHeaders();
            headers.add("ACCESS",newAccessJwt);


            return ResponseEntity.ok().headers(headers).body("NEW ACCESS TOKEN");
//            return ResponseEntity.status(200).body("new access token 발급");
        }
        else {
            // 리프레쉬 토큰이 db와 일치하지 않으면 요청 종료
            return ResponseEntity.status(409).body("요청 실패 - 로그인 검증 실패");
//            return ResponseEntity.status(409).body("요청 실패 - 로그인 검증 실패");
        }
    }

    @PostMapping("/signout")
    public ResponseEntity logout(@RequestBody UserCheckRequest userToken, HttpServletResponse response){
        String accessToken=userToken.getAccessToken();
        boolean check=tokenService.checkTokenValid(userToken.getAccessToken());
        if (check==true){
            Long id=userToken.getId();
            userService.logout(id, accessToken);
            return ResponseEntity.status(200).body("SUCCESS");
        }
        return ResponseEntity.status(409).body("FAIL:TOKEN");
    }



    //email 인증
//    @PostMapping("/email-valid")
//    public ResponseEntity<?> sendEmail(){
//
//    }

//    @PostMapping("/email-valid/certi")
//    public ResponseEntity<?> emailValid(){
//
//    }

    //닉네임 중복 검사
//    @GetMapping("/nickname-check")
//    public ResponseEntity<?> nicknameCheck(@RequestBody String nickname){
//        if(!userService.checkDuplicatedNickname(nickname))
//            return ResponseEntity.status(200).body("nickname is not duplicated");
//        return ResponseEntity.status(409).body("nickname is duplicated");
//    }

    //refresh token 재발급

    //비밀번호 찾기

    //

}
