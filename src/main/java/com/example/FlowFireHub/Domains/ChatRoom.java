package com.example.FlowFireHub.Domains;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private boolean isPrivate;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "CHATROOM_SUBSCRIPTION",
            joinColumns = @JoinColumn(name = "chatroom_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatMessage> chatMessage;

    public ChatRoom(String name, boolean isPrivate) {
        this.name = name;
        this.isPrivate = isPrivate;
        this.users = new ArrayList<>();
    }

    public ChatRoom() {
        this.users = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

    public boolean hasUser(User user) {
        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.get(i).getId() == user.getId()) {
                return true;
            }
        }
        return false;
    }
}
