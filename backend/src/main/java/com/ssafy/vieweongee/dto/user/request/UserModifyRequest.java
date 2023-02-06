package com.ssafy.vieweongee.dto.user.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModifyRequest {
    private String email;
    private String name;
    private String provider;
    private String password;
    private String passwordCheck;
}
