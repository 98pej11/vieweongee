package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.user.request.UserCreateRequest;
import com.ssafy.vieweongee.entity.Summary;
import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.repository.SummaryRepository;
import com.ssafy.vieweongee.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
public class SummaryServiceImpl implements SummaryService{


    private final UserRepository userRepository;

    private final SummaryRepository summaryRepository;

    public SummaryServiceImpl(UserRepository userRepository, SummaryRepository summaryRepository) {
        this.userRepository = userRepository;
        this.summaryRepository = summaryRepository;
    }

//    public SummaryServiceImpl(UserRepository userRepository, SummaryRepository summaryRepository) {
//        this.userRepository = userRepository;
//        this.summaryRepository = summaryRepository;
//    }

    @Override
    public void createSummary(UserCreateRequest user) {
        log.info("크리에이트 드러왔더요, 이메일 : {}, 프로바이더 : {}", user.getEmail(), user.getProvider());
//        User dbUser=userRepository.findByEmailAndProvider(user.getEmail(), user.getProvider());
        User dbUser = userRepository.getUserByEmailandSocial(user.getEmail(),user.getProvider());
        log.info("DB user in summary {} and {}",dbUser.getName(), dbUser.getId());

        Summary newSum;
        newSum = new Summary(dbUser);
        log.info("---------{}---------",newSum.getUser().getId());
//        log.info(newSum.getId().toString());
        // 둘 중 하나로 하면 잘됩니당
        summaryRepository.save(newSum);
//        Summary newSum = summaryRepository.save(Summary.builder().user(dbUser).build());

    }
}
