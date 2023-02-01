package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.meeting.request.MeetingScoreRequest;

import java.util.Date;
import java.util.List;

public interface MeetingService {
    //스터디 참가 상태 변경
    void updateStudyProgress(String studyId);

    //스터디 참가자의 채점표 불러오기
    List<MeetingScoreRequest> getAllScore(String studyId);

    //면접자의 하나의 스터디 채점표 갱신
    void updateScore(String studyId, MeetingScoreRequest score);

    //스터디 채점표 생성
    boolean makeScorecard(String studyId);

    //스터디 모의 면접 시작 시간 갱신
    String getStudyStartTimeAndRunningTime(String studyId);

    //면접 순서 지정
    String makeStudyOrder(String studyId);
}
