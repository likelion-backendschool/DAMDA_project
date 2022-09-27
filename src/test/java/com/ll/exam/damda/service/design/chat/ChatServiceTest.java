package com.ll.exam.damda.service.design.chat;

import com.ll.exam.damda.entity.design.chat.ChatMessage;
import com.ll.exam.damda.entity.design.chat.ChatRoom;
import com.ll.exam.damda.entity.design.map.Plan;
import com.ll.exam.damda.entity.user.SiteUser;
import com.ll.exam.damda.repository.design.chat.ChatMessageRepository;
import com.ll.exam.damda.repository.design.chat.ChatRoomRepository;
import com.ll.exam.damda.service.design.map.PlanService;
import com.ll.exam.damda.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ChatServiceTest {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private PlanService planService;

    @Autowired
    private UserService userService;

    /*@Test
    void findAllRoom() {
    }

    @Test
    void findById() {
    }

    @Test
    @DisplayName("대화방 저장 테스트")
    @Rollback(false)
    void createRoom() {
        Plan plan = planService.create("테스트 계획", "2022-10-10", "2022-10-15", "테스트", "사용자");
        chatRoomRepository.save(ChatRoom.builder()
                .plan(plan)
                .roomTitle("테스트 대화방")
                .build());
    }

    @Test
    @DisplayName("메시지 저장 테스트")
    @Rollback(false)
    void saveChatMessage() {
        Plan plan = planService.create("테스트 계획", "2022-10-10", "2022-10-15", "테스트", "사용자");
        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.builder()
                .plan(plan)
                .roomTitle("테스트 대화방")
                .build());

        SiteUser siteUser = userService.create("user4", "nick4", "user4@email.com", "1234");
        chatRoom.getChatMessages().add(ChatMessage.builder()
                .chatRoom(chatRoom)
                .user(siteUser)
                .content("테스트 메시지")
                .build());
    }

    @Test
    @DisplayName("계획 아이디로 채팅방 가져오기")
    @Rollback(false)
    void getChatRoom() {
        Plan plan = planService.create("테스트 계획", "2022-10-10", "2022-10-15", "테스트", "사용자");
        chatRoomRepository.save(ChatRoom.builder()
                .plan(plan)
                .roomTitle(plan.getTitle())
                .build());
        ChatRoom chatRoom = chatRoomRepository.findByPlan_id(plan.getId()).orElseThrow(() -> new NoSuchElementException("채팅방이 존재하지 않습니다."));
        assertThat(chatRoom.getRoomTitle()).isEqualTo("테스트 계획");
    }*/
}