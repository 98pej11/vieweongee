package com.ssafy.vieweongee.dto.summary;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SummaryCreateRequest {
    private Long user_id;

    @Builder
    public SummaryCreateRequest(Long user_id){
        this.user_id=user_id;
    }
}

