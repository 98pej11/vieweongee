package com.ssafy.vieweongee.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
//@IdClass(ParticipantId.class)
@Getter
@Builder
@NoArgsConstructor
@ToString
public class Participant {

    // 회원, 스터디 아이디
    @EmbeddedId
    private ParticipantId participant_id;

    // 자기소개서 파일명
    @Column(length = 100)
    private String filename;

    // 자기소개서 저장 경로
    private String save;

    // 면접자로 참여했는지 여부
    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean is_interviewer;

    @Builder
    public Participant(ParticipantId participant_id, String filename, String save, boolean is_interviewer) {
        this.participant_id = participant_id;
        this.filename = filename;
        this.save = save;
        this.is_interviewer = is_interviewer;
    }
}
