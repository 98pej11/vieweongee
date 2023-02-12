//package com.ssafy.vieweongee.service;
//
//import com.ssafy.vieweongee.entity.User;
//
//import com.ssafy.vieweongee.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class PrincipalDetailService implements UserDetailsService {
//private final UserRepository userRepository;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user=userRepository.findByEmail(username)
//                .orElseThrow(()->new RuntimeException());
//        return new PrincipalDetails(user);
//    }
//}
