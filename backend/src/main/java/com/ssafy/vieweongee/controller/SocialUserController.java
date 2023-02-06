package com.ssafy.vieweongee.controller;


import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//@RequiredArgsConstructor
@Slf4j
@RestController
public class SocialUserController {
    private static UserRepository userRepository;
    public SocialUserController(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    // 닉네임 한 개 불러오기
    public static String NickName() throws Exception{
        String result=null;
        String res=null;
        try{
            URL url = new URL("https://nickname.hwanmoo.kr/?format=text&count=1&max_length=8");
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/text; utf-8");
            conn.setDoOutput(true);

            // 서버로부터 데이터 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
                sb.append(line);
            }

            result=sb.toString();
            log.info("중간 닉네임은!!!!!!!! {}", result);
//            res = checkNickname(result);

        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    // 중복검사 후 확정
    public static String checkNickname(String nickname) throws Exception {
        String fin=nickname;
        UserRepository userRepository = null;
        // 중복되는 경우 true -> false가 나와야 함.
        // 중복이면 계쏙 새로 만들어서 fin에 저장
//        while (userRepository.existsByName(nickname)){
//            fin = NickName();
//        }
        log.info("지금은 닉네임 생성중, 체크 : {}", fin);
        return fin;
    }


}

