package com.ssafy.vieweongee.dto.user.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordCheckRequest {
    private Long id;
    private String password;
}
