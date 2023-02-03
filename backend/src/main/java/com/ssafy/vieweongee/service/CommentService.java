package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.comment.CommentResponse;
import com.ssafy.vieweongee.dto.comment.CreateCommentRequest;
import com.ssafy.vieweongee.dto.comment.CreateReplyRequest;
import com.ssafy.vieweongee.entity.Comment;
import com.ssafy.vieweongee.entity.Reply;
import com.ssafy.vieweongee.entity.Study;
import com.ssafy.vieweongee.entity.User;

import java.util.List;

public interface CommentService {
    Comment createComment(User user, Study study, CreateCommentRequest createCommentRequest);

    Comment updateComment(Long comment_id, Comment comment);

    void deleteComment(Long comment_id);

    Comment getCommentById(Long comment_id);

    Reply createReply(User user, Comment comment, CreateReplyRequest createReplyRequest);

    Reply updateReply(Long reply_id, Reply reply);

    void deleteReply(Long reply_id);

    List<CommentResponse> getAllComment(Long study_id);
}
