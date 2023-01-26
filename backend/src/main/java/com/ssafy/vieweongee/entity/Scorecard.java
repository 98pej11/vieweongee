package com.ssafy.vieweongee.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;


@Entity
//@IdClass(ScorecardId.class)
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Scorecard implements Serializable{
    // 회원, 스터디 아이디
    @EmbeddedId
    private ScorecardId score_id;

    // 태도 점수
    @ColumnDefault("0")
    private float attitude;

    // 직무역량
    @ColumnDefault("0")
    private float ability;

    // 팀워크
    @ColumnDefault("0")
    private float teamwork;

    // 문제해결능력
    @ColumnDefault("0")
    private float solving;

    // 기업관심도
    @ColumnDefault("0")
    private float loyalty;

    // 피드백
    @Column(length = 1000)
    private String feedback;

    @Builder
    public Scorecard(ScorecardId score_id, float attitude, float ability, float teamwork, float solving, float loyalty, String feedback) {
        this.score_id = score_id;
        this.attitude = attitude;
        this.ability = ability;
        this.teamwork = teamwork;
        this.solving = solving;
        this.loyalty = loyalty;
        this.feedback = feedback;
    }
}
