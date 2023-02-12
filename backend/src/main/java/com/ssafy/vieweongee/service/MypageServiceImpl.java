package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.mypage.response.MyStudyListResponse;
import com.ssafy.vieweongee.dto.mypage.response.ScorecardResponse;
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
    public ScorecardResponse calFeedback(Long userId, Long studyId) {
        Scorecard feedback = scorecardRepository.findFeedback(userId, studyId);
        if(feedback != null){
            float attitude_average = 0;
            float ability_average = 0;
            float teamwork_average = 0;
            float solving_average = 0;
            float loyalty_average = 0;
            if(feedback.getAttitude() != 0)
                attitude_average = (float) (Math.round( (float)feedback.getAttitude() / (float)feedback.getInterviewer() *100) / 100.0);

            if(feedback.getAbility() != 0)
                ability_average = (float) (Math.round((float)feedback.getAbility() / (float)feedback.getInterviewer()*100)/100.0);

            if(feedback.getTeamwork() != 0)
                teamwork_average = (float) (Math.round((float)feedback.getTeamwork() / (float) feedback.getInterviewer()*100)/100.0);

            if(feedback.getSolving() != 0)
                solving_average = (float) (Math.round((float)feedback.getSolving() / (float) feedback.getInterviewer()*100)/100.0);

            if(feedback.getLoyalty() != 0)
                loyalty_average = (float)(Math.round((float) feedback.getLoyalty() / (float) feedback.getInterviewer()*100)/100.0);

            ScorecardResponse score = new ScorecardResponse(attitude_average, ability_average, teamwork_average, solving_average, loyalty_average, feedback.getFeedback());
            return score;
        }
        return null;
    }

    @Override
    public Summary getAbilitySummary(Long userId) {
        Summary summary = summaryRepository.findById(userId);
        return summary;
    }

    @Override
    public List<Progress> findStudiedList(Long userId) {
        List<Progress> studiedList = progressRepository.findByUser_idAndStatus(userId, true);
        return studiedList;
    }

    @Override
    public List<MyStudyListResponse> findUpcomingStudyList(Long userId) {
        List<Progress> upcomingStudyList = progressRepository.findByUser_idAndStatus(userId, false);
        System.out.println("size: "+upcomingStudyList.size());
        List<MyStudyListResponse> confirmedStudyList = new ArrayList<>();
        for(Progress progress : upcomingStudyList){
            Study study = studyRepository.findByIdAndConfirm(progress.getProgress_id().getStudy().getId(), true);
            if(study == null)
                continue;
            MyStudyListResponse myStudy = new MyStudyListResponse(study.getId(), study.getTitle(), study.getCompany(), study.getJob(), study.getStudy_datetime(), study.getRunning_time(), false);
            confirmedStudyList.add(myStudy);
        }

        return confirmedStudyList;
    }

    @Override
    public List<Alarm> getAlarms(Long userId) {
        List<Alarm> alarms = alarmRepository.findByUser_id(userId);

        if(alarms != null) {
            //최신 알람으로 정렬
            Collections.sort(alarms, new Comparator<Alarm>() {
                @Override
                public int compare(Alarm a1, Alarm a2) {
                    return a2.getDatetime().compareTo(a1.getDatetime());
                }
            });

            List<Alarm> latest = new ArrayList<>();
            if (alarms.size() >= 20) {
                int cnt = 0;
                for (Alarm alarm : alarms) {
                    if(cnt == 20)
                        break;
                    latest.add(alarm);
                    cnt++;
                }
                return latest;
            } else if (alarms.size() > 0)
                for (Alarm alarm : alarms) {
                    latest.add(alarm);
                }
                return latest;
        }

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
