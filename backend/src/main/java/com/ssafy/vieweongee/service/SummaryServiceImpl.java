package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.user.request.UserCreateRequest;
import com.ssafy.vieweongee.entity.Summary;
import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.repository.SummaryRepository;
import com.ssafy.vieweongee.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class SummaryServiceImpl implements SummaryService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    SummaryRepository summaryRepository;
    @Override
    public void createSummary(UserCreateRequest user) {
        log.info("크리에이트 드러왔더요, 이메일 : {}, 프로바이더 : {}", user.getEmail(), user.getProvider());
//        User dbUser=userRepository.findByEmailAndProvider(user.getEmail(), user.getProvider());
        User dbUser = userRepository.getUserByEmailandSocial(user.getEmail(),"global");
        log.info("DB user in summary {}",dbUser.getName());

        Summary newSum;
        newSum = new Summary(dbUser);
        log.info("---------{}---------",newSum.getId());
        // 둘 중 하나로 하면 잘됩니당
        summaryRepository.save(newSum);
//        Summary newSum = summaryRepository.save(Summary.builder().user(dbUser).build());

    }
}
