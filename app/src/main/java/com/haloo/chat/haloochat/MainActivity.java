package com.haloo.chat.haloochat;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

public class MainActivity extends AppCompatActivity {

    private static ConversationsArrayAdapter conversationsAdapter;
    ArrayList<ConversationInfo> conversationList;
    ListView conversationsListView;
    FloatingActionButton fab;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.new_message_btn);

        conversationsListView = findViewById(R.id.conversationsListView);
        conversationList = new ArrayList<>();

        conversationsAdapter = new ConversationsArrayAdapter(conversationList, getApplicationContext());
        conversationsListView.setAdapter(conversationsAdapter);

        getConversationList();

        conversationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ConversationInfo conversationInfo = conversationsAdapter.getItem(i);

                openMessageActivity(conversationInfo);
            }
        });
    }

    private void getConversationList() {
        String url = "http://192.168.1.101:8000/conversations?user_id=304556471322083329";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.has("rooms")) {
                        ArrayList<ConversationInfo> roomConversations = ConversationInfo.deserializeJson(response.getJSONArray("rooms"));
                        conversationList.addAll(roomConversations);
                        conversationsAdapter.notifyDataSetChanged();
                    }

                    if (response.has("conversations")) {
                        ArrayList<ConversationInfo> userConversations = ConversationInfo.deserializeJson(response.getJSONArray("conversations"));
                        conversationList.addAll(userConversations);
                        conversationsAdapter.notifyDataSetChanged();
                    }
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

    private void openMessageActivity(ConversationInfo info) {
        Intent intent = new Intent(this, MessageListActivity.class);
        intent.putExtra("info", info);
        startActivityForResult(intent, 100);
    }
}
