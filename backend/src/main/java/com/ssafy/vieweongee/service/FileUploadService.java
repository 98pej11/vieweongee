package com.ssafy.vieweongee.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.ssafy.vieweongee.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileUploadService {

    // 버켓 이름 동적 할당 (설정파일에서 읽어옴, not lombok)
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.bucket.url}")
    private String defaultUrl;

    private final AmazonS3Client amazonS3Client;

    private final ParticipantRepository participantRepository;

    // MultipartFile 타입으로 받아서 저장 경로를 String으로 반환
    public String upload(MultipartFile uploadFile) throws IOException{
        String origName=uploadFile.getOriginalFilename();
        String url;
        log.info("fileUploadService's 오리지널 파일 이름 : {}", origName);
        try{
            // 확장자 찾기
            final String ext=origName.substring(origName.lastIndexOf('.'));
            // 파일 이름 암호화 (랜덤 저장이름값)
            final String saveFileName=getUuid()+ext;
            // 파일 객체 생성 (현재 작업 디렉토리 + 임시파일명)
            File file=new File(System.getProperty("user.dir")+saveFileName);
            // 파일 변환
            uploadFile.transferTo(file);
            // S3 upload
            uploadOnS3(saveFileName, file);
            // 주소 할당
            url=defaultUrl+saveFileName;
            // 파일 삭제
            file.delete();
        }catch (StringIndexOutOfBoundsException e){
            url=null;
        }
        log.info("새로 생긴 url : {}", url);
        return url;
    }

    private void uploadOnS3(String findName, File file) {
        // AWS S3 전송 객체 생성
        final TransferManager transferManager=new TransferManager(this.amazonS3Client);
        // 요청 객체 생성
        final PutObjectRequest request=new PutObjectRequest(bucket, findName, file);
        // 업로드 시도
        final Upload upload=transferManager.upload(request);

        try{
            log.info("s3에 올리러 왔동.");
            upload.waitForCompletion();
        }catch (AmazonClientException amazonClientException){
            log.error(amazonClientException.getMessage());
        }catch (InterruptedException e){
            log.error(e.getMessage());
        }

    }

    private String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
