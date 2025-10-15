package com.puzzlix.solid_task.domain.issue.dto;

import lombok.Getter;
import lombok.Setter;

/*
실시간 채팅 메시지를 담는 DTO
 */
@Getter
@Setter
public class ChatMessageDto {

    private Long issueId;
    private String sender; //보낸이
    private String content; //글내용
}
