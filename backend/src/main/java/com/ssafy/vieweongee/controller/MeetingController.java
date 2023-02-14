package com.ssafy.vieweongee.controller;

import com.ssafy.vieweongee.dto.meeting.MeetingRatioRequest;
import com.ssafy.vieweongee.dto.meeting.MeetingResumeRequest;
import com.ssafy.vieweongee.dto.meeting.MeetingScoreRequest;
import com.ssafy.vieweongee.entity.Study;
import com.ssafy.vieweongee.service.MeetingService;
import com.ssafy.vieweongee.service.StudyService;
import com.ssafy.vieweongee.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/meeting")
public class MeetingController {

    private final MeetingService meetingService;
    private final StudyService studyService;
    private final TokenService tokenService;

    @Autowired
    public MeetingController(MeetingService meetingService, StudyService studyService, TokenService tokenService) {
        this.meetingService = meetingService;
        this.studyService = studyService;
        this.tokenService=tokenService;
    }

    // 스터디원 자기소개서 전부 가져오기 >> 서버 or 시그널

    /**
     * 스터디 채점표 생성
     *
     * @param study_ID
     * @return
     */
    @PostMapping("/{study_ID}/score")
    public ResponseEntity<?> startMeeting(@PathVariable("study_ID") String study_ID, @RequestHeader("ACCESS") String access) {
        Map<String, String> result = new HashMap<>();
        //해당 스터디가 존재하는지 확인
        Study study = studyService.getStudyDetail(Long.parseLong(study_ID));
        if (study == null) { //스터디 존재 안 할때
            result.put("data", "해당 스터디가 존재하지 않습니다.");
            result.put("message", "FAIL");
            return ResponseEntity.status(400).body(result);
        }

        //스터디 존재 하면 채점표 생성
        meetingService.makeScorecard(study_ID);
        result.put("data", "스터디 채점표 생성 완료");
        result.put("message", "SUCCESS");
        return ResponseEntity.ok().body(result);
    }

    /**
     * 스터디원의 채점표를 전부 불러옴
     *
     * @param study_ID
     * @return List<MeetingScoreRequest>
     */
    @GetMapping("/{study_ID}/score")
    public ResponseEntity<?> showAllScorecard(
            @PathVariable("study_ID") String study_ID, @RequestHeader("ACCESS") String access) {
        Map<String, Object> result = new HashMap<>();
        //스터디 찾음
        Study study = studyService.getStudyDetail(Long.parseLong(study_ID));
        if (study == null) { //스터디 존재 안 할때
            result.put("data", "해당 스터디가 존재하지 않습니다.");
            result.put("message", "FAIL");
            return ResponseEntity.status(400).body(result);
        }
        //채점표 찾음
        //채점표 없음 처리
        List<MeetingScoreRequest> score = meetingService.getAllScore(study_ID);
        if (score.size() == 0) { //생성된 채점표가 없을때
            result.put("message", "FAIL");
            result.put("data", "생성된 채점표가 없습니다.");
            return ResponseEntity.status(400).body(result);
        }
        result.put("message", "SUCCESS");
        result.put("data", score);
        return ResponseEntity.ok().body(result);
    }
    /**
     * 스터디원의 자소서를 전부 불러옴
     *
     * @param study_ID
     * @return List<MeetingScoreRequest>
     */
    @GetMapping("{study_ID}/resume")
    public ResponseEntity<?> showAllResume(@PathVariable("study_ID") Long study_ID, @RequestHeader("ACCESS") String access){
        Map<String, Object> result = new HashMap<>();
        List<MeetingResumeRequest> list = meetingService.getAllResume(study_ID);
        if(list.size() > 0){
            result.put("data", list);
            result.put("message", "SUCCESS");
            return ResponseEntity.ok().body(result);
        }
        result.put("message", "FAIL");
        result.put("data", "몰라요");
        return ResponseEntity.status(400).body(result);
    }
    /**
     * 면접자 1명의 채점표 갱신
     *
     * @param study_ID
     * @param meetingScoreRequest
     * @return
     */
    @PutMapping("/{study_ID}/score")
    public ResponseEntity<?> modifyScorecard(
            @PathVariable("study_ID") String study_ID,
            @RequestBody MeetingScoreRequest meetingScoreRequest) {
        Map<String, String> result = new HashMap<>();
        try {
            meetingService.updateScore(study_ID, meetingScoreRequest);

            //마이페이지에 통계 갱신 추가 후 정상 작동 확인 필요!!

            result.put("message", "SUCCESS");
            result.put("data", "채점표 갱신 완료");
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            result.put("message", "FAIL");
            result.put("data", "해당 데이터가 존재하지 않습니다.");
            return ResponseEntity.status(400).body(result);
        }


    }

    // 스터디 상태(진행/완료) 변경하기 >> 스터디에서 생성한 종료 시간이 됐을 때 >> Video 컨트롤러에서 처리 완료

    //스터디 순서 지정
    @PostMapping("/{study_ID}/order")
    public ResponseEntity<?> studyMeetingOrder(
            @PathVariable("study_ID") String study_ID,
            @RequestBody MeetingRatioRequest meetingRatioRequest,
            @RequestHeader("ACCESS") String access) {
        Map<String, String> result = new HashMap<>();
        //스터디 찾음
        Study study = studyService.getStudyDetail(Long.parseLong(study_ID));
        if (study == null) { //스터디 존재 안 할때
            result.put("data", "해당 스터디가 존재하지 않습니다.");
            result.put("message", "FAIL");
            return ResponseEntity.status(400).body(result);
        }
        //정상 작동 처리
        String order = meetingService.makeStudyOrder(study_ID, meetingRatioRequest);
        //참가자 없음
        if(order == null){
            result.put("data", "해당 참가자가 존재하지 않습니다.");
            result.put("message", "FAIL");
            return ResponseEntity.status(400).body(result);
        }
        result.put("data", order);
        result.put("message", "SUCCESS");
        return ResponseEntity.ok().body(result);
    }


    /**
     * study 확정(confirm) 변경, progress status 변경
     *
     * @param study_ID
     * @return
     */
    @PutMapping("/{study_ID}/status")
//    ResponseEntity<?> modifyConfirmAndStatus(@PathVariable("study_ID") String study_ID, @RequestHeader("ACCESS") String access){
    ResponseEntity<?> modifyConfirmAndStatus(@PathVariable("study_ID") String study_ID, @RequestBody String id){
//        Long userId = Long.parseLong(tokenService.getUid(access).replaceAll("\"",""));
        Long userId = Long.parseLong(id);
        //status, confirm 변경
        meetingService.updateConfirmAndStatus(Long.parseLong(study_ID), userId);
        // summary 변경
        meetingService.updateSummary(Long.parseLong(study_ID), userId);

        Map<String, Object> result = new HashMap<>();
        result.put("data", "변경 완료");
        result.put("message", "SUCCESS");
        return ResponseEntity.ok().body(result);
    }

}
