package com.ll.exam.damda.repository.design.chat;

import com.ll.exam.damda.entity.design.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByChatRoom_idOrderByIdDesc(Long roomId);
}
