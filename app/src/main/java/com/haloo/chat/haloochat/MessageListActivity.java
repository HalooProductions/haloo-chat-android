package com.haloo.chat.haloochat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageListActivity extends AppCompatActivity {

    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private List<HalooMessage> messageList;
    private Button sendMessageBtn;
    private EditText messageInput;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        Intent intent = getIntent();
        ConversationInfo info = intent.getParcelableExtra("info");
        setTitle(info.getName());

        sendMessageBtn = findViewById(R.id.button_chatbox_send);
        messageInput = findViewById(R.id.edittext_chatbox);

        messageList = new ArrayList<>();

        mMessageRecycler = findViewById(R.id.recyclerview_message_list);
        mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);

        if (info.getSenderID() != 0) {
            getUserMessages(info.getSenderID());
        } else if (info.getRoomID() != 0) {
            getRoomMessages(info.getRoomID());
        }

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageList.add(new HalooMessage(messageInput.getText().toString().trim(), System.currentTimeMillis(), new User(0, "Riku Wikman", "das.jpg")));
                mMessageAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getUserMessages(long id) {
        String url = "http://192.168.1.101:8000/chatlog?user_id=304556471322083329&receiver_id=" + id;
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<HalooMessage> list = HalooMessage.deserializeJson(response);
                    messageList.addAll(list);
                    mMessageAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("jsonerror", "Error: " + error.getMessage());
            }
        });

        queue = ConversationQueueSingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        queue.add(request);
    }

    private void getRoomMessages(long id) {
        String url = "http://192.168.1.101:8000/chatlog?room_id=" + id;
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<HalooMessage> list = HalooMessage.deserializeJson(response);
                    messageList.addAll(list);
                    mMessageAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("jsonerror", "Error: " + error.getMessage());
            }
        });

        queue = ConversationQueueSingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        queue.add(request);
    }
}
