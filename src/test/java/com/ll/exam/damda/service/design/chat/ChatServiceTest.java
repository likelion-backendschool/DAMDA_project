package com.ll.exam.damda.service.design.chat;

import com.ll.exam.damda.entity.design.chat.ChatMessage;
import com.ll.exam.damda.entity.design.chat.ChatRoom;
import com.ll.exam.damda.entity.design.map.Plan;
import com.ll.exam.damda.repository.design.chat.ChatMessageRepository;
import com.ll.exam.damda.repository.design.chat.ChatRoomRepository;
import com.ll.exam.damda.service.design.map.PlanService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ChatServiceTest {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private PlanService planService;

    @Test
    void findAllRoom() {
    }

    @Test
    void findById() {
    }

    @Test
    @DisplayName("대화방 저장 테스트")
    @Rollback(false)
    void createRoom() {
        Plan plan = planService.create("테스트 계획", 3, "테스트", "사용자");
        chatRoomRepository.save(ChatRoom.builder()
                .plan(plan)
                .roomTitle("테스트 대화방")
                .build());
    }

    @Test
    @DisplayName("메시지 저장 테스트")
    @Rollback(false)
    void saveChatMessage() {
        Plan plan = planService.create("테스트 계획", 3, "테스트", "사용자");
        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.builder()
                .plan(plan)
                .roomTitle("테스트 대화방")
                .build());

        chatRoom.getChatMessages().add(ChatMessage.builder()
                .chatRoom(chatRoom)
                .userId("관리자")
                .content("테스트 메시지")
                .build());
    }

    @Test
    @DisplayName("계획 아이디로 채팅방 가져오기")
    @Rollback(false)
    void getChatRoom() {
        Plan plan = planService.create("테스트 계획", 3, "테스트", "사용자");
        chatRoomRepository.save(ChatRoom.builder()
                .plan(plan)
                .roomTitle(plan.getTitle())
                .build());
        ChatRoom chatRoom = chatRoomRepository.findByPlan_id(plan.getId()).orElseThrow(() -> new NoSuchElementException("채팅방이 존재하지 않습니다."));
        assertThat(chatRoom.getRoomTitle()).isEqualTo("테스트 계획");
    }
}