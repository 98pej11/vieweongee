package com.ssafy.vieweongee.model;

import com.ssafy.vieweongee.entity.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class UserRequestMapper {
    public User toDto(OAuth2User oAuth2User){
        var attributes=oAuth2User.getAttributes();
        return User.builder()
                .email((String)attributes.get("email"))
                .name((String) attributes.get("name"))
//                .id((String) attributes.get())
                .provider((String) attributes.get("social_login"))
                .build();
    }

//    public UserfindRequest toFindDto(User user){
//        return new UserFindRequest(user.getEmail());
//    }
}
