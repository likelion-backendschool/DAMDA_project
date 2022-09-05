package com.ll.exam.damda.entity.design.chat;

import com.ll.exam.damda.dto.design.chat.ChatRoomDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //나중에 여행 계획과 OneToOne 맵핑 필요
    @Column(nullable = false)
    private Long planId;

    @Column(nullable = false)
    private String roomTitle;

    @Builder.Default
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public ChatRoomDto toDto() {
        ChatRoomDto room = new ChatRoomDto();
        room.setRoomId(id);
        room.setRoomName(roomTitle);
        return room;
    }
}
