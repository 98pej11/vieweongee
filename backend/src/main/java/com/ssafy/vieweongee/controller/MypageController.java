package com.ssafy.vieweongee.controller;

import com.ssafy.vieweongee.dto.mypage.request.InquireTypeRequest;
import com.ssafy.vieweongee.dto.mypage.request.MyStudyFeedbackRequest;
import com.ssafy.vieweongee.dto.mypage.response.AbilitySummaryResponse;
import com.ssafy.vieweongee.dto.mypage.response.MyStudyListResponse;
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
        result.put("message", "사용자의 타입을 찾았습니다.");
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
            result.put("message", "일정이 없습니다.");
            return ResponseEntity.status(200).body(result);
        }
        else{
            result.put("message", "내 스터디 목록 반환 성공");
            return ResponseEntity.status(200).body(result);
        }
    }

    //참여 스터디 1개 조회 - 피드백 / 예상 스터디
    @GetMapping("/mystudy/{study_id}")
    public ResponseEntity<?> myStudyFeedback (@PathVariable("study_id") String studyId, @RequestBody InquireTypeRequest userInfo){
        Scorecard feedback = mypageService.findFeedback(userInfo.getId(), Long.parseLong(studyId));
        Map<String, Object> result = new HashMap<>();
        if(feedback != null){
            result.put("data", feedback);
            result.put("message", "SUCCESS");
            return ResponseEntity.status(200).body(result);
        }
        return ResponseEntity.status(201).body("진행 예정인 스터디입니다."); //스터디 링크로 가야함

    }

    //회차별 통계 조회
    @GetMapping("/turn")
    public ResponseEntity<?> turnStatistics(@RequestBody InquireTypeRequest userInfo){
        List<Progress> studiedList = mypageService.findStudiedList(userInfo.getId());
        Map<String, Object> result = new HashMap<>();
        if(studiedList == null){
            result.put("data", studiedList);
            result.put("message", "스터디 이력이 없습니다."); //완료된 스터디 없을 때
            return ResponseEntity.status(201).body(result);
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
        List<Study> upcomingStudyList = mypageService.findUpcomingStudyList(userInfo.getId());
        Map<String, Object> result = new HashMap<>();
        if(upcomingStudyList == null){
            result.put("data", upcomingStudyList);
            result.put("message", "참여 예정 스터디가 없습니다.");
            return ResponseEntity.status(201).body(result);
        }
        else{
            List<MyStudyListResponse> willStudy = new ArrayList<>();
            for(Study study : upcomingStudyList){
                MyStudyListResponse myStudy = new MyStudyListResponse(
                        study.getId(),
                        study.getTitle(),
                        study.getCompany(),
                        study.getJob(),
                        study.getStudy_datetime(),
                        study.getRunning_time(),
                        false);
                willStudy.add(myStudy);
            }

            //정렬
            Collections.sort(willStudy, new Comparator<MyStudyListResponse>(){
                @Override
                public int compare(MyStudyListResponse o1, MyStudyListResponse o2) {
                    return o1.getStudy_datetime().compareTo(o2.getStudy_datetime());
                }
            });

            result.put("data", willStudy);
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
        result.put("message", "알람이 없습니다.");
        return ResponseEntity.status(201).body(result);
    }

    //알림 읽음 처리
    @PutMapping("/alarm")
    public ResponseEntity<?> readAlarms(@RequestBody InquireTypeRequest userInfo){
        mypageService.readAlarms(userInfo.getId());
        return ResponseEntity.status(200).body("알림 읽기 완료!");
    }
}
