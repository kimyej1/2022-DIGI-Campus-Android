package com.kbstar.m01weblist;

public class UserData {
    // Variables
    private String userIdx, userName, userId, userLevel, userMemo;

    // Getters and Setters
    public String getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(String userIdx) {
        this.userIdx = "No. " + userIdx;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserMemo() {
        return userMemo;
    }

    public void setUserMemo(String userMemo) {
        this.userMemo = "memo :  " + userMemo;
    }
}
