package com.ssafy.vieweongee.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Reply {
    // 대댓글 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글 번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    @NotNull
    private Comment comment_id;

    // 작성자 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName="id")
    @NotNull
    private User user_id;

    // 대댓글 내용
    @Column(length = 200)
    @NotNull
    private String content;

    // 대댓글 등록 날짜 및 시간
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date datetime;

    @Builder
    public Reply(Long id, Comment comment_id, User user_id, String content, Date datetime) {
        this.id = id;
        this.comment_id = comment_id;
        this.user_id = user_id;
        this.content = content;
        this.datetime = datetime;
    }
}
