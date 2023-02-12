package com.ssafy.vieweongee.dto.mypage.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyStudyFeedbackRequest {
    private Long user_id;
    private Long study_id;
}
