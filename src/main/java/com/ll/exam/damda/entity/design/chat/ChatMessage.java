package com.ll.exam.damda.entity.design.chat;

import com.ll.exam.damda.dto.design.chat.ChatMessageDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom chatRoom;

    //나중에 User객체로 변경하고 연관 맵핑 필요
    @Column(nullable = false)
    private String userId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    public ChatMessageDto toDto() {
        ChatMessageDto message = new ChatMessageDto();
        message.setRoomId(chatRoom.getId());
        message.setUser(userId);
        message.setContent(content);
        return message;
    }
}