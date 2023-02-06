package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.mypage.request.InquireTypeRequest;
import com.ssafy.vieweongee.dto.mypage.response.MyStudyListResponse;
import com.ssafy.vieweongee.dto.mypage.response.ScorecardResponse;
import com.ssafy.vieweongee.entity.*;

import java.util.List;

public interface MypageService {
    String findUserType(Long id);
    List<Progress> findMyStudyList(Long userId);
    Study findStudyList(Long studyId);
    Scorecard findFeedback(Long userId, Long studyId);
    ScorecardResponse calFeedback(Long userId, Long studyId);
    Summary getAbilitySummary(Long userId);
    List<Progress> findStudiedList(Long userId);
    List<MyStudyListResponse> findUpcomingStudyList(Long userId);
    List<Alarm> getAlarms(Long userId);
    void readAlarms(Long userId);

}
