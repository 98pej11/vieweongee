package com.ssafy.vieweongee.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Data
@NoArgsConstructor
@ToString
@Embeddable
public class ScorecardId implements Serializable {
    // 채점표를 위한 복합키 - 회원 참조
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user_id")
    private User user;

    // 채점표를 위한 복합키 - 스터디 참조
    @ManyToOne(targetEntity = Study.class)
    @JoinColumn(name="study_id")
    private Study study;

}