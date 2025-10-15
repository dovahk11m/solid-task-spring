package com.puzzlix.solid_task.domain.issue;

import com.puzzlix.solid_task.domain.issue.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/*
채팅 웹 테스트용
 */

@RequiredArgsConstructor
@Controller
public class IssueChatController {

    //스톰프 메시지 템플릿
    private final SimpMessagingTemplate messagingTemplate;

    //클라가 /app/chat/{issueId} 경로로 메시지를 보내면 콜백
    @MessageMapping("/chat/{issueId}")
    public void habdleChatMessage(
            @DestinationVariable Long issueId,
            ChatMessageDto messageDto
    ) {
        messagingTemplate.convertAndSend("/topic/issues/" + issueId, messageDto);
    }
}
