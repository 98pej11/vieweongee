package com.ssafy.vieweongee.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Meeting implements Serializable {
    @Id
    @Column(name = "study_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name="study_id")
    private Study study_id;
//    @Id
//    @OneToOne
//    @JoinColumn(name="study_id",referencedColumnName="id")
//    private Study study_id;

    @NotNull
    private String link;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date start;

    @Builder
    public Meeting(Long id, Study study_id, String link, Date start) {
        this.id = id;
        this.study_id = study_id;
        this.link = link;
        this.start = start;
    }
}