package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MypageServiceImpl implements MypageService{
    @Autowired
    UserRepository userRepository;
    @Override
    public String findUserType(User user) {
        User dbUser = userRepository.findByEmailAndProvider(user.getEmail(), user.getProvider());
        if(dbUser != null)
            return dbUser.getProvider();
        return null;
    }
}
