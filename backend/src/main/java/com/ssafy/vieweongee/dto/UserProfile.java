package com.ssafy.vieweongee.dto;

import com.ssafy.vieweongee.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserProfile {
    private static String name = null;
    private final String email;
    private final String social_login;

    public UserProfile(String name, String email, String picture, String social_login) {
        this.name = name;
        this.email = email;
        this.social_login=social_login;
    }

//    public static User toUser() {
//        return new User(name, email, imageUrl);
//    }

    public static Object toUser(String name, String email, String picture, String social_login) {
        return new User(name, email, social_login);
    }

//    public static Object toUser(String name, String email, String picture) {
//    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


}
