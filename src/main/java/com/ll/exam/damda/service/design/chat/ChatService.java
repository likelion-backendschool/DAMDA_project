package com.ll.exam.damda.service.design.chat;

import com.ll.exam.damda.dto.design.chat.ChatMessageDto;
import com.ll.exam.damda.dto.design.chat.ChatRoomDto;
import com.ll.exam.damda.entity.design.chat.ChatMessage;
import com.ll.exam.damda.entity.design.chat.ChatRoom;
import com.ll.exam.damda.entity.design.map.Plan;
import com.ll.exam.damda.repository.design.chat.ChatMessageRepository;
import com.ll.exam.damda.repository.design.chat.ChatRoomRepository;
import com.ll.exam.damda.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;

    private final ChatRoomRepository chatRoomRepository;

    private final UserService userService;

    //채팅방 불러오기
    @Transactional(readOnly = true)
    public List<ChatRoomDto> findAllRoom() {
        //채팅방 최근 생성 순으로 반환
        List<ChatRoomDto> result = chatRoomRepository.findAll()
                .stream()
                .map(ChatRoom::toDto)
                .collect(Collectors.toList());

        return result;
    }

    //채팅방 아이디로 찾기
    @Transactional(readOnly = true)
    public ChatRoomDto findById(Long roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new NoSuchElementException("채팅방이 존재하지 않습니다."))
                .toDto();
    }

    //채팅방 계획 아이디로 찾기
    @Transactional(readOnly = true)
    public ChatRoomDto findByPlan_id(Plan plan) {
        return chatRoomRepository.findByPlan_id(plan.getId())
                .orElseThrow(() -> new NoSuchElementException("채팅방이 존재하지 않습니다."))
                .toDto();
    }

    //채팅방 메시지들 불러오기
    @Transactional(readOnly = true)
    public List<ChatMessageDto> findAllMessages(Long roomId) {
        return chatMessageRepository.findAllByChatRoom_idOrderByIdDesc(roomId)
                .stream()
                .map(ChatMessage::toDto)
                .collect(Collectors.toList());
    }

    //채팅방 생성
    public ChatRoomDto createRoom(Plan plan) {
        ChatRoom chatRoom = ChatRoom.builder()
                .plan(plan)
                .build();

        chatRoom = chatRoomRepository.save(chatRoom);

        ChatRoomDto chatRoomDto = chatRoom.toDto();
        return chatRoomDto;
    }

    //채팅 메시지 DB 저장
    public Boolean saveChatMessage(ChatMessageDto chatMessageDto) {
        if (chatMessageDto.getJoin() == 1 && chatMessageRepository.findAllByUser_username(chatMessageDto.getUser()).isPresent()){
            return false;
        }
        else {
            ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDto.getRoomId())
                    .orElseThrow(() -> new NoSuchElementException("채팅방이 존재하지 않습니다."));
            chatRoom.getChatMessages().add(chatMessageDto.toEntity(chatRoom, userService.getUser(chatMessageDto.getUser())));
            return true;
        }
    }
}