package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import java.nio.BufferUnderflowException;

public class PopupOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        int themeId = preferences.getInt("themeId", R.style.Theme1);
        setTheme(themeId);

        setContentView(R.layout.activity_popup_option);
        Bundle bundle=getIntent().getExtras();
    }
    private void requireAuth() {
        SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        String userId = preferences.getString("userId", ""); //lưu trữ tên người dùng
        if (userId == null || userId == "") {
            Intent intent = new Intent(PopupOption.this, Login.class);
            startActivity(intent);
            finish();
            return;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mnu=getMenuInflater();
        mnu.inflate(R.menu.profile_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}