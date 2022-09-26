package com.ll.exam.damda.controller.design.chat;

import com.ll.exam.damda.dto.design.chat.ChatMessageDto;
import com.ll.exam.damda.dto.design.chat.ChatRoomDto;
import com.ll.exam.damda.service.design.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {
    private final ChatService chatService;

    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoomDto> room() {
        return chatService.findAllRoom();
    }

    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }
    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoomDto roomInfo(@PathVariable Long roomId) {
        return chatService.findById(roomId);
    }

    // 특정 채팅방 메시지 조회
    @GetMapping("/room/messages/{roomId}")
    @ResponseBody
    public List<ChatMessageDto> roomMessages(@PathVariable Long roomId) {
        return chatService.findAllMessages(roomId);
    }
}
