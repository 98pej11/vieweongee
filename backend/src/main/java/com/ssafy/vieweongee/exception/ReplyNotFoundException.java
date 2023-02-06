package com.ssafy.vieweongee.exception;

public class ReplyNotFoundException extends RuntimeException {
    public ReplyNotFoundException() {
        super("등록되지 않은 대댓글입니다.");
    }
}
