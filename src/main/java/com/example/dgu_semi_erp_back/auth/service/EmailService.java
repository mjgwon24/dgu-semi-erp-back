package com.example.dgu_semi_erp_back.auth.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) throws MessagingException {
        String subject = "회원가입 이메일 인증 OTP";
        String content = "<p>인증번호: <b>" + otp + "</b></p>"
                + "<p>5분 내로 입력해주세요.</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    public String generateOtp() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
    }
}
