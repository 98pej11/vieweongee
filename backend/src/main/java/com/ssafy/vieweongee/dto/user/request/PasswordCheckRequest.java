package com.ssafy.vieweongee.dto.user.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordCheckRequest {
    private String email;
    private String provider;
    private String password;
    private String passwordCheck;
}
