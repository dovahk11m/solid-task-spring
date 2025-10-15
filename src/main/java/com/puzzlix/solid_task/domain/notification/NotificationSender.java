package com.puzzlix.solid_task.domain.notification;

/*
알림전략 설계
구현클래스에 강제시킬 추상 메서드
 */
public interface NotificationSender {

    void send(String message);
    boolean supports(String type);
}
