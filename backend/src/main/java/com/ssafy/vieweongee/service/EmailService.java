package com.ssafy.vieweongee.service;

//import org.springframework.ws.mime.MimeMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {
    String createAuthNum();
    String createTempPassword();
    MimeMessage createMessage(String to, String code) throws Exception;
    MimeMessage createMessagePw(String to, String password) throws Exception;
    String sendSimpleMessage(String to, String password) throws Exception;
}

