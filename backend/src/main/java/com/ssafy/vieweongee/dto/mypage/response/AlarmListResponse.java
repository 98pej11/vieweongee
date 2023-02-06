package com.ssafy.vieweongee.dto.mypage.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class AlarmListResponse {
    private String content;
    private Date datetime;
    private Long study_id;
    private Long user_id;

    @Builder
    public AlarmListResponse(String content, Date datetime, Long study_id, Long user_id) {
        this.content = content;
        this.datetime = datetime;
        this.study_id = study_id;
        this.user_id = user_id;
    }
}
