package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.meeting.MeetingRatioRequest;
import com.ssafy.vieweongee.dto.meeting.MeetingResumeRequest;
import com.ssafy.vieweongee.dto.meeting.MeetingScoreRequest;
import com.ssafy.vieweongee.entity.*;
import com.ssafy.vieweongee.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {

    private final ScoreRepository meetingScoreRepository;
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final StudyRepository studyRepository;
    private final ProgressRepository progressRepository;

    @Autowired
    public MeetingServiceImpl(ScoreRepository meetingScoreRepository, ParticipantRepository participantRepository, UserRepository userRepository, StudyRepository studyRepository, ProgressRepository progressRepository) {
        this.meetingScoreRepository = meetingScoreRepository;
        this.participantRepository = participantRepository;
        this.userRepository = userRepository;
        this.studyRepository = studyRepository;
        this.progressRepository = progressRepository;
    }

    /**
     * 스터디 참가 상태를 변경 (예정 -> 완료)
     *
     * @param studyId
     */
    @Transactional
    @Override
    public void updateStudyProgress(String studyId) {
        //스터디 아이디로 스터디 참가 이력 가져옴
        List<Progress> list = progressRepository.findAllByStudyId(Long.parseLong(studyId));

        //스터디 참가 상태 변경 (예정 >> 완료)
        for (Progress progress : list) {
            progress.changeStatusToTrue();
        }
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
        List<Scorecard> list = meetingScoreRepository.findAllByStudyId(Long.parseLong(studyId));

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
     * 스터디의 참가자들의 자기소개서 불러옴
     *
     * @param studyId
     */
    @Override
    public List<MeetingResumeRequest> getAllResume(Long studyId) {
        List<Participant> resumes = participantRepository.findAllByStudyId(studyId);
        List<MeetingResumeRequest> result=new ArrayList<>();

        for (Participant participant : resumes){
            MeetingResumeRequest temp=new MeetingResumeRequest(
                    participant.getParticipant_id().getUser().getId()
                    , participant.getSave()
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
                .orElseThrow(() -> new IllegalArgumentException("no user data"));

        Study study = studyRepository.findById(Long.parseLong(studyId))
                .orElseThrow(() -> new IllegalArgumentException("no study data"));

        ScorecardId scorecardId = ScorecardId.builder()
                .user(user)
                .study(study)
                .build();

        //얘로 채점표를 찾음
        Scorecard scorecard = meetingScoreRepository.findById(scorecardId)
                .orElseThrow(() -> new IllegalArgumentException("no score data"));

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
        List<Participant> participants = participantRepository.findAllByStudyId(Long.parseLong(studyId));
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
     * 면접자 순서 설정
     *
     * @param studyId
     * @param meetingRatioRequest
     * @return 면접자 순서 문자열
     */
    @Override
    public String makeStudyOrder(String studyId, MeetingRatioRequest meetingRatioRequest) {
        //결과를 위한 Stringbuilder
        //ex) 3[0(1{1(3{2(5
        //ex) 3!0=1&1=1&2=2
        //ex) 3[0(1,2{1(3,4{2(5,6
        //ex) 3!0=1,2&1=3,4&2=5,6
        StringBuilder result = new StringBuilder();

        //스터디 아이디로 참가자 리스트 가져옴
        List<Participant> participants = participantRepository.findAllByStudyId(Long.parseLong(studyId));

        for(Participant p : participants){
            System.out.println("참자가 아이디 >> " + p.getParticipant_id().getUser().getId());
        }

        //스터디 참가자 수 확인
        int totalCnt = participants.size();

        //면접자 수 확인
        int intervieweeCnt = meetingRatioRequest.getInterviewee();

//        System.out.println("전체수, 면접자" + totalCnt + " " + intervieweeCnt);

        //면접 회차 저장
        int turn = totalCnt % intervieweeCnt == 0 ? totalCnt / intervieweeCnt : totalCnt / intervieweeCnt + 1;
        result.append(turn).append("!");


        //비율 확인
        if (intervieweeCnt == 1) { //1:1, 1:N
            //0 1 2 3 4 5
            for (int i = 0; i < totalCnt; i++) {
                if (i != 0) {
                    result.append("&");
                }
                result.append(i).append("=").append(participants.get(i).getParticipant_id().getUser().getId());
            }

            return result.toString();
        }

        //N:M
        //6 | 1:5 | 2:4 3:3 4:2
        if (totalCnt == 6) {
            if (intervieweeCnt == 2) {
                //0 1 | 2 3 | 4 5
                int cnt = 0;
                for (int i = 0; i < totalCnt; i += 2) {
                    if (i != 0) {
                        result.append("&");
                    }
                    result.append(cnt++).append("=");
                    for (int j = 0; j < intervieweeCnt; j++) {
                        result.append(participants.get(i + j).getParticipant_id().getUser().getId());
                        if (j == 0) {
                            result.append(",");
                        }
                    }
                }
            } else if (intervieweeCnt == 3) {
                //012 | 345
                int cnt = 0;
                for (int i = 0; i < totalCnt; i += 3) {
                    if (i == 3) {
                        result.append("&");
                    }
                    result.append(cnt++).append("=");
                    for (int j = 0; j < intervieweeCnt; j++) {
                        result.append(participants.get(i + j).getParticipant_id().getUser().getId());
                        if (j != 2 && j != 5) {
                            result.append(",");
                        }
                    }
                }
            } else if (intervieweeCnt == 4) {
                //0123 | 45
                result.append(0).append("=");
                for (int i = 0; i < 4; i++) {
                    result.append(participants.get(i).getParticipant_id().getUser().getId());
                    if (i != 3) {
                        result.append(",");
                    }
                }
                result.append("&").append(1).append("=");
                result.append(participants.get(4).getParticipant_id().getUser().getId()).append(",");
                result.append(participants.get(5).getParticipant_id().getUser().getId());
            }
            return result.toString();
        }

        //5 | 1:4 | 2:3 3:2
        if (totalCnt == 5) {
            if (intervieweeCnt == 2) {
                //01 | 23 | 4
                result.append(0).append("=");
                result.append(participants.get(0).getParticipant_id().getUser().getId()).append(",");
                result.append(participants.get(1).getParticipant_id().getUser().getId());
                result.append("&").append(1).append("=");
                result.append(participants.get(2).getParticipant_id().getUser().getId()).append(",");
                result.append(participants.get(3).getParticipant_id().getUser().getId());
                result.append("&").append(2).append("=");
                result.append(participants.get(4).getParticipant_id().getUser().getId());
            } else if (intervieweeCnt == 3) {
                //012 | 34
                result.append(0).append("=");
                result.append(participants.get(0).getParticipant_id().getUser().getId()).append(",");
                result.append(participants.get(1).getParticipant_id().getUser().getId()).append(",");
                result.append(participants.get(2).getParticipant_id().getUser().getId());
                result.append("&").append(1).append("=");
                result.append(participants.get(3).getParticipant_id().getUser().getId()).append(",");
                result.append(participants.get(4).getParticipant_id().getUser().getId());
            }
            return result.toString();
        }
        //4 | 1:3 | 2:2
        if (totalCnt == 4) {
            if (intervieweeCnt == 2) {
                //01 | 23
                result.append(0).append("=");
                result.append(participants.get(0).getParticipant_id().getUser().getId()).append(",");
                result.append(participants.get(1).getParticipant_id().getUser().getId());
                result.append("&").append(1).append("=");
                result.append(participants.get(2).getParticipant_id().getUser().getId()).append(",");
                result.append(participants.get(3).getParticipant_id().getUser().getId());
            }
            return result.toString();
        }
        //3 | 1:2 |
        //2 | 1:1 |


        return null;
    }

}