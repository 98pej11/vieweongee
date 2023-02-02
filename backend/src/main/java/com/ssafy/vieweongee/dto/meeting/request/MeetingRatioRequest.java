package com.ssafy.vieweongee.dto.meeting.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MeetingRatioRequest {
    private int interviewee;

    public MeetingRatioRequest(int interviewee) {
        this.interviewee = interviewee;
    }
}
