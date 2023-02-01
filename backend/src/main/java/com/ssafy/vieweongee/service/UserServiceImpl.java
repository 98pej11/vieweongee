package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.user.request.PasswordCheckRequest;
import com.ssafy.vieweongee.dto.user.request.UserCreateRequest;
import com.ssafy.vieweongee.dto.user.request.UserModifyRequest;
import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User login(User user) {
        User dbUser = userRepository.findByEmailAndProvider(user.getEmail(), user.getProvider());
        //비밀번호 일치
        if(dbUser != null && passwordEncoder.matches(user.getPassword(), dbUser.getPassword()))
            return dbUser;
        return null;
    }

    @Override
    public User createUser(UserCreateRequest registInfo) {
        registInfo.setPassword(passwordEncoder.encode(registInfo.getPassword()));
        User user = new User(registInfo);
        return userRepository.save(user);
    }

    @Override
    public User getUser(String email, String provider) {
        User dbUser = userRepository.findByEmailAndProvider(email, provider);
        return dbUser;
    }

    @Override
    public boolean checkDuplicatedEmail(String email) {
        System.out.println(email);
        if(userRepository.existsByEmail(email))
            return true;
        return false;
    }

    @Override
    public boolean checkDuplicatedNickname(String nickname) {
        if(userRepository.existsByNickname(nickname))
            return true;
        return false;
    }

    @Override
    public boolean checkPassword(PasswordCheckRequest userInfo) {
        User dbUser = userRepository.findByEmailAndProvider(userInfo.getEmail(), userInfo.getProvider());
        if(dbUser != null && passwordEncoder.matches(userInfo.getPassword(), dbUser.getPassword()))
            return true;
        return false;
    }

    @Override
    public void deleteUser(PasswordCheckRequest userInfo){
        User user = userRepository.findByEmailAndProvider(userInfo.getEmail(), userInfo.getProvider());
        userRepository.delete(user);
    }

    @Transactional
    @Override
    public void modifyUser(UserModifyRequest userInfo) {
        User dbUser = userRepository.findByEmailAndProvider(userInfo.getEmail(), userInfo.getProvider());
        String encryptPassword = passwordEncoder.encode(userInfo.getPassword());
        dbUser.update(userInfo.getNickname(), encryptPassword);
        userRepository.save(dbUser);

    }

    @Override
    public void deleteRefreshtoken(String email, String provider) {
        User logoutUser = userRepository.findByEmailAndProvider(email, provider);
        logoutUser.deleteRefreshToken(null);
    }


    @Override
    public boolean saveTempPassword(String email, String password) {
        User dbUser = userRepository.findByEmailAndProvider(email, "global");
        if(dbUser != null){
            String tempPw = passwordEncoder.encode(password);
            dbUser.updateTempPassword(tempPw);
            userRepository.save(dbUser);
            return true;
        }
        else
            return false;
    }


}
