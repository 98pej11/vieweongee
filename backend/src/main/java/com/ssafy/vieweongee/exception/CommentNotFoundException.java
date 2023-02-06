package com.ssafy.vieweongee.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException() {
        super("등록되지 않은 댓글입니다.");
    }
}
