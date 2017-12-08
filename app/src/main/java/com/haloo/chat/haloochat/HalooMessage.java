package com.haloo.chat.haloochat;

public class HalooMessage {
    private String mMessage;
    private long mTimestamp;
    private User mUser;

    public HalooMessage(String message, long timestamp, User user) {
        mMessage = message;
        mTimestamp = timestamp;
        mUser = user;
    }

    public String getMessage() {
        return mMessage;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public User getUser() {
        return mUser;
    }
}
