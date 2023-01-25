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
    @JoinColumn(name="user_id",referencedColumnName="id")
    @NotNull
    private User user_id;

    //study id 외래키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="study_id",referencedColumnName="id")
    @NotNull
    private Study study_id;

    @Column(length = 50)
    @NotNull
    private String content;

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean see;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date datetime;

    @Builder
    public Alarm(Long id, User user_id, Study study_id, String content, boolean see, Date datetime) {
        this.id = id;
        this.user_id = user_id;
        this.study_id = study_id;
        this.content = content;
        this.see = see;
        this.datetime = datetime;
    }
}
