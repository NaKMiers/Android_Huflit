package com.anpha.android_huflit;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.LinearLayout;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.PopupWindow;
import android.view.View;
import android.view.LayoutInflater;

public class TextChat extends AppCompatActivity {

    PopupWindow popupWindow;

    Toolbar toolbarChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_chat);

        toolbarChat = findViewById(R.id.toolbarChat);

        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_chat_menu, null);
        popupWindow = new PopupWindow(
                popupView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1600,
                true
        );

        toolbarChat.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.iconMenu)
                {
                    popupWindow.showAsDropDown(toolbarChat.findViewById(R.id.iconMenu));
                    return true;
                }
                return false;
            }
        });
    }
}