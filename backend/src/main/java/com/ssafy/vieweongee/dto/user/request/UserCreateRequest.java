package com.ssafy.vieweongee.dto.user.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {
    private String email;
    private String name;
    private String password;
    private String passwordCheck;
    private String role="USER";


}
