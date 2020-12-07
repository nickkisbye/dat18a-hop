package com.example.FlowFireHub.Domains;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String name;
    private boolean isPrivate;

    public ChatRoom(String name, boolean isPrivate) {
        this.name = name;
        this.isPrivate = isPrivate;
    }

    public ChatRoom() {}
}
