package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.user.request.UserCreateRequest;
import com.ssafy.vieweongee.entity.User;

public interface UserService {
    User login(User user);
    User createUser(UserCreateRequest registInfo);
    User getUserByEmail(String email);
    boolean checkDuplicatedEmail(String email);
    boolean checkDuplicatedNickname(String nickname);
}
