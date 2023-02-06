package com.ssafy.vieweongee.dto.comment;

import com.ssafy.vieweongee.entity.Comment;
import com.ssafy.vieweongee.entity.Reply;
import com.ssafy.vieweongee.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Getter
public class CreateReplyRequest {
    private String content;

    @Builder
    public CreateReplyRequest(String content) {
        this.content = content;
    }

    public Reply toEntity(User user, Comment comment) {
        return Reply.builder()
                .content(content)
                .datetime(new Date())
                .comment(comment)
                .user(user)
                .build();
    }
}
