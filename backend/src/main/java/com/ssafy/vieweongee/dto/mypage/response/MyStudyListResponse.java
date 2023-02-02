package com.ssafy.vieweongee.dto.mypage.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class MyStudyListResponse {
    private Long id;
    private String title;
    private String company;
    private String job;
    private Date study_datetime;
    private int running_time;
    private boolean status;

    @Builder

    public MyStudyListResponse(Long id, String title, String company, String job, Date study_datetime, int running_time, boolean status) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.job = job;
        this.study_datetime = study_datetime;
        this.running_time = running_time;
        this.status = status;
    }
}
