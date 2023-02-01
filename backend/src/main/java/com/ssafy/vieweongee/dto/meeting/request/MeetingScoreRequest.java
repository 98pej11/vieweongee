package com.ssafy.vieweongee.dto.meeting.request;

import com.ssafy.vieweongee.entity.Scorecard;
import com.ssafy.vieweongee.entity.ScorecardId;
import lombok.*;

/**
 * 채점표 점수 변경을 위한 DTO
 * 태도, 직무역량, 팀워크, 문제해결능력, 기업관심도, 피드백에 대해서만 업데이트
 */

@Getter
@NoArgsConstructor
public class MeetingScoreRequest {
    //면접자 id
    private Long id;
    // 태도 점수
    private int attitude;
    // 직무역량
    private int ability;
    // 팀워크
    private int teamwork;
    // 문제해결능력
    private int solving;
    // 기업관심도
    private int loyalty;
    // 피드백
    private String feedback;

    public MeetingScoreRequest(Long id, int attitude, int ability, int teamwork, int solving, int loyalty, String feedback) {
        this.id = id;
        this.attitude = attitude;
        this.ability = ability;
        this.teamwork = teamwork;
        this.solving = solving;
        this.loyalty = loyalty;
        this.feedback = feedback;
    }
}

