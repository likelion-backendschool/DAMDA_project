package com.ll.exam.damda.entity.design.chat;

import com.ll.exam.damda.dto.design.chat.ChatRoomDto;
import com.ll.exam.damda.entity.design.map.Plan;
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
    
    //여행 계획과 OneToOne 맵핑
    @OneToOne
    @JoinColumn(nullable = false, name = "plan_id")
    private Plan plan;

    @Builder.Default
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public ChatRoomDto toDto() {
        ChatRoomDto room = new ChatRoomDto();
        room.setRoomId(id);
        room.setRoomName(plan.getTitle());
        return room;
    }
}
