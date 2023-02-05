//package com.ssafy.vieweongee.service;
//
//import com.amazonaws.Request;
//import com.ssafy.vieweongee.dto.study.RequestFileUpload;
//import com.ssafy.vieweongee.repository.ParticipantRepository;
//import org.springframework.web.multipart.MultipartFile;
//
//import static com.amazonaws.util.AWSRequestMetrics.Field.StatusCode;
//
//public class ResumeService {
//    private final FileUploadService fileUploadService;
//    private final ParticipantRepository participantRepository;
//    public ResumeService(FileUploadService fileUploadService, ParticipantRepository participantRepository){
//        this.fileUploadService=fileUploadService;
//        this.participantRepository=participantRepository;
//    }
//
//    public DefaultRes resumeSave(RequestFileUpload requestFileUpload, MultipartFile multipartFile){
//        try{
//            if(multipartFile!=null){
//                requestFileUpload.setSava(fileUploadService.upload(multipartFile));
//            }
//            participantRepository.save(requestFileUpload);
//            return DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS);
//        }catch (Exception e){
//            return
//        }
//    }
//}
