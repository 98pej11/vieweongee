package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.user.request.UserCreateRequest;
import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User login(User user) throws Exception {
        User dbUser = userRepository.findByEmail(user.getEmail());
        //비밀번호 일치
        if(dbUser != null && passwordEncoder.matches(user.getPassword(), dbUser.getPassword()))
            return dbUser;
        return null;
    }

    @Override
    public User createUser(UserCreateRequest registInfo) {
//        System.out.println(registInfo.getEmail() + " " + registInfo.getPassword() + " " + registInfo.getNickname());
        registInfo.setPassword(passwordEncoder.encode(registInfo.getPassword()));
        User user = new User(registInfo);
        return userRepository.save(user);
    }
}
