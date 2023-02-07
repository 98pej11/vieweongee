package com.ssafy.vieweongee.controller;

import com.ssafy.vieweongee.dto.comment.CommentResponse;
import com.ssafy.vieweongee.dto.comment.CreateCommentRequest;
import com.ssafy.vieweongee.dto.comment.CreateReplyRequest;
import com.ssafy.vieweongee.dto.study.CreateStudyRequest;
import com.ssafy.vieweongee.dto.study.StudyResponse;
import com.ssafy.vieweongee.entity.Comment;
import com.ssafy.vieweongee.entity.Reply;
import com.ssafy.vieweongee.entity.Study;
import com.ssafy.vieweongee.entity.User;
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

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/study")
//@RequestMapping("/study")
public class StudyController {
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

        return ResponseEntity.status(HttpStatus.CREATED).body(study.getId());
    }

    /**
     * 스터디 모집 게시글 수정
     * @param study_id
     * @param study
     * @return study_id
     */
    @PutMapping("/{study_id}")
    public ResponseEntity<?> updateStudy(@PathVariable("study_id") Long study_id, @RequestBody Study study, @RequestHeader("ACCESS") String access) {

        Long result = studyService.updateStudy(study_id, study).getId();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 스터디 모집 게시글 삭제
     * @param study_id
     * @return null
     */
    @DeleteMapping("/{study_id}")
    public ResponseEntity<?> deleteStudy(@PathVariable("study_id") Long study_id,@RequestHeader("ACCESS") String access) {
        studyService.deleteStudy(study_id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 모든 스터디 조회
     * @return List<StudyResponse>
     */
    @GetMapping
    public ResponseEntity<?> getAllStudy() {
        List<Study> studyList = studyService.getAllStudy();
        if (studyList.isEmpty()) {
            String msg = "등록된 스터디가 없습니다.";
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(msg);
        }

        List<StudyResponse> result = new ArrayList<>();
        for(int i = 0; i < studyList.size(); i++) {
            result.add(new StudyResponse(studyList.get(i)));
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 가장 최근 생성된 스터디 3개 조회
     * @return List<StudyResponse>
     */
    @GetMapping("/top3")
    public ResponseEntity<?> getTop3Study() {
        List<Study> studyList = studyService.getTop3Study();
        if (studyList.isEmpty()) {
            String msg = "등록된 스터디가 없습니다.";
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(msg);
        }

        List<StudyResponse> result = new ArrayList<>();
        for(int i = 0; i < studyList.size(); i++) {
            result.add(new StudyResponse(studyList.get(i)));
        }

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
        String search = words.replace(",", "|").replace(" ", "|");

        List<Study> studyList = studyService.searchStudy(search);
        if (studyList.isEmpty()) {
            String msg = "검색하신 스터디가 없습니다.";
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(msg);
        }

        List<StudyResponse> result = new ArrayList<>();
        for(int i = 0; i < studyList.size(); i++) {
            result.add(new StudyResponse(studyList.get(i)));
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 스터디 1개 상세 조회
     * @param study_id
     * @return studyResponse
     */
    @GetMapping("/{study_id}")
    public ResponseEntity<StudyResponse> getStudyDetail(@PathVariable("study_id") Long study_id) {
        Study study = studyService.getStudyDetail(study_id);
        StudyResponse result = new StudyResponse(study);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 스터디 참가자 수 조회
     * @param study_id
     * @return int
     */
    @GetMapping("/{study_id}/current-people")
    public ResponseEntity<?> getParticipantCnt(@PathVariable("study_id") Long study_id) {
        int result = studyService.getParticipantCnt(study_id).size();
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

        // 마감 여부 확인
        if (studyService.getParticipantCnt(study_id).size() >= study.getPersonnel()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("마감된 스터디입니다.");
        }

        // 참가 명단에 추가
        studyService.registParticipant(user, study);

        // 참가 이력 생성
        studyService.createProgress(user, study);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 스터디 신청 취소
     * @param study_id
     * @return null
     */
    @DeleteMapping("/{study_id}/member")
    public ResponseEntity<?> cancelStudy(@PathVariable("study_id") Long study_id,@RequestHeader("ACCESS") String access) {

        Long user_id = Long.parseLong(tokenService.getUid(access).replaceAll("\"",""));
        User user = userService.getUserById(user_id);
        Study study = studyService.getStudyDetail(study_id);

        // 참가 명단, 참가 이력에서 삭제
        studyService.cancelStudy(user, study);

        return ResponseEntity.status(HttpStatus.OK).build();
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

        return ResponseEntity.status(HttpStatus.CREATED).body(comment.getStudy().getId());
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

        return ResponseEntity.status(HttpStatus.OK).body(study_id);
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
        return ResponseEntity.status(HttpStatus.OK).body(study_id);
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

        return ResponseEntity.status(HttpStatus.CREATED).body(study_id);
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

        return ResponseEntity.status(HttpStatus.OK).body(study_id);
    }

    /**
     * 대댓글 삭제
     * @param study_id
     * @param comment_id
     * @param reply_id
     * @return study_id
     */
    @DeleteMapping("/{study_id}/comment/{comment_id}/{reply_id}")
//    public ResponseEntity<?> deleteComment(@RequestHeader(value = "Authorization") String token,
//                                         @PathVariable("study_id") Long study_id,
//                                         @PathVariable("comment_id") Long comment_id)
//                                         @PathVariable("reply_id") Long reply_id) {
    public ResponseEntity<?> deleteComment(@PathVariable("study_id") Long study_id,
                                           @PathVariable("comment_id") Long comment_id,
                                           @PathVariable("reply_id") Long reply_id,
                                           @RequestHeader("ACCESS") String access) {

//        // jwt 토큰 확인
//        if (!jwtTokenProvider.validateToken(token)) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(new ErrorResponse(messageSource.getMessage("error.valid.jwt", null, LocaleContextHolder.getLocale())));
//        }
        Long user_id = Long.parseLong(tokenService.getUid(access).replaceAll("\"",""));
        commentService.deleteReply(reply_id);
        return ResponseEntity.status(HttpStatus.OK).body(study_id);
    }

    /**
     * 댓글 및 대댓글 조회
     * @param study_id
     * @return List<Comment>
     */
    @GetMapping("/{study_id}/comment")
    public ResponseEntity<?> getAllComment(@PathVariable("study_id") Long study_id) {
        List<CommentResponse> result = commentService.getAllComment(study_id);
        if (result == null) {
            String msg = "등록된 댓글이 없습니다.";
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(msg);
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 자기소개서 업로드 및 수정
     * @param study_id
     * @param resume
     * @return study_id
     */
    @PutMapping("/{study_id}/resume")
//    public ResponseEntity<?> uploadResume(@RequestHeader(value = "Authorization") String token,
//            @PathVariable("study_id") Long study_id,
//            @RequestBody MultipartFile resume) {
    public ResponseEntity<?> uploadResume(@PathVariable("study_id") Long study_id,
                                          @RequestBody MultipartFile resume,@RequestHeader("ACCESS") String access) throws IOException {


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
