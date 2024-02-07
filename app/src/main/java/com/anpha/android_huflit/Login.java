package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {
     Button btnsignin,btnlogin;
     EditText edtemaillogin,edtpasswordlogin;
     TextView txtforgetpassword;
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
    }
    public void addControlLogin()
    {
        btnlogin =findViewById(R.id.btnlogin);
        btnsignin=findViewById(R.id.btnsignup);
        edtemaillogin=findViewById(R.id.edtemaillogin);
        edtpasswordlogin=findViewById(R.id.edtpasswordlogin);
        txtforgetpassword = findViewById(R.id.txtforgetpassword);
    }
}