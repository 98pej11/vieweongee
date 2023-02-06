package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.user.request.PasswordCheckRequest;
import com.ssafy.vieweongee.dto.user.request.UserCreateRequest;
import com.ssafy.vieweongee.dto.user.request.UserModifyRequest;
import com.ssafy.vieweongee.entity.User;

public interface UserService {
    User login(User user);
    User createUser(UserCreateRequest registInfo);
    Long getUserId(User user);

    User getUser(String email, String provider);
    User getUserByEmail(String email);
    User getUserById(Long id);
    boolean checkDuplicatedEmail(String email);
    boolean checkDuplicatedNickname(String nickname);
    boolean checkPassword(PasswordCheckRequest userInfo);
    void deleteUser(PasswordCheckRequest userInfo);
    void modifyUser(UserModifyRequest userInfo);
    void deleteRefreshtoken(String email, String provider);
    boolean saveTempPassword(String email, String password);

    String getJwtToken(Long id);

    void logout(Long id, String accessToken);
}
