package com.puzzlix.solid_task.domain.notification;

import org.springframework.stereotype.Component;

@Component
public class EmailNotificationSender implements NotificationSender {


    @Override
    public void send(String message) {
        //이메일 외부IP 연동 처리
        System.out.println("[이메일 발송]" + message);
    }

    @Override
    public boolean supports(String type) {
        //이메일 타입 요청
        return "EMAIL".equalsIgnoreCase(type);
    }
}
