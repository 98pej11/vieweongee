package com.ssafy.vieweongee.controller;

import com.ssafy.vieweongee.dto.mypage.request.InquireTypeRequest;
import com.ssafy.vieweongee.dto.mypage.request.MyStudyFeedbackRequest;
import com.ssafy.vieweongee.dto.mypage.response.AbilitySummaryResponse;
import com.ssafy.vieweongee.dto.mypage.response.MyStudyListResponse;
import com.ssafy.vieweongee.dto.mypage.response.ScorecardResponse;
import com.ssafy.vieweongee.dto.mypage.response.TurnSummaryResponse;
import com.ssafy.vieweongee.entity.*;
import com.ssafy.vieweongee.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")
public class MypageController {
    @Autowired
    MypageService mypageService;

    //사용자 타입 조회
    @GetMapping("/type")
    public ResponseEntity<?> inquireType(@RequestBody InquireTypeRequest userInfo){
        String type = mypageService.findUserType(userInfo.getId());
        Map<String, String> result = new HashMap<>();
        result.put("data", type);
        result.put("message", "SUCCESS");
        return ResponseEntity.status(200).body(result);
    }

    //참여 스터디 여러개 조회
    @GetMapping("/mystudy")
    public ResponseEntity<?> myStudyList(@RequestBody InquireTypeRequest userInfo){
        List<Progress> myStudyList = mypageService.findMyStudyList(userInfo.getId());
        List<MyStudyListResponse> studyList = new ArrayList<>();

        for(Progress progress : myStudyList){
            Study study = mypageService.findStudyList(progress.getProgress_id().getStudy().getId());
            MyStudyListResponse myStudy = new MyStudyListResponse(study.getId(), study.getTitle(), study.getCompany(), study.getJob(), study.getStudy_datetime(), study.getRunning_time(), progress.isStatus());
            studyList.add(myStudy);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("data", studyList);
        if(studyList.size() == 0){
            result.put("message", "EMPTY");
            return ResponseEntity.status(204).body(result);
        }
        else{
            result.put("message", "SUCCESS");
            return ResponseEntity.status(200).body(result);
        }
    }

    //참여 스터디 1개 조회 - 피드백 / 예상 스터디
    @GetMapping("/mystudy/{study_id}")
    public ResponseEntity<?> myStudyFeedback (@PathVariable("study_id") String studyId, @RequestBody InquireTypeRequest userInfo){
        ScorecardResponse feedback = mypageService.calFeedback(userInfo.getId(), Long.parseLong(studyId));
        Map<String, Object> result = new HashMap<>();
        if(feedback != null){
            result.put("data", feedback);
            result.put("message", "SUCCESS");
            return ResponseEntity.status(200).body(result);
        }
        return ResponseEntity.status(204).body("EMPTY"); //스터디 링크로 가야함

    }

    //회차별 통계 조회
    @GetMapping("/turn")
    public ResponseEntity<?> turnStatistics(@RequestBody InquireTypeRequest userInfo){
        List<Progress> studiedList = mypageService.findStudiedList(userInfo.getId());
        Map<String, Object> result = new HashMap<>();
        if(studiedList == null){
            result.put("data", studiedList);
            result.put("message", "EMPTY"); //완료된 스터디 없을 때
            return ResponseEntity.status(204).body(result);
        }
        else{
            List<Study> studied = new ArrayList<>();
            for(Progress progress : studiedList){
                Study study = mypageService.findStudyList(progress.getProgress_id().getStudy().getId());
                studied.add(study);
            }
            // 스터디 시작 시간으로 정렬
            Collections.sort(studied, new Comparator<Study>(){
                @Override
                public int compare(Study o1, Study o2) {
                    return o1.getStudy_datetime().compareTo(o2.getStudy_datetime());
                }
            });

            List<TurnSummaryResponse> response = new ArrayList<>();
            float total_average = 0;
            for(Study study : studied){
                Scorecard scorecard = mypageService.findFeedback(userInfo.getId(), study.getId());

                int count = 0;
                if(scorecard.getAttitude() != 0) {
                    float attitude_average = (float) (Math.round((float)scorecard.getAttitude() / (float)scorecard.getInterviewer()*100)/100.0);
                    total_average += attitude_average;
                    count++;
                }
                if(scorecard.getAbility() != 0) {
                    float ability_average = (float) (Math.round((float)scorecard.getAbility() / (float)scorecard.getInterviewer()*100)/100.0);
                    total_average += ability_average;
                    count++;
                }
                if(scorecard.getTeamwork() != 0) {
                    float teamwork_average = (float) (Math.round((float)scorecard.getTeamwork() / (float)scorecard.getInterviewer()*100)/100.0);
                    total_average += teamwork_average;
                    count++;
                }
                if(scorecard.getSolving() != 0) {
                    float solving_average = (float) (Math.round((float)scorecard.getSolving() / (float)scorecard.getInterviewer()*100)/100.0);
                    total_average += solving_average;
                    count++;
                }
                if(scorecard.getLoyalty() != 0) {
                    float loyalty_average = (float) (Math.round((float)scorecard.getLoyalty() / (float)scorecard.getInterviewer()*100)/100.0);
                    total_average += loyalty_average;
                    count++;
                }

//                System.out.println("total_average: "+total_average +" count: "+count);
                total_average = (float) (Math.round(total_average / (float)count *100) / 100.0);
                TurnSummaryResponse summary = new TurnSummaryResponse(study.getId(), study.getTitle(), study.getStudy_datetime(), total_average);
                response.add(summary);
            }
            result.put("data", response);
            result.put("message", "SUCCESS");
            return ResponseEntity.status(200).body(result);
        }//else

    }

    //역량별 통계 조회
    @GetMapping("/graph")
    public ResponseEntity<?> abilityStatistics(@RequestBody InquireTypeRequest userinfo){
        Summary summary = mypageService.getAbilitySummary(userinfo.getId());
        AbilitySummaryResponse ability = new AbilitySummaryResponse(summary.getAttitude_average(), summary.getAbility_average(), summary.getTeamwork_average(), summary.getSolving_average(), summary.getLoyalty_average());
        Map<String, Object> result = new HashMap<>();
        result.put("data", ability);
        result.put("message", "SUCCESS");
        return ResponseEntity.status(200).body(result);
    }

    //참여 예정 스터디 조회(곧참스)
    @GetMapping("/mystudy/upcoming")
    public ResponseEntity<?> upcomingStudy(@RequestBody InquireTypeRequest userInfo){
        List<MyStudyListResponse> upcomingStudyList = mypageService.findUpcomingStudyList(userInfo.getId());
        Map<String, Object> result = new HashMap<>();
        if(upcomingStudyList == null){
            result.put("data", upcomingStudyList);
            result.put("message", "EMPTY");
            return ResponseEntity.status(204).body(result);
        }
        else{
            //정렬
            Collections.sort(upcomingStudyList, new Comparator<MyStudyListResponse>(){
                @Override
                public int compare(MyStudyListResponse o1, MyStudyListResponse o2) {
                    return o1.getStudy_datetime().compareTo(o2.getStudy_datetime());
                }
            });

            result.put("data", upcomingStudyList);
            result.put("message", "SUCCESS");
            return ResponseEntity.status(200).body(result);
        }
    }

    //알림 전체 조회
    @GetMapping("/alarm")
    public ResponseEntity<?> getAlarms(@RequestBody InquireTypeRequest userInfo){
        List<Alarm> alarms = mypageService.getAlarms(userInfo.getId());
        Map<String, Object> result = new HashMap<>();
        if(alarms != null){
            result.put("data", alarms);
            result.put("message", "SUCCESS");
            return ResponseEntity.status(200).body(result);
        }

        result.put("data", alarms);
        result.put("message", "EMPTY");
        return ResponseEntity.status(204).body(result);
    }

    //알림 읽음 처리
    @PutMapping("/alarm")
    public ResponseEntity<?> readAlarms(@RequestBody InquireTypeRequest userInfo){
        mypageService.readAlarms(userInfo.getId());
        return ResponseEntity.status(200).body("SUCCESS");
    }
}
