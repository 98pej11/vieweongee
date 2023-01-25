package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.user.request.UserCreateRequest;
import com.ssafy.vieweongee.entity.User;

public interface UserService {
    public User login(User user) throws Exception;
    public User createUser(UserCreateRequest registInfo);
}
