package com.ssafy.vieweongee.controller;

import com.ssafy.vieweongee.dto.user.request.UserCreateRequest;
import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    //로그인
    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            User loginUser = userService.login(user);
            if(loginUser != null){
                //String accessToken
                //String refreshToken
                return ResponseEntity.status(200).body("login success");
            }
            return ResponseEntity.status(400).body("login fail1");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("login fail2");
        }

    }

    //회원 가입
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody UserCreateRequest registInfo) {
        try {
//            System.out.println(registInfo.getEmail() + " " + registInfo.getPassword() + " " + registInfo.getNickname());
            User user = userService.createUser(registInfo);
        } catch (Exception e) {
            return ResponseEntity.status(409).body("regist fail");
        }
        return ResponseEntity.status(200).body("regist success");
    }
}
