package com.example.FlowFireHub.Domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String username;

    @JsonIgnoreProperties("friend")
    @OneToOne(mappedBy = "user")
    private Steam steam;

    @JsonIgnoreProperties("friend")
    @OneToOne(mappedBy = "user")
    private FireFlow fireFlow;

    @JsonIgnoreProperties("friend")
    @ManyToMany()
    private Set<User> friend;

    @JsonIgnoreProperties("friend")
    @ManyToOne()
    private Role role;


    public User() {
    }

    public User(String username, Role role) {
        this.username = username;
        this.role = role;
    }

    public User(String username, Long id) {
    }

    public void setUser(Steam steam) {

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public FireFlow getFireFlow() {
        return fireFlow;
    }

    public void setFireFlow(FireFlow fireFlow) {
        this.fireFlow = fireFlow;
    }

    public Set<User> getFriend() {
        return friend;
    }

    public void setFriend(Set<User> friend) {
        this.friend = friend;
    }


}