package com.ssafy.vieweongee.dto.meeting;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MeetingResumeRequest {
    private Long user_ID;
    private String imgURL;

    public MeetingResumeRequest(Long user_ID, String imgURL){
        this.user_ID=user_ID;
        this.imgURL=imgURL;
    }
}
