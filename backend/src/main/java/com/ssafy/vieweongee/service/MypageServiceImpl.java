package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.entity.*;
import com.ssafy.vieweongee.exception.StudyNotFoundException;
import com.ssafy.vieweongee.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MypageServiceImpl implements MypageService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProgressRepository progressRepository;
    @Autowired
    private StudyRepository studyRepository;
    @Autowired
    private ScorecardRepository scorecardRepository;
    @Autowired
    private SummaryRepository summaryRepository;

    @Override
    public String findUserType(Long id) {
        User dbUser = userRepository.findById(id).get();
        if(dbUser != null)
            return dbUser.getProvider();
        return null;
    }

    @Override
    public List<Progress> findMyStudyList(Long userId) {
        List<Progress> myStudyList = progressRepository.findByUser_id(userId);
        return myStudyList;
    }

    @Override
    public Study findStudyList(Long studyId) {
        Study study = studyRepository.findById(studyId).orElseThrow(()->new StudyNotFoundException());
        return study;
    }

    @Override
    public Scorecard findFeedback(Long userId, Long studyId) {
        Scorecard feedback = scorecardRepository.findFeedback(userId, studyId);
        return feedback;
    }

    @Override
    public Summary getAbilitySummary(Long userId) {
        Summary summary = summaryRepository.findById(userId).get();
        return summary;
    }


}
