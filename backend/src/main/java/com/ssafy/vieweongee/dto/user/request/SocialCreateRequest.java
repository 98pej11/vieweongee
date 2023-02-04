package com.ssafy.vieweongee.dto.user.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SocialCreateRequest {
    String email;
    String name;
//    Long id;
    String refresh_token;
    String access_token;
    String soical_login;

//    public SocialCreateRequest(String email, String nickName, String refreshToken, String socialLogin) {
//    }
}
