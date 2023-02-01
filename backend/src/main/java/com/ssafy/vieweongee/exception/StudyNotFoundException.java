package com.ssafy.vieweongee.exception;

public class StudyNotFoundException extends RuntimeException {
    public StudyNotFoundException() {
        super("등록되지 않은 스터디 게시글입니다.");
    }
}
