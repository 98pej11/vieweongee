package com.ssafy.vieweongee.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentResponse {
    private Long user_id;
    private String user_name;
    private Long comment_id;
    private Long reply_id;
    private int depth;
    private String content;
    private Date datetime;

    public CommentResponse(Long user_id, String user_name, Long comment_id, int depth, String content, Date datetime){
        this.user_id = user_id;
        this.user_name = user_name;
        this.comment_id = comment_id;
        this.depth = depth;
        this.content = content;
        this.datetime = datetime;
    }

    public CommentResponse(Long user_id, String user_name, Long comment_id, Long reply_id, int depth, String content, Date datetime) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.comment_id = comment_id;
        this.reply_id = reply_id;
        this.depth = depth;
        this.content = content;
        this.datetime = datetime;
    }
}
