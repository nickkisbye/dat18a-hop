package com.example.FlowFireHub.Domains;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CHAT_MESSAGE")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private MessageType type;
    private String content;
    private String sender;

    @JsonIgnore
    private LocalDateTime timeStamp;

    @JsonIgnore
    @ManyToOne
    private ChatRoom chatRoom;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE;
    }
    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
