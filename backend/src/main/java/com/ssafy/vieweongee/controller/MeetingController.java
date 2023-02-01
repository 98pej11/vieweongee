package com.ssafy.vieweongee.controller;

import com.ssafy.vieweongee.dto.meeting.request.MeetingScoreRequest;
import com.ssafy.vieweongee.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/meeting")
public class MeetingController {

    private final MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    // 스터디원 자기소개서 전부 가져오기 >> 서버에서 저장된 경로 찾아서 파일 넘겨주기

    /**
     * 스터디 채점표 생성
     *
     * @param study_ID
     * @return
     */
    @PostMapping("/{study_ID}/score")
    public ResponseEntity<Boolean> startMeeting(@PathVariable("study_ID") String study_ID) {
        if (!meetingService.makeScorecard(study_ID)) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    //미팅 시작 시간, 진행 시간

    /**
     * 스터디원의 채점표를 전부 불러옴
     *
     * @param study_ID
     * @return List<MeetingScoreRequest>
     */
    @GetMapping("/{study_ID}/score")
    public ResponseEntity<List<MeetingScoreRequest>> showAllScorecard(
            @PathVariable("study_ID") String study_ID) {

        return new ResponseEntity<>(meetingService.getAllScore(study_ID), HttpStatus.OK);
    }

    /**
     * 면접자 1명의 채점표 갱신
     *
     * @param study_ID
     * @param meetingScoreRequest
     * @return
     */
    @PutMapping("/{study_ID}/score")
    public ResponseEntity<MeetingScoreRequest> modifyScorecard(
            @PathVariable("study_ID") String study_ID,
            @RequestBody MeetingScoreRequest meetingScoreRequest) {
        meetingService.updateScore(study_ID, meetingScoreRequest);

        //마이페이지에 통계 갱신

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 스터디 상태(진행/완료) 변경하기 >> 스터디에서 생성한 종료 시간이 됐을 때 >> Video 컨트롤러에서 처리 완료

    //스터디 순서 지정
}
