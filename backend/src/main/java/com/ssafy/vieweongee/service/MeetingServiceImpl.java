package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.entity.*;
import com.ssafy.vieweongee.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService{

    private final MeetingScoreRepository meetingScoreRepository;
    private final MeetingParticipantRepository meetingParticipantRepository;
    private final UserRepository userRepository;
    private final StudyRepository studyRepository;

    @Autowired
    public MeetingServiceImpl(MeetingScoreRepository meetingScoreRepository, MeetingParticipantRepository meetingParticipantRepository, UserRepository userRepository, StudyRepository studyRepository) {
        this.meetingScoreRepository = meetingScoreRepository;
        this.meetingParticipantRepository = meetingParticipantRepository;
        this.userRepository = userRepository;
        this.studyRepository = studyRepository;
    }

    /**
     * 스터디 채점표 생성
     * @param studyId
     */
    @Transactional
    @Override
    public boolean makeScorecard(String studyId) {
        //스터디 아이디로 참가자 명단 가져오고
        List<Participant> participants = meetingParticipantRepository.findAllByStudy_id(Long.parseLong(studyId));
        //그 명단에서 회원 아이디 다 받아옴
        for(Participant p : participants){
            Long user_id = p.getParticipant_id().getUser().getId();

            //스터디 아이디와 회원 아이디로 ScorecardId 만들어줌
            User user = userRepository.findById(user_id)
                    .orElseThrow(() -> new IllegalArgumentException("no such data"));

            Study study = studyRepository.findById(Long.parseLong(studyId))
                    .orElseThrow(() -> new IllegalArgumentException("no such data"));

            ScorecardId scorecardId = ScorecardId.builder()
                    .user(user)
                    .study(study)
                    .build();

            Scorecard scorecard = Scorecard.builder()
                    .score_id(scorecardId)
                    .build();

            meetingScoreRepository.save(scorecard);
        }
        return true;
    }

    @Transactional
    @Override
    public String updateStudyStartTime(String studyId) {
//        //스터디 아이디로 미팅 찾음
//        Meeting meeting = meetingRepository.findById(Long.parseLong(studyId))
//                .orElseThrow(() -> new IllegalArgumentException("no such data"));
//
//        //현재 시간으로 갱신
//        LocalDateTime now = LocalDateTime.now();
//        meeting.updateStartTime(now);
//
//        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        return null;
    }

    @Override
    public String makeStudyOrder(String studyId) {
        return null;
    }
}
