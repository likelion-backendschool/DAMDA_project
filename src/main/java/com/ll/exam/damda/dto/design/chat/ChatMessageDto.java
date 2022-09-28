package com.ll.exam.damda.dto.design.chat;

import com.ll.exam.damda.entity.design.chat.ChatMessage;
import com.ll.exam.damda.entity.design.chat.ChatRoom;
import com.ll.exam.damda.entity.user.SiteUser;
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

    private Long join;

    public ChatMessage toEntity(ChatRoom chatRoom, SiteUser user) {
        return ChatMessage.builder()
                .chatRoom(chatRoom)
                .user(user)
                .content(content)
                .build();
    }
}