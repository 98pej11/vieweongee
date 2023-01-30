package com.ssafy.vieweongee.controller;

import com.ssafy.vieweongee.dto.meeting.request.MeetingScoreRequest;
import com.ssafy.vieweongee.service.MeetingScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/meeting")
public class MeetingController {

    private final MeetingScoreService meetingScoreService;

    @Autowired
    public MeetingController(MeetingScoreService meetingScoreService) {
        this.meetingScoreService = meetingScoreService;
    }

    // 스터디원 자기소개서 전부 가져오기 >> 서버에서 저장된 경로 찾아서 파일 넘겨주기
    // 스터디원 채점표 생성하기 >> 스터디 생성 24시간이내

    /**
     * 스터디원의 채점표를 전부 불러옴
     * @param study_ID
     * @return List<MeetingScoreRequest>
     */
    @GetMapping("{study_ID}/score")
    public ResponseEntity<List<MeetingScoreRequest>> showAllScorecard(
            @PathVariable("study_ID") String study_ID) {

        return new ResponseEntity<>(meetingScoreService.getAllScore(study_ID), HttpStatus.OK);
    }

    // 스터디원 채점표 저장하기
    /*
    [스터디원 채점표 저장하기]
    프론트에서 스터디ID(파라미터), 면접자ID(리퀘), 항목별 점수 PUT
    백에서 파라미터로 스터디 아이디, DTO에서 면접자 아이디, 항목별 점수 가져옴
    서비스에서 해당 면접자의 채점표 가져오고
    DTO에서 파라미터 DTO를 처리하고
    처리한 DTO를 엔티티로 바꿔주고
    엔티티를 디비로 update 보냄
     */

    //1) 유저서비스로 유저 객체 가져오기
    //2) 스터디서비스로 스터디 객체 가져오기
    //3) 미팅서비스로 스터디의 유저 채점표 업데이트하기

    /**
     * 면접자 1명의 채점표 갱신
     * @param study_ID
     * @param meetingScoreRequest
     * @return
     */
    @PutMapping("{study_ID}/score")
    public ResponseEntity<MeetingScoreRequest> modifyScorecard(
            @PathVariable("study_ID") String study_ID,
            @RequestBody MeetingScoreRequest meetingScoreRequest) {
        meetingScoreService.updateScore(study_ID, meetingScoreRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 스터디원 마이페이지 통계 갱신하기

    // 스터디 상태(진행/완료) 변경하기 >> 스터디에서 생성한 종료 시간이 됐을 때
}
