package com.ssafy.vieweongee.entity;

import com.ssafy.vieweongee.dto.meeting.MeetingScoreRequest;
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
    @ColumnDefault("3")
    private int attitude;

    // 직무역량
    @ColumnDefault("3")
    private int ability;

    // 팀워크
    @ColumnDefault("3")
    private int teamwork;

    // 문제해결능력
    @ColumnDefault("3")
    private int solving;

    // 기업관심도
    @ColumnDefault("3")
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


    /**
     * 면접자의 한 회차 스터디 점수 업데이트를 위함
     * @param scoreRequest
     */
    public void changeScore(MeetingScoreRequest scoreRequest){
        if(this.interviewer == 0){
            //저장한 면접자가 아무도 없을 때
            this.attitude = scoreRequest.getAttitude();
            this.ability = scoreRequest.getAbility();
            this.teamwork = scoreRequest.getTeamwork();
            this.solving = scoreRequest.getSolving();
            this.loyalty = scoreRequest.getLoyalty();
            this.feedback = scoreRequest.getFeedback();
            this.interviewer = 1;
        }else {
            this.attitude += scoreRequest.getAttitude();
            this.ability += scoreRequest.getAbility();
            this.teamwork += scoreRequest.getTeamwork();
            this.solving += scoreRequest.getSolving();
            this.loyalty += scoreRequest.getLoyalty();
            this.feedback += "\n" + scoreRequest.getFeedback();
            this.interviewer += 1;
        }
    }
}

