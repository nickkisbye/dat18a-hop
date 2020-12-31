package com.example.FlowFireHub.Repositories;

import com.example.FlowFireHub.Domains.ChatMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends CrudRepository<ChatMessage, Long> {
    @Query("select c from ChatMessage c where c.chatRoom.id=:id")
    List<ChatMessage> getMessageByRoomId(Long id);
}
