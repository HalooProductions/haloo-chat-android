package com.haloo.chat.haloochat;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConversationInfo implements Parcelable {
    private long mSenderID;
    private long mRoomID;
    private String mImageUrl;
    private String mName;
    private String mPreview;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(mSenderID);
        out.writeLong(mRoomID);
        out.writeString(mImageUrl);
        out.writeString(mName);
        out.writeString(mPreview);
    }

    public static final Parcelable.Creator<ConversationInfo> CREATOR = new Parcelable.Creator<ConversationInfo>() {
        public ConversationInfo createFromParcel(Parcel in) {
            return new ConversationInfo(in);
        }

        public ConversationInfo[] newArray(int size) {
            return new ConversationInfo[size];
        }
    };

    public ConversationInfo() {}

    public ConversationInfo(Parcel in) {
        mSenderID = in.readLong();
        mRoomID = in.readLong();
        mImageUrl = in.readString();
        mName = in.readString();
        mPreview = in.readString();
    }

    public ConversationInfo(int sender, String imageUrl, String name, String preview) {
        mSenderID = sender;
        mRoomID = 0; // Not a room, thus roomid is 0
        mImageUrl = imageUrl;
        mName = name;
        mPreview = preview;
    }

    public ConversationInfo(int sender, int roomID, String imageUrl, String name, String preview) {
        mSenderID = sender;
        mRoomID = roomID;
        mImageUrl = imageUrl;
        mName = name;
        mPreview = preview;
    }

    public long getSenderID() {
        return mSenderID;
    }

    public long getRoomID() {
        return mRoomID;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getName() {
        return mName;
    }

    public String getPreview() {
        return mPreview;
    }

    private ConversationInfo readConversationJson(JSONObject obj) throws JSONException {
        mSenderID = obj.has("ID") ? obj.getLong("ID") : 0;
        mName = obj.getString("Name");
        mPreview = "...";
        mImageUrl = obj.getString("Picture");
        mRoomID = obj.has("Room_ID") ? obj.getLong("Room_ID") : 0;
        return this;
    }

    public static ArrayList<ConversationInfo> deserializeJson(JSONArray arr) {
        ArrayList<ConversationInfo> list = new ArrayList<>();

        try {
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = (JSONObject) arr.get(i);

                ConversationInfo conversation = new ConversationInfo().readConversationJson(obj);
                list.add(conversation);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
