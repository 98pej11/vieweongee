package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.comment.CommentReplyJoin;
import com.ssafy.vieweongee.dto.comment.CommentResponse;
import com.ssafy.vieweongee.dto.comment.CreateCommentRequest;
import com.ssafy.vieweongee.dto.comment.CreateReplyRequest;
import com.ssafy.vieweongee.entity.Comment;
import com.ssafy.vieweongee.entity.Reply;
import com.ssafy.vieweongee.entity.Study;
import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.exception.CommentNotFoundException;
import com.ssafy.vieweongee.exception.ReplyNotFoundException;
import com.ssafy.vieweongee.repository.CommentRepository;
import com.ssafy.vieweongee.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    @Override
    @Transactional
    public Comment createComment(User user, Study study, CreateCommentRequest createCommentRequest) {
        return commentRepository.save(createCommentRequest.toEntity(user, study));
    }

    @Override
    @Transactional
    public Comment updateComment(Long comment_id, Comment comment) {
        Comment origin = commentRepository.findById(comment_id).orElseThrow(() -> new CommentNotFoundException());

        Comment result = Comment.builder()
                .id(comment_id)
                .content(comment.getContent())
                .datetime(origin.getDatetime())
                .study(origin.getStudy())
                .user(origin.getUser())
                .build();
        commentRepository.save(result);

        return result;
    }

    @Override
    @Transactional
    public void deleteComment(Long comment_id) {
        commentRepository.deleteById(comment_id);
    }

    @Override
    public Comment getCommentById(Long comment_id) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(() -> new CommentNotFoundException());
        return comment;
    }

    @Override
    @Transactional
    public Reply createReply(User user, Comment comment, CreateReplyRequest createReplyRequest) {
        return replyRepository.save(createReplyRequest.toEntity(user, comment));
    }

    @Override
    @Transactional
    public Reply updateReply(Long reply_id, Reply reply) {
        Reply origin = replyRepository.findById(reply_id).orElseThrow(() -> new ReplyNotFoundException());

        Reply result = Reply.builder()
                .id(reply_id)
                .content(reply.getContent())
                .datetime(origin.getDatetime())
                .comment(origin.getComment())
                .user(origin.getUser())
                .build();

        replyRepository.save(result);
        return result;
    }

    @Override
    @Transactional
    public void deleteReply(Long reply_id) {
        replyRepository.deleteById(reply_id);
    }

    @Override
    public List<CommentResponse> getAllComment(Long study_id) {
        List<CommentReplyJoin> commentList = commentRepository.findAllComment(study_id);
        if (commentList.isEmpty()) {
            return null;
        }

        List<CommentResponse> result = new ArrayList<>();
        Long comment_id = 0L;
        Long reply_id = 0L;
        for (int i = 0; i < commentList.size(); i++){
            CommentReplyJoin now = commentList.get(i);

            if (comment_id != now.getComment_id()) {
                CommentResponse comment = new CommentResponse(now.getComment_user_id(), now.getUser_name(), now.getComment_id(), 1, now.getComment_content(), now.getComment_datetime());
                result.add(comment);
                comment_id = now.getComment_id();
                System.out.println(comment);

                if (now.getReply_id() != null) {
                    CommentResponse reply = new CommentResponse(now.getReply_user_id(), now.getUser_name(), now.getComment_id(), now.getReply_id(), 2, now.getReply_content(), now.getReply_datetime());
                    result.add(reply);
                    reply_id = now.getReply_id();
                    System.out.println(reply);
                }

            } else if (comment_id == now.getComment_id() && reply_id != now.getReply_id()) {
                CommentResponse reply = new CommentResponse(now.getReply_user_id(), now.getUser_name(), now.getComment_id(), now.getReply_id(), 2, now.getReply_content(), now.getReply_datetime());
                result.add(reply);
                reply_id = now.getReply_id();
                System.out.println(reply);
            }

            if (i != commentList.size() - 1 && commentList.get(i + 1).getComment_id() != now.getComment_id()) {
                reply_id = 0L;
            }

//            if (comment_id != commentList.get(i).getComment_id()){
//                CommentReplyJoin now = commentList.get(i);
//                CommentResponse comment = new CommentResponse(now.getComment_user_id(), now.getUser_name(), now.getComment_id(), 1, now.getComment_content(), now.getComment_datetime());
//                result.add(comment);
//                System.out.println(comment);
//
//                if (now.getReply_id() != null) {
//                    CommentResponse reply = new CommentResponse(now.getReply_user_id(), now.getUser_name(), now.getComment_id(), now.getReply_id(), 2, now.getReply_content(), now.getReply_datetime());
//                    result.add(reply);
//                    System.out.println(reply);
//                }
//
//                comment_id = now.getComment_id();
//            } else {
//                CommentReplyJoin now = commentList.get(i);
//                CommentResponse reply = new CommentResponse(now.getReply_user_id(), now.getUser_name(), now.getComment_id(), now.getReply_id(), 2, now.getReply_content(), now.getReply_datetime());
//                result.add(reply);
//            }
        }

        return result;
    }
}
