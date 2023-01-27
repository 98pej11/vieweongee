package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.meeting.request.MeetingScoreRequest;

import java.util.List;

public interface MeetingScoreService {
    //스터디 참가자의 채점표 불러오기
    List<MeetingScoreRequest> getAllScore(String studyId);

    //면접자의 하나의 스터디 채점표 갱신
    void updateScore(String studyId, MeetingScoreRequest score);
}
