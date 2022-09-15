package com.ll.exam.damda.dto.design.chat;

import com.ll.exam.damda.entity.design.chat.ChatMessage;
import com.ll.exam.damda.entity.design.chat.ChatRoom;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatMessageDto {
    //채팅방 ID
    private Long roomId;
    //보내는 사람
    private String user;
    //내용
    private String content;

    public ChatMessage toEntity(ChatRoom chatRoom) {
        return ChatMessage.builder()
                .chatRoom(chatRoom)
                .userId(user)
                .content(content)
                .build();
    }
}