package com.ssafy.vieweongee.entity;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.sql.ConnectionBuilder;


@Getter
@NoArgsConstructor
@ToString
@Embeddable
@Builder
public class ParticipantId implements Serializable {
    // 참가 명단을 위한 복합키 - 회원 참조
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    // 참가 명단을 위한 복합키 - 스터디 참조
    @ManyToOne(targetEntity = Study.class, fetch = FetchType.LAZY)
    @JoinColumn(name="study_id")
    private Study study;

    @Builder
    public ParticipantId(User user, Study study) {
        this.user = user;
        this.study = study;
    }
}

