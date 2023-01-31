package com.ssafy.vieweongee.service;

import javax.mail.internet.MimeMessage;

public interface EmailService {
    String createAuthNum();
    MimeMessage createMessage(String to, String code) throws Exception;
    MimeMessage createMessagePw(String to, String password) throws Exception;
    String sendSimpleMessage(String to, String password) throws Exception;

}
