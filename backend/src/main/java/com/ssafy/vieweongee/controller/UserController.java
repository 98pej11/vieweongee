package com.ssafy.vieweongee.controller;

import com.ssafy.vieweongee.dto.user.request.PasswordCheckRequest;
import com.ssafy.vieweongee.dto.user.request.UserCreateRequest;
import com.ssafy.vieweongee.dto.user.request.UserModifyRequest;
import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    //로그인
    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody User user) {
        User loginUser = userService.login(user);
        if(loginUser != null){
            //String accessToken
            //String refreshToken
            return ResponseEntity.status(200).body("로그인에 성공했습니다.");
        }
        return ResponseEntity.status(400).body("로그인에 실패했습니다.");
    }

    //로그아웃
    @GetMapping("/signout")
    public ResponseEntity<?> logout(@RequestBody String email){
        //delete RefreshToken
        userService.deleteRefreshtoken(email);
        return ResponseEntity.status(200).body("로그아웃에 성공했습니다.");
    }

    //회원 가입
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody UserCreateRequest registInfo) {
        try {
            //email 인증번호 보내기

            //비밀번호 확인
            if(!registInfo.getPassword().equals(registInfo.getPasswordCheck()))
                return ResponseEntity.status(409).body("비밀번호가 일치하지 않습니다.");

            //닉네임 중복검사
            if(userService.checkDuplicatedNickname(registInfo.getNickname()))
                return ResponseEntity.status(409).body("nickname이 중복됩니다.");

            //회원 가입
            User user = userService.createUser(registInfo);
            return ResponseEntity.status(200).body("회원가입 완료!");
        } catch (Exception e) {
            return ResponseEntity.status(409).body("회원가입 실패");
        }
    }

    //email 중복 검사
    @GetMapping("/email-check")
    public ResponseEntity<?> emailCheck(@RequestBody String email){
        if(!userService.checkDuplicatedEmail(email))
            return ResponseEntity.status(200).body("사용 가능한 이메일 입니다!");
        return ResponseEntity.status(409).body("이메일이 중복됩니다. 다른 이메일을 선택해 주세요");
    }

    //인증 이메일 발송
//    @PostMapping("/email-valid")
//    public ResponseEntity<?> sendEmail(){
//
//    }

    //이메일 인증
//    @PostMapping("/email-valid/certi")
//    public ResponseEntity<?> emailValid(){
//
//    }

    //닉네임 중복검사
//    @GetMapping("/nickname-check")
//    public ResponseEntity<?> nicknameCheck(@RequestBody String nickname){
//        if(!userService.checkDuplicatedNickname(nickname))
//            return ResponseEntity.status(200).body("nickname is not duplicated");
//        return ResponseEntity.status(409).body("nickname is duplicated");
//    }

    //refresh token 재발급

    //비밀번호 찾기 -> 임시 비밀번호 발급


    //비밀번호 확인
    @PostMapping("/password")
    public ResponseEntity<?> verifyPassword(@RequestBody PasswordCheckRequest userInfo){
        if(userService.checkPassword(userInfo))
            return ResponseEntity.status(200).body("비밀번호가 일치합니다.");
        return ResponseEntity.status(409).body("비밀번호가 일치하지 않습니다.");
    }

    //회원 정보 조회
    @GetMapping("/")
    public ResponseEntity<?> getInfo (@RequestBody String email){
        User user = userService.getUserByEmail(email);
        if(user == null)
            return ResponseEntity.status(500).body("회원 정보 찾기 불가");
        else {
            Map<String, Object> result = new HashMap<>();
            result.put("data", user);
            result.put("message", "회원 정보 조회 성공");
            return ResponseEntity.status(200).body(result);
        }
    }

    //회원 정보 수정
    @PutMapping("/")
    public ResponseEntity<?> editInfo(@RequestBody UserModifyRequest modifyInfo){
        if(!userService.checkDuplicatedNickname(modifyInfo.getNickname())){
            if(modifyInfo.getPassword().equals(modifyInfo.getPasswordCheck())){
                userService.modifyUser(modifyInfo);
                return ResponseEntity.status(200).body("회원 정보 수정에 성공했습니다.");
            }
            return ResponseEntity.status(409).body("비밀번호가 일치하지 않습니다.");
        }
        return ResponseEntity.status(409).body("닉네임이 중복됩니다.");
    }

    //회원 탈퇴
    @DeleteMapping("/")
    public ResponseEntity<?> withdrawalUser(@RequestBody PasswordCheckRequest userInfo){
        if(userService.checkPassword(userInfo)){
            userService.deleteUser(userInfo);
            return ResponseEntity.status(200).body("회원탈퇴 성공");
        }
        return ResponseEntity.status(409).body("비밀번호가 일치하지 않습니다.");

    }
}
