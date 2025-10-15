package com.puzzlix.solid_task.domain.notification;

import com.solapi.sdk.SolapiClient;
import com.solapi.sdk.message.exception.SolapiMessageNotReceivedException;
import com.solapi.sdk.message.model.Message;
import com.solapi.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Component;

@Component
public class SmsNotificationSender implements NotificationSender {

    DefaultMessageService messageService = SolapiClient.INSTANCE.createInstance(
            "NCST9DCX5LUGZV9V",
            "A4YSJ8JNWL4KMHF92G64LZSEWJKFAXUE"
    );



    @Override
    public void send(String text) {

        //SMS 외부IP 연동 처리
        Message message = new Message();
        message.setFrom("01052464925");
        message.setTo("01052464925");
        message.setText(text);

        try {
            // send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
            messageService.send(message);
        } catch (SolapiMessageNotReceivedException exception) {
            // 발송에 실패한 메시지 목록을 확인할 수 있습니다!
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println("[SMS 발송]" + text);
    }

    @Override
    public boolean supports(String type) {
        //SMS 타입 요청
        return "SMS".equalsIgnoreCase(type);
    }
}
