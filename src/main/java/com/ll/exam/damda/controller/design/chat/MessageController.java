package com.ll.exam.damda.controller.design.chat;

import com.ll.exam.damda.dto.design.chat.ChatMessageDto;
import com.ll.exam.damda.service.design.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;

    private final ChatService chatService;

    @MessageMapping("/chat/message") //requestMapping과 유사 app/chat 맵핑
    public void enter(ChatMessageDto message) {
        System.out.println("message = " + message);
        chatService.saveChatMessage(message);
        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(), message); // /topic/chat/room/ + 방 번호 브로커로 메시지를 보냄
    }
}
