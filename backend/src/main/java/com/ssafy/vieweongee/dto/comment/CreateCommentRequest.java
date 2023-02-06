package com.ssafy.vieweongee.dto.comment;

import com.ssafy.vieweongee.entity.Comment;
import com.ssafy.vieweongee.entity.Study;
import com.ssafy.vieweongee.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Getter
public class CreateCommentRequest {
    private String content;

    @Builder
    public CreateCommentRequest(String content) {
        this.content = content;
    }

    public Comment toEntity(User user, Study study) {
        return Comment.builder()
                .content(content)
                .datetime(new Date())
                .study(study)
                .user(user)
                .build();
    }
}
