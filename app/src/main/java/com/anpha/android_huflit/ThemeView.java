package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.anpha.android_huflit.Models.Theme;

import java.util.ArrayList;

public class ThemeView extends AppCompatActivity {
    ImageView avatar, homeBtn, profileBtn, themeBtn, securityBtn, logoutBtn;
    GridView themeGrid;
    ArrayList<Theme> themes;
    ThemeAdapter themeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        int themeId = preferences.getInt("themeId", R.style.Theme1);

        setTheme(themeId);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_view);

        // mapping
        mapping();

        // events
        addEvents();

        themeGrid = findViewById(R.id.themeGrid);
        themes = new ArrayList<>();

        Theme theme1 = new Theme(R.style.Theme1, "Material", "#333333", "#FFFFFF");
        Theme theme2 = new Theme(R.style.Theme2, "Dark Material", "#FFFFFF", "#121212");
        Theme theme3 = new Theme(R.style.Theme3, "Twilight", "#FFFFFF", "#F5F5F5");
        Theme theme4 = new Theme(R.style.Theme4, "Deep Ocean", "#000000", "#E0E0E0");
        Theme theme5 = new Theme(R.style.Theme5, "Sunset", "#FFFFFF", "#FFEB3B");
        Theme theme6 = new Theme(R.style.Theme6, "Nature", "#212121", "#FFFFFF");
        Theme theme7 = new Theme(R.style.Theme7, "Fire", "#FFFFFF", "#212121");
        Theme theme8 = new Theme(R.style.Theme8, "Earth", "#212121", "#F5F5F5");
        Theme theme9 = new Theme(R.style.Theme9, "Coral", "#FFFFFF", "#FBE9E7");
        Theme theme10 = new Theme(R.style.Theme10, "Sky", "#FFFFFF", "#E3F2FD");

        themes.add(theme1);
        themes.add(theme2);
        themes.add(theme3);
        themes.add(theme4);
        themes.add(theme5);
        themes.add(theme6);
        themes.add(theme7);
        themes.add(theme8);
        themes.add(theme9);
        themes.add(theme10);

        themeAdapter = new ThemeAdapter(ThemeView.this, R.layout.item_theme, themes);
        themeGrid.setAdapter(themeAdapter);

        themeGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Theme theme = themes.get(position);
                Toast.makeText(ThemeView.this, "Đã chọn theme: " + theme.getTitle(), Toast.LENGTH_SHORT).show();

                SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("themeId", theme.getThemeId());
                editor.apply();

                recreate();
            }
        });
    }

    private void mapping() {
        homeBtn = findViewById(R.id.homeBtn);
        profileBtn = findViewById(R.id.profileBtn);
        themeBtn = findViewById(R.id.themeBtn);
        securityBtn = findViewById(R.id.securityBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        avatar = findViewById(R.id.avatar);
    }
    
    private void addEvents() {
        homeBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, TextChat.class);
            startActivity(intent);
        });
        profileBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProfileView.class);
            startActivity(intent);
        });
        themeBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, ThemeView.class);
            startActivity(intent);
        });
        securityBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Security.class);
            startActivity(intent);
        });
        logoutBtn.setOnClickListener(view -> {
            SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}