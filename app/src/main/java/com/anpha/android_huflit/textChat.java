package com.anpha.android_huflit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class textChat extends AppCompatActivity {

    Toolbar toolbarChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_chat);

        toolbarChat = findViewById(R.id.toolbarChat);
        toolbarChat.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.iconMenu){
                    Intent i = new Intent(textChat.this,chatMenu.class);
                    startActivity(i);
                }
                return false;
            }
        });

    }
}