package com.example.FlowFireHub.Domains;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    @OneToOne(mappedBy = "roles", cascade = CascadeType.ALL)
    private User user;

    public Roles(Long id, String role) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Roles(){}

    public String getRole() {
        return name;
    }

    public void setRole(String name) {
        this.name = name;
    }
}
