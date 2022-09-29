package com.ll.exam.damda.controller.design.chat;

import com.ll.exam.damda.dto.design.chat.ChatMessageDto;
import com.ll.exam.damda.service.design.chat.ChatService;
import com.ll.exam.damda.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;

    private final ChatService chatService;

    private final UserService userService;

    @MessageMapping("/chat/message") //requestMapping과 유사 app/chat 맵핑
    public void enter(ChatMessageDto message) {
        log.debug("message = {}", message);
        if (chatService.saveChatMessage(message)){
            sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(), message); // /topic/chat/room/ + 방 번호 브로커로 메시지를 보냄
        }
    }
}
