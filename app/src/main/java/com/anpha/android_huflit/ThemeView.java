package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;


import com.anpha.android_huflit.Models.Theme;

import java.util.ArrayList;

public class ThemeView extends AppCompatActivity {
  ArrayList<Theme> themes;
  ThemesAdapter themeAdapter;
  GridView themeListView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_theme_view);

    setTheme(R.style.AppTheme);

    themeListView = findViewById(R.id.themeLV);
    themes = new ArrayList<>();

    Theme theme1 = new Theme(1,"#333", "#fff", "White 1");
    Theme theme2 = new Theme(2, "#333", "#fff", "White 2");
    Theme theme3 = new Theme(3,"#333", "#fff", "White 3");
    Theme theme4 = new Theme(4, "#333", "#fff", "White 4");
    Theme theme5 = new Theme(5,"#333", "#fff", "White 5");
    Theme theme6 = new Theme(6,"#333", "#fff", "White 6");
    Theme theme7 = new Theme(7,"#333", "#fff", "White 7");
    Theme theme8 = new Theme(8,"#333", "#fff", "White 8");
    Theme theme9 = new Theme(9,"#333", "#fff", "White 9");

    themes.add(theme1);
    themes.add(theme2);
    themes.add(theme3);
    themes.add(theme4);
    themes.add(theme5);
    themes.add(theme6);
    themes.add(theme7);
    themes.add(theme8);
    themes.add(theme9);

    themeAdapter = new ThemesAdapter(ThemeView.this, R.layout.item_theme, themes);
    themeListView.setAdapter(themeAdapter);

    // get global data
    SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
    String theme = preferences.getString("theme", "");

    themeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // get theme which is clicked
        Theme theme = themes.get(position);

        // change theme
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.);

        // change theme globaly
        SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("theme", theme.getThemeId());
        editor.apply();
      }
    });
  }

}