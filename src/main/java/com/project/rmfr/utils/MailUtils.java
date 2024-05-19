package com.project.rmfr.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MailUtils {
    @Autowired
    private JavaMailSender sender;

    public Map<String, Object> sendEmail(String toAddress) {
        Map<String, Object> result = new HashMap<String, Object>();
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String validCode = createToken();
        String base64ValidCode = Base64Util.encode(validCode);
        result.put("token", base64ValidCode);

        try {
            helper.setTo(toAddress);
            helper.setSubject("rmfr 회원가입 인증 메일입니다.");
            helper.setText("rmfr 회원가입 인증 메일입니다.\n회원가입 화면으로 돌아가 아래 인증코드를 입력해주세요.\n\n인증코드 : "+ validCode);
            result.put("resultCode", 200);
            result.put("token", base64ValidCode);
        } catch (MessagingException e) {
            e.printStackTrace();
            result.put("resultCode", 500);
        }
        sender.send(message);
        return result;
    }

    public String createToken() {
        String token = "";
        int tokenLength = 8;
        for ( int i = 0; i < tokenLength; i++ ) {

            int randomKey = (int) (Math.random() * 10);

            if ( randomKey < 4 ) {
                char key = (char) ((Math.random() * 26) + 65);
                token += key + "";
            } else {
                int key = (int) (Math.random() * 10);
                token += key + "";
            }
        }

        return token;
    }
}
