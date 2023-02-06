package com.ssafy.vieweongee.dto.user.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponse {
    private String email;
    private Long id;
    private String name;
}
