package com.haloo.chat.haloochat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView conversationsListView;
    ArrayList<HashMap<String, String>> conversationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       conversationsListView = findViewById(R.id.conversationsListView);
       conversationList = new ArrayList<>();
    }
}
