package com.ssafy.vieweongee.service;


import com.ssafy.vieweongee.dto.user.request.UserCreateRequest;
import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.repository.SummaryRepository;
import com.ssafy.vieweongee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.ssafy.vieweongee.controller.SocialUserController.NickName;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final SummaryService summaryService;

//    private final UserCreateRequest userCreateRequest;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        String email=null;
        String social = oAuth2Attribute.getProvider();

        log.info("?????? ?????? : {}", social);
        log.info("?????? oAuth2Attribute : {}", oAuth2Attribute);
        log.info("?????? ?????? : {}",oAuth2User);

        if (social.equals("kakao")){
            log.info("??? ????????????~~....");

            Map<String, Object>kakaoProfile = (Map<String, Object>)oAuth2User.getAttributes().get("kakao_account");
            Map<String, Object>kakaosAccount=(Map<String, Object>)kakaoProfile.get("profile");

            log.info("????????? ????????????... {}", kakaosAccount);

            email= kakaoProfile.get("email").toString();
            log.info("????????? ????????????~... {}",email);
            List<User> findMember = userRepository.getUserByEmail(email);

            User user;
            try{
                if (findMember.isEmpty())  {
                    log.info("????????? ???????????? ??????! ???????????? ??????");
                    log.info("{}", oAuth2Attribute.getAttributeKey());

                    user = new User(email, NickName(), oAuth2Attribute.getProvider());
//                    user = new User(socialInfo);

                    userRepository.save(user);
                    log.info("????????????????????????????);");
                    // ????????? ?????? ????????? ????????? ??????
                    UserCreateRequest userCreateRequest = new UserCreateRequest();
                    userCreateRequest.setEmail(email);
                    userCreateRequest.setProvider("kakao");
                    summaryService.createSummary(userCreateRequest);
                }
                else {
                    user=userRepository.getUserByEmailandSocial(email, social);
                    log.info("???????????? ?????????????????? ????????? ???????????? ??? ?????? : {} in {}", user, social);
                    if (user==null){
                        log.info("????????? ???????????? ??????! ???????????? ??????");
                        log.info("{}", oAuth2Attribute.getAttributeKey());
                        user = new User(email, NickName(), oAuth2Attribute.getProvider());
                        userRepository.save(user);

                        // ????????? ?????? ????????? ????????? ??????
                        UserCreateRequest userCreateRequest = new UserCreateRequest();
                        userCreateRequest.setEmail(email);
                        userCreateRequest.setName(oAuth2User.getName());
                        userCreateRequest.setProvider("kakao");
                        summaryService.createSummary(userCreateRequest);
                    }
                        log.info("????????? ????????? ????????? ????????????");
//                user=userRepository.findUserByEmail(email);

                }
            }catch (RuntimeException e){
                throw new RuntimeException();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if (social.equals("naver")){
            email=oAuth2Attribute.getEmail();
            List<User> findMember = userRepository.getUserByEmail(email);
            log.info("find memeber : {}", findMember);
            log.info("email now is : {}", email);
            User user;
            try{
                if (findMember.isEmpty()) {
                    log.info("???????????? ??????! ???????????? ??????");
                    log.info("{}", oAuth2Attribute.getAttributeKey());
                    user = new User(email, NickName(), oAuth2Attribute.getProvider());

                    userRepository.save(user);

                    // ????????? ?????? ????????? ????????? ??????
                    UserCreateRequest userCreateRequest = new UserCreateRequest();
                    userCreateRequest.setEmail(email);
                    userCreateRequest.setProvider("naver");
                    log.info("######{}######",userCreateRequest.getEmail());
                    log.info("######{}######",userCreateRequest.getProvider());
                    summaryService.createSummary(userCreateRequest);

                }
                else{
                    user=userRepository.getUserByEmailandSocial(email,social);
                    log.info("??? ????????????.......{}", user);
                    if (user==null){
                        log.info("???????????? ??????! ???????????? ??????");
                        log.info("{}", oAuth2Attribute.getAttributeKey());
                        user = new User(email,  NickName(),oAuth2Attribute.getProvider());
                        userRepository.save(user);

                        // ????????? ?????? ????????? ????????? ??????
                        UserCreateRequest userCreateRequest = new UserCreateRequest();
                        userCreateRequest.setEmail(email);
                        userCreateRequest.setProvider("naver");
                        summaryService.createSummary(userCreateRequest);

                    }
                    log.info("????????? ????????? ????????????");
                }
            }catch (RuntimeException e){
                throw new RuntimeException();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            // ????????? ??????
                email=oAuth2Attribute.getEmail();
                log.info("email : {}", email);
                User findMember = userRepository.getUserByEmailandSocial(email, social);
                log.info("now user is : {}", findMember);
                User user;
                try{
                    if (findMember==null) {
                        log.info("???????????? ??????! ???????????? ??????");
                        log.info("??????????????? : {}", oAuth2Attribute.getProvider());
                        user = new User(email, NickName(), oAuth2Attribute.getProvider());
                        userRepository.save(user);
                    }
                    else{
                        user=userRepository.getUserByEmailandSocial(email,social);
                        log.info("??? ????????????.......{}", user);
                        if (user==null){
                            log.info("???????????? ??????! ???????????? ??????");
                            log.info("{}", oAuth2Attribute.getAttributeKey());
                            user = new User(email, NickName(), oAuth2Attribute.getProvider());
                            userRepository.save(user);
                        }
                        log.info("????????? ????????? ????????????");
                    }
                }
                catch (RuntimeException e){
                    throw new RuntimeException();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }


        var memberAttribute = oAuth2Attribute.convertToMap();
        log.info("member Attrubute : {}", memberAttribute);
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                memberAttribute, "email");
    }


//    private User saveOrUpdate(OAuth2Attribute oAuth2Attribute) {
////        Optional<User> findMember = userRepository.findByEmail(oAuth2Attribute.getEmail());
////        if (findMember.isPresent()){
////            User user=findMember.get();
////            user.updateRefreshToken(jwtTokenPri)
////
////        }
//        User user= (User) userRepository.findByEmail(oAuth2Attribute.getEmail())
//                .orElse((User) UserProfile.toUser(oAuth2Attribute.getName(),oAuth2Attribute.getEmail(),oAuth2Attribute.getPicture()));
//        return userRepository.save(user);
//    }
}
