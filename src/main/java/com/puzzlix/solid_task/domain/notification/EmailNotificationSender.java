package com.puzzlix.solid_task.domain.notification;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EmailNotificationSender implements NotificationSender {

    private final JavaMailSender javaMailSender;

    // application.yml에서 발신자 이메일 주소를 주입받음
    @Value("${spring.mail.username}")
    private String fromEmail;


    @Override
    public void send(String message) {
        //이메일 외부IP 연동 처리

        String toEmail = "choongechobiz@gmail.com";
        String title = "알림메일";

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(title);
            helper.setText(message, false);

            javaMailSender.send(mimeMessage);
            System.out.println("[이메일 발송 성공] to: " + toEmail);
        } catch (MessagingException e) {

            System.err.println("[이메일 발송 실패] " + e.getMessage());
            throw new RuntimeException("이메일 발송 중 오류가 발생했습니다.", e);
        }

        System.out.println("[발송 내용]" + message);
    }

    @Override
    public boolean supports(String type) {
        //이메일 타입 요청
        return "EMAIL".equalsIgnoreCase(type);
    }
}
