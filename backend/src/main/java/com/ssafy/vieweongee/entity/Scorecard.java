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
    private int attitude;

    // 직무역량
    @ColumnDefault("0")
    private int ability;

    // 팀워크
    @ColumnDefault("0")
    private int teamwork;

    // 문제해결능력
    @ColumnDefault("0")
    private int solving;

    // 기업관심도
    @ColumnDefault("0")
    private int loyalty;

    // 피드백
    @Column(length = 1000)
    private String feedback;

    // 면접관 수
    @ColumnDefault("0")
    private int interviewer;

    @Builder
    public Scorecard(ScorecardId score_id, int attitude, int ability, int teamwork, int solving, int loyalty, String feedback, int interviewer) {
        this.score_id = score_id;
        this.attitude = attitude;
        this.ability = ability;
        this.teamwork = teamwork;
        this.solving = solving;
        this.loyalty = loyalty;
        this.feedback = feedback;
        this.interviewer = interviewer;
    }
}
