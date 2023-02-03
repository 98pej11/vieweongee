package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.entity.*;
import com.ssafy.vieweongee.exception.StudyNotFoundException;
import com.ssafy.vieweongee.exception.UserNotFoundException;
import com.ssafy.vieweongee.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    private AlarmRepository alarmRepository;

    @Override
    public String findUserType(Long id) {
        User dbUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        return dbUser.getProvider();
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

    @Override
    public List<Progress> findStudiedList(Long userId) {
        List<Progress> studiedList = progressRepository.findByUser_idAndStatus(userId, true);
        return studiedList;
    }

    @Override
    public List<Study> findUpcomingStudyList(Long userId) {
        List<Progress> upcomingStudyList = progressRepository.findByUser_idAndStatus(userId, false);
        List<Study> confirmedStudyList = new ArrayList<>();
        for(Progress progress : upcomingStudyList){
            Study confirmedStudy = studyRepository.findByIdAndConfirm(progress.getProgress_id().getStudy().getId(), true);
            confirmedStudyList.add(confirmedStudy);
        }
        return confirmedStudyList;
    }

    @Override
    public List<Alarm> getAlarms(Long userId) {
        List<Alarm> alarms = alarmRepository.findByUser_id(userId);
        //최신 알람으로 정렬
        Collections.sort(alarms, new Comparator<Alarm>(){
            @Override
            public int compare(Alarm a1, Alarm a2){
                return a2.getDatetime().compareTo(a1.getDatetime());
            }
        });

        List<Alarm> latest = new ArrayList<>();
        if(alarms.size() >= 20){
            for(int i=0; i<20; i++)
            latest.add(alarms.get(i));
            return latest;
        }
        else if(alarms.size() > 0)
            return alarms;
        else
            return null;
    }

    @Override
    public void readAlarms(Long userId) {
        List<Alarm> alarms = alarmRepository.findByUser_idAndSee(userId, false);

        if(alarms.size() == 0)
            return;

        for(Alarm alarm : alarms){
            alarm.updateSee(true);
            alarmRepository.save(alarm);
        }
    }


}
