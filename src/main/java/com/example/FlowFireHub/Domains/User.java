package com.example.FlowFireHub.Domains;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Steam steam;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private FireFlow fireFlow;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Friend> friend = new HashSet<>();

    @JsonIgnore
//    @JsonIgnoreProperties("friend")
    @ManyToOne()
    private Role role;

    @JsonIgnore
    @ManyToMany(mappedBy = "users")
    private List<ChatRoom> chatRooms;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name="USER_ID")
    private Set<ChatMessage> chatMessages;

    public User() {
    }

    public User(String username, Role role) {
        this.username = username;
        this.role = role;
    }

    public User(String username, Long id) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Steam getSteam() {
        return steam;
    }

    public void setSteam(Steam steam) {
        this.steam = steam;
    }

    public FireFlow getFireFlow() {
        return fireFlow;
    }

    public void setFireFlow(FireFlow fireFlow) {
        this.fireFlow = fireFlow;
    }

    public Set<Friend> getFriend() {
        return friend;
    }

    public void setFriend(Set<Friend> friend) {
        this.friend = friend;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public void setChatRooms(List<ChatRoom> chatRooms) {
        this.chatRooms = chatRooms;
    }

    public Set<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(Set<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }
}