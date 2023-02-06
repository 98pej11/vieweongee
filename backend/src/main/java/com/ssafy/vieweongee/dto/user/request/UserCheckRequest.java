package com.ssafy.vieweongee.dto.user.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCheckRequest {
    String accessToken="";
    String refreshToken="";
    Long id=null;
    String email="";
}
