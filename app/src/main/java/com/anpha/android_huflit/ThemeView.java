package com.anpha.android_huflit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.anpha.android_huflit.Models.theme;
import com.anpha.android_huflit.adapter_theme.ThemeAdapter;

import java.util.ArrayList;

public class ThemeView extends AppCompatActivity {
Button btnoption;
RecyclerView recyclerViewTheme;
ThemeAdapter themeAdapter;
ArrayList<theme> arr_theme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_view);
        addControls();
    }

    //thêm ảnh vào listview trong theme
    private void loadData() {
        arr_theme.add(new theme(R.drawable.cam,"DF8634"));
        arr_theme.add(new theme(R.drawable.xanhdam,"3877B1"));
        arr_theme.add(new theme(R.drawable.den,"000000"));
        arr_theme.add(new theme(R.drawable.maudo,"F42D2D"));
        arr_theme.add(new theme(R.drawable.xam,"D9D9D9"));
        arr_theme.add(new theme(R.drawable.xanhla,"65DC2D"));
        arr_theme.add(new theme(R.drawable.nau,"3E2323"));
        arr_theme.add(new theme(R.drawable.tim,"BD20CB"));
        arr_theme.add(new theme(R.drawable.xanhnhat,"27BEC7"));
    }



//    private void addControls() {
//        recyclerViewTheme=findViewById(R.id.recyclerViewTheme);
//        arr_theme=new ArrayList<>();
//        themeAdapter=new ThemeAdapter(this,arr_theme);
//        recyclerViewTheme.setAdapter(themeAdapter);
//        btnoption=findViewById(R.id.btnoption);
//        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
//        recyclerViewTheme.setLayoutManager(gridLayoutManager);
//    }
private void addControls() {
    recyclerViewTheme = findViewById(R.id.recyclerViewTheme);
    arr_theme = new ArrayList<>();
    loadData(); // Add this line to load data
    themeAdapter = new ThemeAdapter(this, arr_theme);
    recyclerViewTheme.setAdapter(themeAdapter);
    btnoption = findViewById(R.id.btnoption);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
    recyclerViewTheme.setLayoutManager(gridLayoutManager);
}



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mnu=getMenuInflater();
        mnu.inflate(R.menu.profile_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void clickOptionTheme(View view) {
        // Check if RecyclerView is hidden (Visibility.GONE)
        if (recyclerViewTheme.getVisibility() == View.GONE) {
            themeAdapter.notifyDataSetChanged(); // Update data if needed
            recyclerViewTheme.setVisibility(View.VISIBLE); // Show RecyclerView
        } else {
            recyclerViewTheme.setVisibility(View.GONE); // Hide RecyclerView
        }
    }
}