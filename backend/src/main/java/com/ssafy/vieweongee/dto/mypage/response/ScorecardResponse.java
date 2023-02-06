package com.ssafy.vieweongee.dto.mypage.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ScorecardResponse {
    private float attitude_average;
    private float ability_average;
    private float teamwork_average;
    private float solving_average;
    private float loyalty_average;
    private String feedback;

    @Builder
    public ScorecardResponse(float attitude_average, float ability_average, float teamwork_average, float solving_average, float loyalty_average, String feedback) {
        this.attitude_average = attitude_average;
        this.ability_average = ability_average;
        this.teamwork_average = teamwork_average;
        this.solving_average = solving_average;
        this.loyalty_average = loyalty_average;
        this.feedback = feedback;
    }
}
