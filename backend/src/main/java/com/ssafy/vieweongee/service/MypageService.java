package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.mypage.request.InquireTypeRequest;
import com.ssafy.vieweongee.entity.Progress;
import com.ssafy.vieweongee.entity.Scorecard;
import com.ssafy.vieweongee.entity.Study;
import com.ssafy.vieweongee.entity.User;

import java.util.List;

public interface MypageService {
    String findUserType(Long id);
    List<Progress> findMyStudyList(Long userId);
    Study findStudyList(Long studyId);
    Scorecard findFeedback(Long userId, Long studyId);
}
