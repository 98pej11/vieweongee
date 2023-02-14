package com.ssafy.vieweongee.dto.comment;

import java.time.LocalDateTime;
import java.util.Date;

public interface CommentReplyJoin {
    Long getComment_user_id();
    Long getReply_user_id();
    Long getComment_id();
    Long getReply_id();
    String getComment_content();
    String getReply_content();
    LocalDateTime getComment_datetime();
    LocalDateTime getReply_datetime();
    String getUser_name();
}
