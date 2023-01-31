package com.ssafy.vieweongee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message.RecipientType;
import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService{
    @Autowired
    private JavaMailSender emailSender;

    @Override
    public String createAuthNum(){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i=0; i<6; i++){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    @Override
    public MimeMessage createMessage(String to, String code) throws Exception{ // 회원가입 시 인증 메일 전송
//        logger.info("대상 : " + to);
//        logger.info("인증 번호 : " + code);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to); //보내는 대상
        message.setSubject("확인 코드: " + code); //제목

        String msg="";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 인증 코드 란에 입력하세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += code;
        msg += "</td></tr></tbody></table></div>";

        message.setText(msg, "utf-8", "html"); //내용
        message.setFrom(new InternetAddress("shadberry0319@gmail.com","vieweongee")); //보내는 사람

        return message;
    }

    @Override
    public MimeMessage createMessagePw(String to, String password) throws Exception{ // 비밀번호찾기 시 임시 비밀번호 발급
//        logger.info("대상 : "+ to);
//        logger.info("임시 비밀번호 : " + password);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to); //보내는 대상
        message.setSubject("임시 비밀번호: " + password); //제목

        String msg="";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">임시 비밀번호 발급</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">하단의 임시 비밀번호로 로그인 하세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += password;
        msg += "</td></tr></tbody></table></div>";

        message.setText(msg, "utf-8", "html"); //내용
        message.setFrom(new InternetAddress("ssafy6a201@gmail.com","inview")); //보내는 사람
        return message;
    }

    @Override
    public String sendSimpleMessage(String to, String password) throws Exception {
        MimeMessage message;

        String code = createAuthNum();

        if(password.equals("")){
            System.out.println("MailServiceImpl : sendSimpleMessage");
            message = createMessage(to, code);
        }
        else
            message = createMessagePw(to, password);

        try { //예외처리
            System.out.println(message);
            emailSender.send(message);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        return code;
    }
}