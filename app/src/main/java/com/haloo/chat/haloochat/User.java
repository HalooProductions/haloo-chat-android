package com.haloo.chat.haloochat;

public class User {
    private int mId;
    private String mName;
    private String mImgUrl;

    public User(int id, String name, String imgUrl) {
        mId = id;
        mName = name;
        mImgUrl = imgUrl;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getImgUrl() {
        return mImgUrl;
    }
}
