package com.ssafy.vieweongee.entity;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //user id 외래키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @NotNull
    private User user;

    //study id 외래키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="study_id")
    @NotNull
    private Study study;

    @Column(length = 50)
    @NotNull
    private String content;

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean see;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date datetime;

    @Builder
    public Alarm(Long id, User user, Study study, String content, boolean see, Date datetime) {
        this.id = id;
        this.user = user;
        this.study = study;
        this.content = content;
        this.see = see;
        this.datetime = datetime;
    }
}
