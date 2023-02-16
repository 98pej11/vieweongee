package com.ssafy.vieweongee.repository;

//import com.ssafy.vieweongee.dto.comment.CommentReplyJoin;
import com.ssafy.vieweongee.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
//    @Query("SELECT c.id AS comment_id, c.user.id AS comment_user_id, r.id AS reply_id, r.user.id AS reply_user_id," +
//            "c.content AS comment_content, r.content AS reply_content, c.datetime AS comment_datetime, " +
//            "r.datetime AS reply_datetime, u.name AS user_name FROM Comment c LEFT JOIN Reply r ON c.id = r.comment.id LEFT JOIN User u ON (u.id = c.user.id OR u.id = r.user.id) WHERE c.study.id = :study_id ORDER BY c.id, r.id")
//    List<CommentReplyJoin> findAllComment(Long study_id);

    List<Comment> findByStudy_id(Long study_id);
}
