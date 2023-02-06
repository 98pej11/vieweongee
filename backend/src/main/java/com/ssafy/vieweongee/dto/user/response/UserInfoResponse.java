package com.ssafy.vieweongee.dto.user.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInfoResponse {
    private String email;
    private String name;
    private String provider;

    @Builder
    public UserInfoResponse(String email, String name, String provider) {
        this.email = email;
        this.name = name;
        this.provider = provider;
    }
}
