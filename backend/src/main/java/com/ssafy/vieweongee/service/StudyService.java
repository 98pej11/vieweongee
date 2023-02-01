package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.study.CreateStudyRequest;
import com.ssafy.vieweongee.entity.Participant;
import com.ssafy.vieweongee.entity.Study;
import com.ssafy.vieweongee.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudyService {

    Study createStudy(User user, CreateStudyRequest createStudyRequest);

    Study updateStudy(Long study_id, Study study);

    void deleteStudy(Long study_id);

    List<Study> getAllStudy();

    List<Study> getTop3Study();

    Study getStudyDetail(Long study_id);

    List<Study> searchStudy(String search);

    void registParticipant(User user, Study study);

    void createProgress(User user, Study study);

    List<Participant> getParticipantCnt(Long study_id);

    void cancelStudy(User user, Study study);

    void updateResume(User user, Study study, MultipartFile resume);
}
