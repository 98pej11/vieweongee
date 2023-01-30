package com.ssafy.vieweongee.service;

public interface MeetingProgressService {

    /**
     * 스터디 참가 상태를 변경 (예정 -> 완료)
     * @param studyId
     */
    void updateStudyProgress(String studyId);
}
