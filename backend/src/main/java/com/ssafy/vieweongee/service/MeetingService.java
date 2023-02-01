package com.ssafy.vieweongee.service;

import java.util.Date;

public interface MeetingService {
    //스터디 채점표 생성
    boolean makeScorecard(String studyId);
    //스터디 모의 면접 시작 시간 갱신
    String updateStudyStartTime(String studyId);
    //면접 순서 지정
    String makeStudyOrder(String studyId);
}
