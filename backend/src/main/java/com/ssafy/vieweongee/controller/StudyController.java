package com.ssafy.vieweongee.controller;

import com.ssafy.vieweongee.dto.comment.CommentResponse;
import com.ssafy.vieweongee.dto.comment.CreateCommentRequest;
import com.ssafy.vieweongee.dto.comment.CreateReplyRequest;
import com.ssafy.vieweongee.dto.study.CreateStudyRequest;
import com.ssafy.vieweongee.dto.study.StudyResponse;
import com.ssafy.vieweongee.entity.*;
import com.ssafy.vieweongee.repository.CommentRepository;
import com.ssafy.vieweongee.repository.ParticipantRepository;
import com.ssafy.vieweongee.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/study")
//@RequestMapping("/study")
public class StudyController {
    private final CommentRepository commentRepository;
    private final ParticipantRepository participantRepository;
    private final StudyService studyService;
    //    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final CommentService commentService;

    private final TokenService tokenService;

    private final FileUploadService fileUploadService;


    /** 스터디 모집 게시글 생성
     * @param createStudyRequest
     * @return study_id
     */
    @PostMapping
    public ResponseEntity<?> createStudy(@RequestBody CreateStudyRequest createStudyRequest, @RequestHeader("ACCESS") String access) {
        Map<String, Object> result = new HashMap<>();
        Long user_id = Long.parseLong(tokenService.getUid(access).replaceAll("\"",""));
        log.info("user id in create study is {}", user_id);
        User user = userService.getUserById(user_id);
        log.info("user in study : {}",user.getName());

        // 스터디 생성
        Study study = studyService.createStudy(user, createStudyRequest);

        // 참가 명단에 추가
        studyService.registParticipant(user, study);

        // 참가 이력 생성
        studyService.createProgress(user, study);
        result.put("data",study.getId());
        result.put("message","SUCCESS");
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 스터디 모집 게시글 수정
     * @param study_id
     * @param study
     * @return study_id
     */
    @PutMapping("/{study_id}")
    public ResponseEntity<?> updateStudy(@PathVariable("study_id") Long study_id, @RequestBody Study study, @RequestHeader("ACCESS") String access) {
        Map<String, Object> result = new HashMap<>();
        Long studyId = studyService.updateStudy(study_id, study).getId();

        result.put("data",studyId);
        result.put("message","SUCCESS");

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 스터디 모집 게시글 삭제
     * @param study_id
     * @return null
     */
    @DeleteMapping("/{study_id}")
    public ResponseEntity<?> deleteStudy(@PathVariable("study_id") Long study_id,@RequestHeader("ACCESS") String access) {
        Long user_id = Long.parseLong(tokenService.getUid(access).replaceAll("\"",""));
        Study study = studyService.getStudyDetail(study_id);
        Map<String, Object> result = new HashMap<>();

        if (user_id != study.getUser().getId()) {
            result.put("data", null);
            result.put("message", "FAIL");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        } else {
            result.put("data",null);
            result.put("message", "SUCCESS");
            studyService.deleteStudy(study_id);
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 모든 스터디 조회
     * @return List<StudyResponse>
     */
    @GetMapping
    public ResponseEntity<?> getAllStudy() {
        List<Study> studyList = studyService.getAllStudy();
        Map<String, Object> result = new HashMap<>();
        if (studyList.isEmpty()) {
            String msg = "등록된 스터디가 없습니다.";
            result.put("data",null);
            result.put("message",msg);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
        }

        List<StudyResponse> results = new ArrayList<>();
        for(int i = 0; i < studyList.size(); i++) {
            results.add(new StudyResponse(studyList.get(i)));
        }
        result.put("data",results);
        result.put("message","SECCESS");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 가장 최근 생성된 스터디 3개 조회
     * @return List<StudyResponse>
     */
    @GetMapping("/top3")
    public ResponseEntity<?> getTop3Study() {
        Map<String, Object> result = new HashMap<>();
        List<Study> studyList = studyService.getTop3Study();
        if (studyList.isEmpty()) {
            result.put("data",null);
            String msg = "등록된 스터디가 없습니다.";
            result.put("message",msg);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
        }

        List<StudyResponse> results = new ArrayList<>();
        for(int i = 0; i < studyList.size(); i++) {
            results.add(new StudyResponse(studyList.get(i)));
        }
        result.put("data",results);
        result.put("message","SUCCESS");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 스터디 검색
     * @param words
     * @return List<StudyResponse>
     */
    @GetMapping("/search/{words}")
    public ResponseEntity<?> searchStudy(@PathVariable String words) {
        // 콤마와 띄어쓰기로 구분하여 List로 바꾸기
//        String search = words.replace(",", "|").replace(" ", "|");
        String search = words.replace(",", " ").replaceAll("\\s+", " ").replace(" ", "|");

        Map<String, Object> result = new HashMap<>();
        List<Study> studyList = studyService.searchStudy(search);
        if (studyList.isEmpty()) {
            result.put("data",null);
            String msg = "검색하신 스터디가 없습니다.";
            result.put("message",msg);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
        }

        List<StudyResponse> results = new ArrayList<>();
        for(int i = 0; i < studyList.size(); i++) {
            results.add(new StudyResponse(studyList.get(i)));
        }
        result.put("data",results);
        result.put("message","SUCCESS");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 스터디 1개 상세 조회
     * @param study_id
     * @return studyResponse
     */
    @GetMapping("/detail/{study_id}/{user_id}")
    public ResponseEntity<?> getStudyDetail(@PathVariable("study_id") Long study_id,
                                            @PathVariable("user_id") Long user_id) {
        Study study = studyService.getStudyDetail(study_id);

        Map<String, Object> result = new HashMap<>();

        StudyResponse results = new StudyResponse(study);

        result.put("data",results);

        if (user_id==0){
            result.put("message", "OTHER");
        }
        else{
//            Long user_id = Long.parseLong(tokenService.getUid(access).replaceAll("\"",""));
            List<Participant> studies = participantRepository.findAllByStudyId(study_id);
            for (Participant participant:studies){
                // 해당 스터디 아이디에 해당하는 참가 기록에 토큰에서 뽑은 유저 아이디가 있으면 신청한 스터디
                if (participant.getParticipant_id().getUser().getId().equals(user_id)){
                    result.put("message","MINE");
                }else{
                    result.put("message","OTHER");
                }
            }
//            result.put("message", "MINE");
        }


        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 스터디 참가자 수 조회
     * @param study_id
     * @return int
     */
    @GetMapping("/{study_id}/current-people")
    public ResponseEntity<?> getParticipantCnt(@PathVariable("study_id") Long study_id) {
        int results = studyService.getParticipantCnt(study_id).size();
        Map<String, Object> result = new HashMap<>();
        result.put("data",results);
        result.put("message","SUCCESS");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 스터디 신청
     * @param study_id
     * @return study_id
     */
    @PostMapping("/{study_id}/member")
    public ResponseEntity<?> applyStudy(@PathVariable("study_id") Long study_id, @RequestHeader("ACCESS") String access) {
        Long user_id = Long.parseLong(tokenService.getUid(access).replaceAll("\"",""));
        User user = userService.getUserById(user_id);
        Study study = studyService.getStudyDetail(study_id);

        Map<String, Object> result = new HashMap<>();
        // 마감 여부 확인
        if (studyService.getParticipantCnt(study_id).size() >= study.getPersonnel()){
            result.put("data",null);
            result.put("message", "FAIL-SOLDOUT");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
        }

        // 참가 명단에 추가
        studyService.registParticipant(user, study);

        // 참가 이력 생성
        studyService.createProgress(user, study);

        result.put("data",null);
        result.put("message", "SUCCESS");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 스터디 신청 취소
     * @param study_id
     * @return null
     */
    @DeleteMapping("/{study_id}/member")
    public ResponseEntity<?> cancelStudy(@PathVariable("study_id") Long study_id,@RequestHeader("ACCESS") String access) {
        Map<String, Object> result = new HashMap<>();
        Long user_id = Long.parseLong(tokenService.getUid(access).replaceAll("\"",""));
        User user = userService.getUserById(user_id);
        Study study = studyService.getStudyDetail(study_id);

        // 참가 명단, 참가 이력에서 삭제
        studyService.cancelStudy(user, study);
        result.put("data",null);
        result.put("message","SUCCESS");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 댓글 생성
     * @param study_id
     * @param createCommentRequest
     * @return study_id
     */
    @PostMapping("/{study_id}/comment")
    public ResponseEntity<?> createComment(@PathVariable("study_id") Long study_id,
                                           @RequestBody CreateCommentRequest createCommentRequest,
                                           @RequestHeader("ACCESS") String access) {

        Long user_id = Long.parseLong(tokenService.getUid(access).replaceAll("\"",""));
        User user = userService.getUserById(user_id);
        Study study = studyService.getStudyDetail(study_id);
        Comment comment = commentService.createComment(user, study, createCommentRequest);
        Map<String, Object> result = new HashMap<>();
        result.put("data",comment.getStudy().getId());
        result.put("message","SUCCESS");
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 댓글 수정
     * @param study_id
     * @param comment_id
     * @param comment
     * @return study_id
     */
    @PutMapping("/{study_id}/comment/{comment_id}")
    public ResponseEntity<?> updateComment(@PathVariable("study_id") Long study_id,
                                           @PathVariable("comment_id") Long comment_id,
                                           @RequestBody Comment comment,
                                           @RequestHeader("ACCESS") String access) {

        Long user_id = Long.parseLong(tokenService.getUid(access).replaceAll("\"",""));
        commentService.updateComment(comment_id, comment);
        Map<String, Object> result = new HashMap<>();
        result.put("data",study_id);
        result.put("message","SUCCESS");

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 댓글 삭제
     * @param study_id
     * @param comment_id
     * @return study_id
     */
    @DeleteMapping("/{study_id}/comment/{comment_id}")
    public ResponseEntity<?> deleteComment(@PathVariable("study_id") Long study_id,
                                           @PathVariable("comment_id") Long comment_id,
                                           @RequestHeader("ACCESS") String access) {

        commentService.deleteComment(comment_id);
        Map<String, Object> result = new HashMap<>();
        result.put("data",study_id);
        result.put("message","SUCCESS");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 대댓글 생성
     * @param study_id
     * @param comment_id
     * @param createReplyRequest
     * @return study_id
     */
    @PostMapping("/{study_id}/comment/{comment_id}/reply")
    public ResponseEntity<?> createReply(@PathVariable("study_id") Long study_id,
                                         @PathVariable("comment_id") Long comment_id,
                                         @RequestBody CreateReplyRequest createReplyRequest,
                                         @RequestHeader("ACCESS") String access) {
        Long user_id = Long.parseLong(tokenService.getUid(access).replaceAll("\"",""));
        User user = userService.getUserById(user_id);
        Comment comment = commentService.getCommentById(comment_id);
        commentService.createReply(user, comment, createReplyRequest);

        Map<String, Object> result = new HashMap<>();
        result.put("data", study_id);
        result.put("message", "SUCCESS");
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 대댓글 수정
     * @param study_id
     * @param comment_id
     * @param reply_id
     * @param reply
     * @return study_id
     */
    @PutMapping("/{study_id}/comment/{comment_id}/{reply_id}")
    public ResponseEntity<?> updateReply(@PathVariable("study_id") Long study_id,
                                         @PathVariable("comment_id") Long comment_id,
                                         @PathVariable("reply_id") Long reply_id,
                                         @RequestBody Reply reply,
                                         @RequestHeader("ACCESS") String access) {

        commentService.updateReply(reply_id, reply);
        Map<String, Object> result = new HashMap<>();
        result.put("data", study_id);
        result.put("message", "SUCCESS");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 대댓글 삭제
     * @param study_id
     * @param comment_id
     * @param reply_id
     * @return study_id
     */
    @DeleteMapping("/{study_id}/comment/{comment_id}/{reply_id}")
    public ResponseEntity<?> deleteComment(@PathVariable("study_id") Long study_id,
                                           @PathVariable("comment_id") Long comment_id,
                                           @PathVariable("reply_id") Long reply_id,
                                           @RequestHeader("ACCESS") String access) {

        Long user_id = Long.parseLong(tokenService.getUid(access).replaceAll("\"",""));
        commentService.deleteReply(reply_id);

        Map<String, Object> result = new HashMap<>();
        result.put("data", study_id);
        result.put("message","SUCCESS");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 댓글 및 대댓글 조회
     * @param study_id
     * @return List<Comment>
     */
    @GetMapping("/{study_id}/comment")
    public ResponseEntity<?> getAllComment(@PathVariable("study_id") Long study_id) {
        List<CommentResponse> results = commentService.getAllComment(study_id);
        Map<String, Object> result = new HashMap<>();
        if (results == null) {
            String msg = "등록된 댓글이 없습니다.";
            result.put("data",null);
            result.put("message",msg);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
        }
        result.put("data", results);
        result.put("message", "SUCCESS");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 자기소개서 업로드 및 수정
     * @param study_id
     * @param resume
     * @return study_id
     */
    @PutMapping("/{study_id}/resume")
    public ResponseEntity<?> uploadResume(@PathVariable("study_id") Long study_id,
                                          @RequestPart(value="file") MultipartFile resume,@RequestHeader("ACCESS") String access) throws IOException {


        Long user_id = Long.parseLong(tokenService.getUid(access).replaceAll("\"",""));
        User user = userService.getUserById(user_id);
        Study study = studyService.getStudyDetail(study_id);

        String url= fileUploadService.upload(resume);
//        requestFileUpload.setSava(url);
//        requestFileUpload.setFilename(resume.getOriginalFilename());

//        ParticipantId ids = new ParticipantId(user, study);
//
//        participantRepository.save(requestFileUpload);
        studyService.updateResume(user,study, resume, url);

        HashMap<String, String> result=new HashMap<>();
        result.put("data",null);
        result.put("message","SUCCESS");

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

