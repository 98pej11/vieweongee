package com.ssafy.vieweongee.entity;

import lombok.*;

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

    @Builder
    public Participant(ParticipantId participant_id, String filename, String save) {
        this.participant_id = participant_id;
        this.filename = filename;
        this.save = save;
    }
}
