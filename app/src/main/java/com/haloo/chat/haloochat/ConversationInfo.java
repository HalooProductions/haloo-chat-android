package com.haloo.chat.haloochat;

public class ConversationInfo {
    private int mSenderID;
    private int mRoomID;
    private String mImageUrl;
    private String mName;

    public ConversationInfo(int sender, String imageUrl, String name) {
        mSenderID = sender;
        mRoomID = 0; // Not a room, thus roomid is 0
        mImageUrl = imageUrl;
        mName = name;
    }

    public ConversationInfo(int sender, int roomID, String imageUrl, String name) {
        mSenderID = sender;
        mRoomID = roomID;
        mImageUrl = imageUrl;
        mName = name;
    }

    public int getSenderID() {
        return mSenderID;
    }

    public int getRoomID() {
        return mRoomID;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getName() {
        return mName;
    }
}
