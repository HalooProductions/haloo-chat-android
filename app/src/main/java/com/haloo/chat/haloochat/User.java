package com.haloo.chat.haloochat;

public class User {
    private long mId;
    private String mName;
    private String mImgUrl;

    public User(long id, String name, String imgUrl) {
        mId = id;
        mName = name;
        mImgUrl = imgUrl;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getImgUrl() {
        return mImgUrl;
    }
}
