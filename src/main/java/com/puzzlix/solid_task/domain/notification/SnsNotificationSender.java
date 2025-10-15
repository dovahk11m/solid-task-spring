package com.puzzlix.solid_task.domain.notification;

import org.springframework.stereotype.Component;

@Component
public class SnsNotificationSender implements NotificationSender{

    @Override
    public void send(String message) {
        //SMS 외부IP 연동 처리
        System.out.println("[SMS 발송]" + message);
    }

    @Override
    public boolean supports(String type) {
        //SMS 타입 요청
        return "SMS".equalsIgnoreCase(type);
    }
}
