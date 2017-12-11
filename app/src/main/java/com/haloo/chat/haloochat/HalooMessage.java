package com.haloo.chat.haloochat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HalooMessage {
    private String mMessage;
    private long mTimestamp;
    private User mUser;

    public HalooMessage(String message, long timestamp, User user) {
        mMessage = message;
        mTimestamp = timestamp;
        mUser = user;
    }

    public HalooMessage(JSONObject obj) throws JSONException {
        mMessage = obj.getString("message");
        mTimestamp = obj.getLong("timestamp");
        mUser = new User(obj.getInt("sender"), obj.getString("name"), "temp.png");
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

    public static ArrayList<HalooMessage> deserializeJson(JSONArray arr) throws JSONException {
        ArrayList<HalooMessage> list = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = (JSONObject) arr.get(i);

            HalooMessage message = new HalooMessage(obj);
            list.add(message);
        }

        return list;
    }
}
