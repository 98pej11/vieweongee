package com.ssafy.vieweongee.controller;

import com.ssafy.vieweongee.dto.mypage.request.InquireTypeRequest;
import com.ssafy.vieweongee.dto.mypage.request.MyStudyFeedbackRequest;
import com.ssafy.vieweongee.dto.mypage.response.MyStudyListResponse;
import com.ssafy.vieweongee.entity.Progress;
import com.ssafy.vieweongee.entity.Scorecard;
import com.ssafy.vieweongee.entity.Study;
import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class MypageController {
    @Autowired
    MypageService mypageService;

    //사용자 타입 조회 -> 필요할까...?
    @GetMapping("/type")
    public ResponseEntity<?> inquireType(@RequestBody InquireTypeRequest userInfo){
        String type = mypageService.findUserType(userInfo.getId());
        if(type == null)
            return ResponseEntity.status(500).body("사용자가 존재하지 않습니다");
        Map<String, String> result = new HashMap<>();
        result.put("data", type);
        result.put("message", "사용자의 타입을 찾았습니다.");
        return ResponseEntity.status(200).body(result);
    }

    //참여 스터디 여러개 조회
    @GetMapping("/mystudy")
    public ResponseEntity<?> myStudyList(@RequestBody Long userId){
        List<Progress> myStudyList = mypageService.findMyStudyList(userId);
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
    public ResponseEntity<?> myStudyFeedback (@PathVariable("study_id") @RequestBody MyStudyFeedbackRequest info){
        Scorecard feedback = mypageService.findFeedback(info.getUser_id(), info.getStudy_id());
        Map<String, Object> result = new HashMap<>();
        if(feedback != null){
            result.put("data", feedback);
            result.put("message", "SUCCESS");
            return ResponseEntity.status(200).body(result);
        }
        return ResponseEntity.status(201).body("진행 예정인 스터디입니다.");

    }

    //회차별 통계 조회
//    @GetMapping("/turn")
//    public ResponseEntity<?> turnStatistics(@RequestBody){
//
//    }

    //역량별 통계 조회
//    @GetMapping("graph")
//    public ResponseEntity<?> abilityStatistics(@RequestBody){
//
//    }

    //참여 예정 스터디 조회(곧참스)
//    @GetMapping("/mystudy/upcoming")
//    public ResponseEntity<?> upcomingStudy(@RequestBody){
//
//    }

    //알림 전체 조회


    //알림 읽음 처리

}
