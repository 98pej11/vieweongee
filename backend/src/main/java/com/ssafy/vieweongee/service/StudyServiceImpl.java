package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.study.CreateStudyRequest;
import com.ssafy.vieweongee.entity.*;
import com.ssafy.vieweongee.exception.ParticipantNotFoundException;
import com.ssafy.vieweongee.exception.StudyNotFoundException;
import com.ssafy.vieweongee.repository.ParticipantRepository;
import com.ssafy.vieweongee.repository.ProgressRepository;
import com.ssafy.vieweongee.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StudyServiceImpl implements StudyService {
    private final StudyRepository studyRepository;
    private final ParticipantRepository participantRepository;
    private final ProgressRepository progressRepository;
    private final ResourceLoader resourceLoader;

    @Override
    @Transactional
    public Study createStudy(User user, CreateStudyRequest createStudyRequest) {
        return studyRepository.save(createStudyRequest.toEntity(user));
    }

    @Override
    @Transactional
    public Study updateStudy(Long study_id, Study study) {
        Study origin = studyRepository.findById(study_id).orElseThrow(() -> new StudyNotFoundException());

        Study result = Study.builder()
            .id(study_id)
            .user(origin.getUser())
            .running_time(study.getRunning_time())
            .personnel(study.getPersonnel())
            .title(study.getTitle())
            .company(origin.getCompany())
            .job(origin.getJob())
            .type(study.getType())
            .content(study.getContent())
            .attitude(study.isAttitude())
            .ability(study.isAbility())
            .teamwork(study.isTeamwork())
            .solving(study.isSolving())
            .loyalty(study.isLoyalty())
            .confirm(origin.isConfirm())
            .study_datetime(origin.getStudy_datetime())
            .regist_datetime(origin.getRegist_datetime())
            .build();

        studyRepository.save(result);
        return result;
    }

    @Override
    @Transactional
    public void deleteStudy(Long study_id) {
        Study study = studyRepository.findById(study_id).orElseThrow(() -> new StudyNotFoundException());
        studyRepository.deleteById(study_id);
    }

    @Override
    public List<Study> getAllStudy() {
        return studyRepository.findAll();
    }

    @Override
    public List<Study> getTop3Study() {
        return studyRepository.findTop3ByOrderByIdDesc();
    }

    @Override
    public Study getStudyDetail(Long study_id) {
//        return studyRepository.findById(study_id).orElseThrow(() -> new StudyNotFoundException());
        Study result = studyRepository.findById(study_id).orElse(null);
        return result;
    }

    @Override
    public List<Study> searchStudy(String search) {
        return studyRepository.searchByWords(search);
    }

    @Override
    @Transactional
    public void registParticipant(User user, Study study) {
        ParticipantId participantId = ParticipantId.builder()
                .user(user)
                .study(study)
                .build();

        Participant participant = Participant.builder()
                .participant_id(participantId)
                .build();

        participantRepository.save(participant);
    }

    @Override
    @Transactional
    public void createProgress(User user, Study study) {
        ProgressId progressId = ProgressId.builder()
                .user(user)
                .study(study)
                .build();

        Progress progress = Progress.builder()
                .progress_id(progressId)
                .build();

        progressRepository.save(progress);
    }

    @Override
    public List<Participant> getParticipantCnt(Long study_id) {
        return participantRepository.findAllByStudyId(study_id);
    }

    @Override
    @Transactional
    public void cancelStudy(User user, Study study) {
        ParticipantId participantId = ParticipantId.builder()
                .user(user)
                .study(study)
                .build();
        ProgressId progressId = ProgressId.builder()
                .user(user)
                .study(study)
                .build();

        participantRepository.deleteById(participantId);
        progressRepository.deleteById(progressId);
    }


    @Override
    @Transactional
    public void updateResume(User user, Study study, MultipartFile resume, String url) {
        ParticipantId participantId = ParticipantId.builder()
                .user(user)
                .study(study)
                .build();

        Participant origin = participantRepository.findById(participantId).orElseThrow(() -> new ParticipantNotFoundException());
//        RequestFileUpload dbResume=new RequestFileUpload();
//        dbResume.setFilename(resume.getOriginalFilename());
//        dbResume.setSava(url);
//        dbResume.setStudy_id(participantId.getStudy().getId());
//        dbResume.setUser_id(participantId.getUser().getId());

        Participant participant=new Participant(participantId, resume.getOriginalFilename(), url);

        // 파일 저장 경로
        Resource save = resourceLoader.getResource("resources/upload");
        participantRepository.save(participant);
    }

//    //Beedly 참고
//    public String uploadImageS3(MultipartFile image) {
//        String imageUrl = null;
//
//        if(image != null) {
//            if (!image.getContentType().startsWith("image")) {
//                throw new NotMatchException(CONTENT_TYPE_NOT_MATCH);
//            }
//
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentLength(image.getSize());
//            objectMetadata.setContentType(image.getContentType());
//            String storeName = UUID.randomUUID().toString();
//
//            try {
//                amazonS3Client.putObject((new PutObjectRequest(bucket, storeName, image.getInputStream(), objectMetadata)
//                        .withCannedAcl(CannedAccessControlList.PublicRead)));
//
//                // 이미지 url 가져오기
//                imageUrl = amazonS3Client.getUrl(bucket, storeName).toString();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        return imageUrl;
//    }
}
