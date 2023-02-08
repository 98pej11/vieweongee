package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.user.request.PasswordCheckRequest;
import com.ssafy.vieweongee.dto.user.request.UserCreateRequest;
import com.ssafy.vieweongee.dto.user.request.UserInfo;
import com.ssafy.vieweongee.dto.user.request.UserModifyRequest;
import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.exception.UserNotFoundException;
import com.ssafy.vieweongee.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private TokenService tokenService;
    @Override
    public User login(User user) {
        log.info("여긴 서비스임플, 유저 아이디눈 {} / {}", user.getEmail(), "global");
        User dbUser = userRepository.getUserByEmailandSocial(user.getEmail(),"global");
        log.info("dbuseR is {}", dbUser);
        //비밀번호 일치
        if(dbUser != null && passwordEncoder.matches(user.getPassword(), dbUser.getPassword()))
            return dbUser;
        return null;
    }

    @Override
    public User createUser(UserCreateRequest registInfo) {
        registInfo.setPassword(passwordEncoder.encode(registInfo.getPassword()));
        log.info("------------ 유저 저장-----------{}",registInfo.getEmail());
        User user = new User(registInfo);
        return userRepository.save(user);
    }

    @Override
    public Long getUserId(User user) {
        log.info("getUserID in service, user : {} / {}", user.getId(), user.getProvider());
        User dbUser = userRepository.getUserByEmailandSocial(user.getEmail(), "global");
        return dbUser.getId();
    }


    @Override
    public User getUserByEmail(String email) {
        User dbUser = userRepository.findByEmailUser(email);
        return dbUser;
//        userRepository.findByEmail(email)
//                .ifPresent(return );
    }

    @Override
    public User getUserById(Long id){
        User dbUser=userRepository.getUserById(id);
        return dbUser;
    }
    @Override
    public User getUser(String email, String provider) {
        log.info("@@@@@userService provider :  {}", provider);
        User dbUser = userRepository.getUserByEmailandSocial(email, provider);
        log.info("######################userService getUser : {}#########################", dbUser);
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
        if(userRepository.existsByName(nickname))
            return true;
        return false;
    }

    @Override
    public boolean checkPassword(PasswordCheckRequest pwCheck) {
        User dbUser = userRepository.getUserById(pwCheck.getId());
        if(dbUser != null && passwordEncoder.matches(pwCheck.getPassword(), dbUser.getPassword()))
            return true;
        return false;
    }

    @Override
    public void deleteUser(Long id){
        User user = userRepository.findById(id).get();
        log.info("서비스임플 {}",user.getId());

        userRepository.delete(user);
    }

    @javax.transaction.Transactional
    @Override
    public void modifyUser(UserModifyRequest userInfo) {
        User dbUser = userRepository.getUserById(userInfo.getId());
        String encryptPassword = passwordEncoder.encode(userInfo.getPassword());
        dbUser.update(userInfo.getName(), encryptPassword);
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

    @Override
    public String getJwtToken(Long id) {
        User user=userRepository.getUserById(id);
        String refreshJwt=user.getJwt_token();
        return refreshJwt;
    }

    @Override
    @Transactional
    public void logout(Long id, String accessToken) {
        log.info("서비스임플 로그아웃 {}, {} ",id, accessToken);
//        boolean val=tokenService.checkTokenValid(accessToken);
        // 로그아웃에 딸려온 토큰이 유효하면 디비에서 지운다
//        if (val==true){
//            User dbUser=userRepository.getUserById(id);
//            User user=new User(dbUser.getEmail(), dbUser.get)
        userRepository.logoutUpdate(id);
//        }
    }
    @Override
    public String getEmail(Long id) {
        String email = userRepository.getEmailById(id);
        return email;
    }

}

