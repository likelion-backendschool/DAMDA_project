package com.ll.exam.damda.service.design.chat;

import com.ll.exam.damda.dto.design.chat.ChatMessageDto;
import com.ll.exam.damda.dto.design.chat.ChatRoomDto;
import com.ll.exam.damda.entity.design.chat.ChatRoom;
import com.ll.exam.damda.repository.design.chat.ChatMessageRepository;
import com.ll.exam.damda.repository.design.chat.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;

    private final ChatRoomRepository chatRoomRepository;


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

    //채팅방 하나 불러오기
    @Transactional(readOnly = true)
    public ChatRoomDto findById(Long roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new NoSuchElementException("채팅방이 존재하지 않습니다."))
                .toDto();
    }

    //채팅방 생성
    public ChatRoomDto createRoom(String name) {
        ChatRoom chatRoom = ChatRoom.builder()
                .planId(1L)
                .roomTitle(name)
                .build();

        chatRoom = chatRoomRepository.save(chatRoom);

        ChatRoomDto chatRoomDto = chatRoom.toDto();
        return chatRoomDto;
    }

    //채팅 메시지 DB 저장
    public void saveChatMessage(ChatMessageDto chatMessageDto) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDto.getRoomId())
                .orElseThrow(() -> new NoSuchElementException("채팅방이 존재하지 않습니다."));

        chatRoom.getChatMessages().add(chatMessageDto.toEntity(chatRoom));
    }
}