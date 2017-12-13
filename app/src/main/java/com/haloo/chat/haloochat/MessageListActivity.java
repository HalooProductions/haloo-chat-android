package com.haloo.chat.haloochat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageListActivity extends AppCompatActivity implements WebSocketConnection.ServerListener {

    private long currentId;
    private long ownId;
    private String currentName;

    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private List<HalooMessage> messageList;
    private Button sendMessageBtn;
    private EditText messageInput;
    RequestQueue queue;

    private final String SERVER_URL = "ws://192.168.0.197:8000/ws";
    private WebSocketConnection mWebSocketConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        Intent intent = getIntent();
        ConversationInfo info = intent.getParcelableExtra("info");
        setTitle(info.getName());
        currentName = info.getName();

        ownId = intent.getLongExtra("userId", 0);

        sendMessageBtn = findViewById(R.id.button_chatbox_send);
        messageInput = findViewById(R.id.edittext_chatbox);

        messageList = new ArrayList<>();

        mMessageRecycler = findViewById(R.id.recyclerview_message_list);
        mMessageAdapter = new MessageListAdapter(this, messageList, ownId);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);

        mMessageRecycler.scrollToPosition(messageList.size() - 1);

        mWebSocketConnection = new WebSocketConnection(SERVER_URL);

        if (info.getSenderID() != 0) {
            getUserMessages(info.getSenderID());
            currentId = info.getSenderID();
        } else if (info.getRoomID() != 0) {
            getRoomMessages(info.getRoomID());
            currentId = info.getRoomID();
        }

        sendMessageBtn.setOnClickListener(view -> {
            String messageText = messageInput.getText().toString().trim();

            messageList.add(new HalooMessage(messageText, System.currentTimeMillis(), new User(ownId, "Riku Wikman", "das.jpg")));
            mMessageAdapter.notifyDataSetChanged();

            mMessageRecycler.scrollToPosition(messageList.size() - 1);

            JSONObject messageJson = new JSONObject();
            try {
                messageJson.put("sender", Long.toString(ownId));
                messageJson.put("receiver", Long.toString(currentId));
                messageJson.put("message", messageText);
                messageJson.put("timestamp", System.currentTimeMillis());

                String messageJsonStr = messageJson.toString();
                Log.i("message-json", messageJsonStr);
                mWebSocketConnection.sendMessage(messageJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            messageInput.setText("");
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebSocketConnection.connect(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebSocketConnection.disconnect();
    }

    @Override
    public void onNewMessage(String message) {
        try {
            JSONObject messageJson = new JSONObject(message);
            String senderIdStr = messageJson.getString("sender");
            String messageText = messageJson.getString("message");
            long senderId = Long.parseLong(senderIdStr);

            Log.i("senderid", senderIdStr);
            Log.i("ownid", Long.toString(ownId));

            if (senderId != ownId) {
                messageList.add(new HalooMessage(messageText, System.currentTimeMillis(), new User(senderId, currentName, "asd.png")));
                mMessageAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChange(WebSocketConnection.ConnectionStatus status) {
        Log.i("websocket_status", status == WebSocketConnection.ConnectionStatus.CONNECTED ? "connected" : "disconnected");
    }

    private void getUserMessages(long id) {
        Log.i("receiver_id", Long.toString(id));
        String url = "http://192.168.0.197:8000/chatlog?user_id=" + Long.toString(ownId) + "&receiver_id=" + id;
        JsonArrayRequest request = new JsonArrayRequest(url, response -> {
            try {
                ArrayList<HalooMessage> list = HalooMessage.deserializeJson(response);
                messageList.addAll(list);
                mMessageAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("jsonerror", "Error: " + error.getMessage()));

        queue = ConversationQueueSingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        queue.add(request);
    }

    private void getRoomMessages(long id) {
        String url = "http://192.168.0.197:8000/chatlog?room_id=" + id;
        JsonArrayRequest request = new JsonArrayRequest(url, response -> {
            try {
                ArrayList<HalooMessage> list = HalooMessage.deserializeJson(response);
                messageList.addAll(list);
                mMessageAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("jsonerror", "Error: " + error.getMessage()));

        queue = ConversationQueueSingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        queue.add(request);
    }
}
