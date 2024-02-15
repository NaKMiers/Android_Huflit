package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Login extends AppCompatActivity {
     Button btnsignin,btnlogin;
     EditText edtemaillogin,edtpasswordlogin;
     TextView txtforgetpassword,txtloginwithgg;
     ImageView imgGG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControlLogin();
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin = new Intent(Login.this, Register.class);
                startActivity(signin);

            }
        });
        txtforgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotpassword = new Intent(Login.this,ForgotPassword.class);
                startActivity(forgotpassword);
            }
        });
        txtloginwithgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginwithgg = new Intent(Login.this, LoginGG.class);
                startActivity(loginwithgg);
            }
        });
        imgGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgGG = new Intent(Login.this, LoginGG.class);
                startActivity(imgGG);
            }
        });
    }
    public void addControlLogin()
    {
        btnlogin =findViewById(R.id.btnlogin);
        btnsignin=findViewById(R.id.btnsignup);
        edtemaillogin=findViewById(R.id.edtemaillogin);
        edtpasswordlogin=findViewById(R.id.edtpasswordlogin);
        txtforgetpassword = findViewById(R.id.txtforgetpassword);
        txtloginwithgg=findViewById(R.id.txtloginwithgg);
        imgGG=findViewById(R.id.imgGG);
    }
}