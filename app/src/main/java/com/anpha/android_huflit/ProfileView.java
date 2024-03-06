package com.anpha.android_huflit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

public class ProfileView extends AppCompatActivity {
    TextView txtnameuser,txtemailprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        txtnameuser = findViewById(R.id.txtnameuser);
        txtemailprofile= findViewById(R.id.txtemailprofile);
        SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        String username = preferences.getString("username", ""); // Retrieve username
       txtnameuser.setText(username);


       SharedPreferences preferences1 = getSharedPreferences("mypreferences1",Context.MODE_PRIVATE);
       String email = preferences1.getString("email","");
       txtmailprofile.setText("EMAIL: " +email);

      






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mnu=getMenuInflater();
        mnu.inflate(R.menu.profile_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}