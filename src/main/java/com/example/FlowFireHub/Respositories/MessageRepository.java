package com.example.FlowFireHub.Respositories;

import com.example.FlowFireHub.Domains.ChatMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<ChatMessage, Long> {
}
