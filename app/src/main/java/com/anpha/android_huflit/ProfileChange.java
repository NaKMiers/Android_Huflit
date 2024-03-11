package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileChange extends AppCompatActivity {
    ImageView avatarProfileChange;
    EditText edtSurname, edtName, edtBirthday, edtJob, edtAddress;
     TextView txtusernamechange,txtemailprofilechange;
    Button btnSaveInfo;

    Intent i = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_change);

        edtSurname = findViewById(R.id.edtSurName);
        edtName = findViewById(R.id.edtName);
        edtBirthday = findViewById(R.id.edtBirthday);
        edtJob = findViewById(R.id.edtJob);
        edtAddress = findViewById(R.id.edtAddress);
        btnSaveInfo = findViewById(R.id.btnSaveInfo);
        avatarProfileChange = findViewById(R.id.avatar);
        txtemailprofilechange=findViewById(R.id.txtemailprofilechange);
        txtusernamechange=findViewById(R.id.txtusernamechange);
        i = getIntent();
        SharedPreferences preferences = getSharedPreferences("mypreferences", MODE_PRIVATE);
        String username = preferences.getString("username", "");
        // Hiển thị tên người dùng trong TextView
        txtusernamechange.setText(username);
        String email = preferences.getString("email", "");
        // Hiển thị email trong TextView
        txtemailprofilechange.setText("EMAIL:"+email);


        // Nhận mảng byte chứa hình ảnh từ Intent
        byte[] byteArray = getIntent().getByteArrayExtra("currentAvatar");
        if (byteArray != null) {
            // Chuyển đổi mảng byte thành Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            // Set avatar bên profileChange thành hình ảnh Bitmap
            avatarProfileChange.setImageBitmap(bitmap);



        }
    }

    public void saveInfo(View view) {
        // Lấy SharedPreferences và tạo SharedPreferences.Editor
        SharedPreferences preferences = getSharedPreferences("mypreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Lấy các giá trị từ EditText
        String surName = edtSurname.getText().toString();
        String name = edtName.getText().toString();
        String birthday = edtBirthday.getText().toString();
        String job = edtJob.getText().toString();
        String address = edtAddress.getText().toString();

        // Lưu các giá trị mới vào SharedPreferences
        editor.putString("ho", surName);
        editor.putString("ten", name);
        editor.putString("ngaysinh", birthday);
        editor.putString("nghe", job);
        editor.putString("diachi", address);

        // Lưu các thay đổi
        editor.apply();

        // Trả kết quả về ProfileView
        setResult(2, i);
        finish();
    }

}