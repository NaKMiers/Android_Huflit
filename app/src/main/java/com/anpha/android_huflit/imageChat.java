package com.anpha.android_huflit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

public class imageChat extends AppCompatActivity {

    Toolbar toolbarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_chat);

        toolbarImage = findViewById(R.id.toolbarImage);

        toolbarImage.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.iconMenu){
                    Intent intent = new Intent(imageChat.this, imageChatMenu.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }
}
