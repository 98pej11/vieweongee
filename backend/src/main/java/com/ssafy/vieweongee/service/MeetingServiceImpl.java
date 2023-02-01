package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.meeting.request.MeetingScoreRequest;
import com.ssafy.vieweongee.entity.*;
import com.ssafy.vieweongee.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingScoreRepository meetingScoreRepository;
    private final MeetingParticipantRepository meetingParticipantRepository;
    private final UserRepository userRepository;
    private final StudyRepository studyRepository;
    private final MeetingProgressRepository meetingProgressRepository;

    @Autowired
    public MeetingServiceImpl(MeetingScoreRepository meetingScoreRepository, MeetingParticipantRepository meetingParticipantRepository, UserRepository userRepository, StudyRepository studyRepository, MeetingProgressRepository meetingProgressRepository) {
        this.meetingScoreRepository = meetingScoreRepository;
        this.meetingParticipantRepository = meetingParticipantRepository;
        this.userRepository = userRepository;
        this.studyRepository = studyRepository;
        this.meetingProgressRepository = meetingProgressRepository;
    }

    /**
     * 스터디 참가 상태를 변경 (예정 -> 완료)
     *
     * @param studyId
     */
    @Transactional
    @Override
    public void updateStudyProgress(String studyId) {
        System.out.println(">>>>>> service 진입");
        //스터디 아이디로 스터디 참가 이력 가져옴
        List<Progress> list = meetingProgressRepository.findAllByStudy_id(Long.parseLong(studyId));
        System.out.println("스터디 참가 이력 size >> " + list.size());


        //스터디 참가 상태 변경 (예정 >> 완료)
        for (Progress progress : list) {
            progress.changeStatusToTrue();
        }
        System.out.println(">>> 스터디 참가 상태 변경 완료");
    }

    /**
     * 스터디 참가자의 채점표 불러오기
     *
     * @param studyId
     * @return List<MeetingScoreRequest>
     */
    @Override
    public List<MeetingScoreRequest> getAllScore(String studyId) {
        //스터디 아이디로 해당되는 채점표 전부 가져오기
        List<Scorecard> list = meetingScoreRepository.findAllByStudy_id(Long.parseLong(studyId));

        List<MeetingScoreRequest> result = new ArrayList<>();

        for (Scorecard scorecard : list) {
            MeetingScoreRequest temp = new MeetingScoreRequest(
                    scorecard.getScore_id().getUser().getId()
                    , scorecard.getAttitude()
                    , scorecard.getAbility()
                    , scorecard.getTeamwork()
                    , scorecard.getSolving()
                    , scorecard.getLoyalty()
                    , scorecard.getFeedback()
            );
            result.add(temp);
        }

        return result;
    }

    /**
     * 스터디의 면접자 1명의 채점표의 점수를 갱신
     *
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

    /**
     * 스터디 채점표 생성
     *
     * @param studyId
     */
    @Transactional
    @Override
    public boolean makeScorecard(String studyId) {
        //스터디 아이디로 참가자 명단 가져오고
        List<Participant> participants = meetingParticipantRepository.findAllByStudy_id(Long.parseLong(studyId));
        //그 명단에서 회원 아이디 다 받아옴
        for (Participant p : participants) {
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

    /**
     * 스터디 시작 시간, 진행 시간 찾기
     * @param studyId
     * @return
     */
    @Transactional
    @Override
    public String getStudyStartTimeAndRunningTime(String studyId) {

        return null;
    }

    @Override
    public String makeStudyOrder(String studyId) {
        return null;
    }
}
