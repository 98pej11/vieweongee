package com.ssafy.vieweongee.dto.study;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RequestFileUpload {
    // 업로드한 파일 이름
    private String filename;

    // 파일 저장 경로
    private String sava;

    private Long user_id;
    private Long study_id;
}
