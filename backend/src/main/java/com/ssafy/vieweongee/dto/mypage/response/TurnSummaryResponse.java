package com.ssafy.vieweongee.dto.mypage.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
public class TurnSummaryResponse {
    private Long id;
    private String title;
    private LocalDateTime study_datetime;
    private float total_average;

    @Builder
    public TurnSummaryResponse(Long id, String title, LocalDateTime study_datetime, float total_average) {
        this.id = id;
        this.title = title;
        this.study_datetime = study_datetime;
        this.total_average = total_average;
    }
}
