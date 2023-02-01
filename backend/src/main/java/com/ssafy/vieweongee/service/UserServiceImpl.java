package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.exception.UserNotFoundException;
import com.ssafy.vieweongee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        return user;
    }
}
