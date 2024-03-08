package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

public class ProfileView extends AppCompatActivity {
    TextView txtnameuser,txtEmailProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        txtnameuser = findViewById(R.id.txtnameuser);
        txtEmailProfile= findViewById(R.id.txtEmailProfile);
        SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        String username = preferences.getString("username", ""); // Retrieve username
       txtnameuser.setText(username);

//       SharedPreferences preferences = getSharedPreferences("mypreferences",Context.MODE_PRIVATE);
//       String email = preferences.getString("email","");
//        txtEmailProfile.setText("EMAIL: " +email);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mnu=getMenuInflater();
        mnu.inflate(R.menu.profile_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}