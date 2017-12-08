package com.haloo.chat.haloochat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageListActivity extends AppCompatActivity {

    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private List<HalooMessage> messageList;
    private Button sendMessageBtn;
    private EditText messageInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        sendMessageBtn = findViewById(R.id.button_chatbox_send);
        messageInput = findViewById(R.id.edittext_chatbox);

        messageList = new ArrayList<>();

        messageList.add(new HalooMessage("Testi viesti!", 1512751763652L, new User(1, "Arttu Lindeman", "asd.jpg")));
        messageList.add(new HalooMessage("Uus levy tulossa :)", 1512751896642L, new User(1, "Arttu Lindeman", "asd.jpg")));
        messageList.add(new HalooMessage("Jos kiinnosti?", 1512751914700L, new User(1, "Arttu Lindeman", "asd.jpg")));
        messageList.add(new HalooMessage("No ei kiinnostanu", 1512751941126L, new User(0, "Riku Wikman", "das.jpg")));

        mMessageRecycler = findViewById(R.id.recyclerview_message_list);
        mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageList.add(new HalooMessage(messageInput.getText().toString(), System.currentTimeMillis(), new User(0, "Riku Wikman", "das.jpg")));
                mMessageAdapter.notifyDataSetChanged();
            }
        });
    }
}
