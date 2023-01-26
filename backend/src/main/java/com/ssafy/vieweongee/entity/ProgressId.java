package com.ssafy.vieweongee.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
//import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Embeddable
public class ProgressId implements Serializable {
    // 채점표를 위한 복합키 - 회원 참조
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",referencedColumnName="id", insertable = false, updatable = false)
    private Long user_id;

    // 채점표를 위한 복합키 - 스터디 참조
    @ManyToOne(targetEntity = Study.class, fetch = FetchType.LAZY)
    @JoinColumn(name="study_id", referencedColumnName="id", insertable = false, updatable = false)
    private Long study_id;

}
