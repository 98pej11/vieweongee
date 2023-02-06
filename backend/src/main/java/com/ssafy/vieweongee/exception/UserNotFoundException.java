package com.ssafy.vieweongee.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("등록되지 않은 사용자입니다.");
    }
}
