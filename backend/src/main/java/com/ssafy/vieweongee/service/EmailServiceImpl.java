package com.ssafy.vieweongee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
    public String createTempPassword() {
        String password = "";
        for (int i = 0; i < 12; i++) {
            password += (char) ((Math.random() * 26) + 97);
        }
        return password;
    }

    @Override
    public MimeMessage createMessage(String to, String code) throws Exception{ // 회원가입 시 인증 메일 전송
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to); //보내는 대상
        message.setSubject("뷰엉이 이메일 확인 코드"); //제목

        String msg="";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인 코드</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 인증 코드 란에 입력하세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += code;
        msg += "</td></tr></tbody></table></div>";

        message.setText(msg, "utf-8", "html"); //내용
        message.setFrom(new InternetAddress("vieweongee701@gmail.com","vieweongee")); //보내는 사람

        return message;
    }

    @Override
    public MimeMessage createMessagePw(String to, String password) throws Exception{ // 비밀번호찾기 시 임시 비밀번호 발급
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to); //보내는 대상
        message.setSubject("뷰엉이 임시 비밀번호 발급"); //제목

        String msg="";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">임시 비밀번호 발급</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">하단의 임시 비밀번호로 로그인 하세요.</p>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">로그인 후 보안을 위해 비밀번호를 꼭 변경해주세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += password;
        msg += "</td></tr></tbody></table></div>";

        message.setText(msg, "utf-8", "html"); //내용
        message.setFrom(new InternetAddress("vieweongee701@gmail.com","vieweongee")); //보내는 사람

        return message;
    }

    @Override
    public String sendSimpleMessage(String to, String type) throws Exception {
        MimeMessage message;

        String code = createAuthNum();
        String password = createTempPassword();
        String value = "";

        if(type.equals("code")) {
            message = createMessage(to, code);
            value = code;
        }

        else if(type.equals("password")){
            message = createMessagePw(to, password);
            value = password;
        }
        else
            message = null;

        try { //예외처리
            emailSender.send(message);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return value;
    }
}