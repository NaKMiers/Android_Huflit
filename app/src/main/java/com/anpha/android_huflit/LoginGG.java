package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginGG extends AppCompatActivity {
        Button btnsignupgg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_gg);
        Logingg();
        btnsignupgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginwithgg= new Intent(LoginGG.this, RegisterGG.class);
                startActivity(loginwithgg);
            }
        });

    }
    public void Logingg()
    {
        btnsignupgg=findViewById(R.id.btnsignupgg);
    }
}