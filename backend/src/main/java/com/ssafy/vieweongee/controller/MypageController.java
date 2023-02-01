package com.ssafy.vieweongee.controller;

import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class MypageController {
    @Autowired
    MypageService mypageService;

    //사용자 타입 조회 -> 필요할까...?
    @GetMapping("/type")
    public ResponseEntity<?> inquireType(@RequestBody User user){
        String type = mypageService.findUserType(user);
        if(type == null)
            return ResponseEntity.status(500).body("사용자가 존재하지 않습니다");
        Map<String, String> result = new HashMap<>();
        result.put("data", type);
        result.put("message", "사용자의 타입을 찾았습니다.");
        return ResponseEntity.status(200).body(result);
    }


}
