package com.ll.exam.damda.service.design.chat;

import com.ll.exam.damda.entity.design.chat.ChatMessage;
import com.ll.exam.damda.entity.design.chat.ChatRoom;
import com.ll.exam.damda.repository.design.chat.ChatMessageRepository;
import com.ll.exam.damda.repository.design.chat.ChatRoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ChatServiceTest {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Test
    void findAllRoom() {
    }

    @Test
    void findById() {
    }

    @Test
    @DisplayName("대화방 저장 테스트")
    void createRoom() {
        chatRoomRepository.save(ChatRoom.builder()
                .planId(1L)
                .roomTitle("테스트 대화방")
                .build());
    }

    @Test
    @DisplayName("메시지 저장 테스트")
    @Rollback(false)
    void saveChatMessage() {
        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.builder()
                .planId(1L)
                .roomTitle("테스트 대화방")
                .build());

        chatRoom.getChatMessages().add(ChatMessage.builder()
                .chatRoom(chatRoom)
                .userId("관리자")
                .content("테스트 메시지")
                .build());
    }
}