package com.example.FlowFireHub.Domains;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

    @Entity
    @Table(name = "steam")
    public class Steam {

        @Id
        @Column(name = "id")
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private Long id;

        private String username;
        private String steamid;

        @JsonIgnore
        @OneToOne(cascade = CascadeType.ALL, optional = false)
        @JoinColumn(name = "user_id")
        private User user;

        public Steam() {
        }

        public Steam(String username, String steamid, User user) {
            this.username = username;
            this.steamid = steamid;
            this.user = user;
            //this.user.setUser(this);
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

        public String getSteamid() {
            return steamid;
        }

        public void setSteamid(String steamid) {
            this.steamid = steamid;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
