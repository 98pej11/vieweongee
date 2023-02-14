package com.ssafy.vieweongee.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    @NotNull
    private String content;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime datetime;

    //스터디 모집 게시글 번호. study id. 외래키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    @NotNull
    private Study study;

    //댓글 작성자. user id. 외래키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    // 대댓글과 1:N 연결
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();

    @Builder
    public Comment(Long id, String content, LocalDateTime datetime, Study study, User user, List<Reply> replies) {
        this.id = id;
        this.content = content;
        this.datetime = datetime;
        this.study = study;
        this.user = user;
        this.replies = replies;
    }
}

