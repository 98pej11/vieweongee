package com.ssafy.vieweongee.exception;

public class ParticipantNotFoundException extends RuntimeException {
    public ParticipantNotFoundException() {
        super("신청되지 않은 스터디입니다.");
    }
}
