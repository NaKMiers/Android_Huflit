package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ResetPassword extends AppCompatActivity {
EditText edtnewpassword,edtpassagainreset;
Button btnokreset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        int themeId = preferences.getInt("themeId", R.style.Theme1);
        setTheme(themeId);

        setContentView(R.layout.activity_reset_password);
        addControls();

    }

    private void addControls() {
        edtnewpassword = findViewById(R.id.edtnewpassword);
        edtpassagainreset = findViewById(R.id.edtpassagainreset);
        btnokreset = findViewById(R.id.btnokreset);
    }

    public void ResetPass(View view) {
        String NewPassWord = edtnewpassword.getText().toString();
        String PassWordAgain = edtpassagainreset.getText().toString();

    }
}
