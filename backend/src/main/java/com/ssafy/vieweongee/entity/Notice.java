package com.ssafy.vieweongee.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //관리자 id. 외래키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Column(length = 10)
    @NotNull
    private String type;
    @Column(length = 25)
    @NotNull
    private String title;
    @Column(length = 2000)
    @NotNull
    private String content;
    //생성 날짜
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date datetime;

    @Builder
    public Notice(Long id, User user, String type, String title, String content, Date datetime) {
        this.id = id;
        this.user = user;
        this.type = type;
        this.title = title;
        this.content = content;
        this.datetime = datetime;
    }
}
