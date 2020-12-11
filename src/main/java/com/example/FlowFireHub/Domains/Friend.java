package com.example.FlowFireHub.Domains;

import org.springframework.stereotype.Component;


@Component
public class Friend {

    private String userid;
    private String friendid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFriendid() {
        return friendid;
    }

    public void setFriendid(String friendid) {
        this.friendid = friendid;
    }

}
