package com.puzzlix.solid_task.domain.notification.listener;

import com.puzzlix.solid_task.domain.issue.Issue;
import com.puzzlix.solid_task.domain.issue.event.IssueStatusChangedEvent;
import com.puzzlix.solid_task.domain.notification.NotificationSender;
import com.puzzlix.solid_task.domain.notification.NotificationSenderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class IssueNotificationEventListener {

    private final NotificationSenderFactory notificationSenderFactory;

    //알림 정책 - yml -> EMAIL
    @Value("${notification.policy.on-status-done}")
    private String onStatusDoneType;

    @EventListener
    public void handleIssueStatusChangeEvent(IssueStatusChangedEvent event) {
        Issue issue = event.getIssue();
        String messsage = "이슈 #[" + issue.getTitle() + "]의 상태가 #[" + issue.getIssueStatus() + "]로 변경";

        if ("DONE".equalsIgnoreCase(issue.getIssueStatus().name())) {
            //팩토리를 활용해 알림 전략 도출
            NotificationSender sender = notificationSenderFactory.findSender(onStatusDoneType);
            //메시지 전송
            sender.send(messsage);
        }
    }
}
