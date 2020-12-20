package com.example.FlowFireHub.Domains;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private boolean isPrivate;

    @JsonIgnore
    @ManyToMany
    private Set<User> users;

    public ChatRoom(String name, boolean isPrivate) {
        this.name = name;
        this.isPrivate = isPrivate;
    }

    public ChatRoom() {
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void AddUser(User user) {
        this.users.add(user);
    }
}
