package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.meeting.request.MeetingScoreRequest;
import com.ssafy.vieweongee.entity.Scorecard;
import com.ssafy.vieweongee.entity.ScorecardId;
import com.ssafy.vieweongee.entity.Study;
import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.repository.MeetingScoreRepository;
import com.ssafy.vieweongee.repository.StudyRepository;
import com.ssafy.vieweongee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeetingScoreServiceImpl implements MeetingScoreService{

    private final MeetingScoreRepository meetingScoreRepository;
    private final UserRepository userRepository;
    private final StudyRepository studyRepository;

    @Autowired
    public MeetingScoreServiceImpl(MeetingScoreRepository meetingScoreRepository, UserRepository userRepository, StudyRepository studyRepository) {
        this.meetingScoreRepository = meetingScoreRepository;
        this.userRepository = userRepository;
        this.studyRepository = studyRepository;
    }

    /**
     * 스터디 참가자의 채점표 불러오기
     * @param studyId
     * @return List<MeetingScoreRequest>
     */
    @Override
    public List<MeetingScoreRequest> getAllScore(String studyId) {
        //스터디 아이디로 해당되는 채점표 전부 가져오기
        List<Scorecard> list = meetingScoreRepository.findAllByStudy_id(Long.parseLong(studyId));

        List<MeetingScoreRequest> result = new ArrayList<>();

        for(Scorecard scorecard : list){
            MeetingScoreRequest temp = new MeetingScoreRequest(
                    scorecard.getScore_id().getUser().getId()
                    ,scorecard.getAttitude()
                    ,scorecard.getAbility()
                    ,scorecard.getTeamwork()
                    ,scorecard.getSolving()
                    ,scorecard.getLoyalty()
                    ,scorecard.getFeedback()
                    );

            result.add(temp);
        }

        return result;
    }

    /**
     * 스터디의 면접자 1명의 채점표의 점수를 갱신
     * @param studyId
     * @param score
     */
    @Transactional
    @Override
    public void updateScore(String studyId, MeetingScoreRequest score) {
        //스터디 아이디와 회원 아이디로 ScorecardId 만들어줌
        User user = userRepository.findById(score.getId())
                .orElseThrow(() -> new IllegalArgumentException("no such data"));

        Study study = studyRepository.findById(Long.parseLong(studyId))
                .orElseThrow(() -> new IllegalArgumentException("no such data"));

        ScorecardId scorecardId = ScorecardId.builder()
                .user(user)
                .study(study)
                .build();

        //얘로 채점표를 찾음
        Scorecard scorecard = meetingScoreRepository.findById(scorecardId)
                .orElseThrow(() -> new IllegalArgumentException("no such data"));

        //엔티티의 점수 변경 메서드 실행해줌
        scorecard.changeScore(score);
    }



}
