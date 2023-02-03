package com.ssafy.vieweongee.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("등록되지 않은 유저입니다.");
    }
}
