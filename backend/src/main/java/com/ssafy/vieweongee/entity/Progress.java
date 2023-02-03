package com.ssafy.vieweongee.entity;

//import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;

@Entity
//@IdClass(ProgressId.class)
@Getter
@NoArgsConstructor
@Builder
@ToString
public class Progress implements Serializable {
    // 회원, 스터디 아이디
    @EmbeddedId
    private ProgressId progress_id;

    // 스터디 참가 상태
    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean status;

    @Builder
    public Progress(ProgressId progress_id, boolean status) {
        this.progress_id = progress_id;
        this.status = status;
    }

    /**
     * 참여 예정인 스터디를 참여 완료로 변경
     */
    public void changeStatusToTrue(){
        this.status = true;
    }
}
