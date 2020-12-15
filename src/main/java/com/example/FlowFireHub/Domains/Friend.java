package com.example.FlowFireHub.Domains;

import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Friend {

    @EmbeddedId
    private Key key;

    @JsonIgnore
    @ManyToOne()
    @MapsId("user_id")
    private User user;

    @JsonIgnore
    @ManyToOne()
    @MapsId("friend_id")
    private User friend;

    private boolean isActive;

    public Friend(User _user, User _friend) {
        this.user = _user;
        this.friend = _friend;
    }

    public Friend(Key _key, User _user, User _friend) {
        this.key = _key;
        this.user = _user;
        this.friend = _friend;
    }

    public Friend() {
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Embeddable
    public static class Key implements Serializable {
        private Long user_id;
        private Long friend_id;

        public Key(Long owner, Long person) {
            this.user_id = owner;
            this.friend_id = person;
        }

        public Key() {
        }

    }
}
