package com.ssafy.vieweongee.entity;

import com.sun.istack.NotNull;
import lombok.*;
//import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
@Embeddable
@Builder
public class ProgressId implements Serializable {
    // 채점표를 위한 복합키 - 회원 참조
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    // 채점표를 위한 복합키 - 스터디 참조
    @ManyToOne(targetEntity = Study.class, fetch = FetchType.LAZY)
    @JoinColumn(name="study_id")
    private Study study;

    @Builder
    public ProgressId(User user, Study study) {
        this.user = user;
        this.study = study;
    }
}
