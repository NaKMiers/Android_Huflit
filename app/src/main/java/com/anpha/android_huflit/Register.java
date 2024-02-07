package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {
    EditText edtemailregister,edtphoneregister,edtnameregister,edtpasswordregister,edtpassagainregister;
    Button btnregister;
    TextView txtsigninregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControlRegister();
        txtsigninregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(Register.this, Login.class);
                startActivity(register);
            }
        });
    }
    public void addControlRegister()
    {
        edtemailregister=findViewById(R.id.edtemailregister);
        edtnameregister=findViewById(R.id.edtemailregister);
        edtpasswordregister=findViewById(R.id.edtpasswordregister);
        edtpassagainregister=findViewById(R.id.edtpasswordregister);
        edtphoneregister=findViewById(R.id.edtphoneregister);
        btnregister=findViewById(R.id.btnregister);
        txtsigninregister=findViewById(R.id.txtsigninregister);
    }
}