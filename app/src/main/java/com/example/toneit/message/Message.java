package com.example.toneit.message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;


import com.example.toneit.R;
import com.example.toneit.TabLayoutAdapter;
import com.example.toneit.message.Chats;
import com.example.toneit.message.MsgUsers;
import com.google.android.material.tabs.TabLayout;


public class Message extends AppCompatActivity {

    ViewPager pagerId;
    TabLayout tabId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        findView();

        setAdapterLayout();

        tabId.setupWithViewPager(pagerId);

    }

    public void findView() {
        pagerId = findViewById(R.id.pagerId);
        tabId = findViewById(R.id.tabId);
    }




    public void setAdapterLayout() {
        TabLayoutAdapter adapter = new TabLayoutAdapter(getSupportFragmentManager());
//        adapter.addFrame(new Chats(), "Chats");
        adapter.addFrame(new MsgUsers(), "Chats");
        pagerId.setAdapter(adapter);

    }


}
