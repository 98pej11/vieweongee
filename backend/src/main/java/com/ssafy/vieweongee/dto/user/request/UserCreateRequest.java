package com.ssafy.vieweongee.dto.user.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {
    private String email;
    private String nickname;
    private String password;
}
