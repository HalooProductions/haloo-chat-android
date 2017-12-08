package com.haloo.chat.haloochat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView conversationsListView;
    ArrayList<ConversationInfo> conversationList;
    private static ConversationsArrayAdapter conversationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       conversationsListView = findViewById(R.id.conversationsListView);
       conversationList = new ArrayList<>();
       conversationList.add(new ConversationInfo(1, "asd.jpg", "Riku Wikman", "Moro mitä kuluu"));
       conversationList.add(new ConversationInfo(2, "asd.jpg", "Testi Boi", "Jeb"));
       conversationList.add(new ConversationInfo(3, "asd.jpg", "Arttu Lindeman", "Jaaa"));

       conversationsAdapter = new ConversationsArrayAdapter(conversationList, getApplicationContext());

       conversationsListView.setAdapter(conversationsAdapter);

       conversationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               ConversationInfo conversationInfo = conversationsAdapter.getItem(i);
               openMessageActivity();
           }
       });
    }

    private void openMessageActivity() {
        Intent intent = new Intent(this, MessageListActivity.class);
        startActivityForResult(intent, 100);
    }
}
