package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.meeting.MeetingRatioRequest;
import com.ssafy.vieweongee.dto.meeting.MeetingResumeRequest;
import com.ssafy.vieweongee.dto.meeting.MeetingScoreRequest;

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

    //면접 순서 지정
    String makeStudyOrder(String studyId, MeetingRatioRequest meetingRatioRequest);

    // 스터디 참가자의 자기소개서 불러오기
    List<MeetingResumeRequest> getAllResume(Long studyId);

    // study confirm, progress status 변경
    void updateConfirmAndStatus(Long studyId, Long userId);

    void updateSummary(Long studyId, Long userId);
}
