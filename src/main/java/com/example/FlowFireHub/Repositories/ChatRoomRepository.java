package com.example.FlowFireHub.Repositories;

import com.example.FlowFireHub.Domains.ChatMessage;
import com.example.FlowFireHub.Domains.ChatRoom;
import com.example.FlowFireHub.Utilities.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
