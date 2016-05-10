package com.consonance.invitation.entity;

/**
 * Created by Deity on 2016/5/10.
 */
public class OrderEntity {
    /**用户头像*/
    private String user_display;
    /**用户昵称*/
    private String user_nickName;

    public String getUser_display() {
        return user_display;
    }

    public void setUser_display(String user_display) {
        this.user_display = user_display;
    }

    public String getUser_nickName() {
        return user_nickName;
    }

    public void setUser_nickName(String user_nickName) {
        this.user_nickName = user_nickName;
    }
}
