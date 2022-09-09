package com.ll.exam.damda.repository.design.chat;

import com.ll.exam.damda.entity.design.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByPlan_id(Long id);
}
