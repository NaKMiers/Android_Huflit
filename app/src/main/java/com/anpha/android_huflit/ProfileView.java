package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

public class ProfileView extends AppCompatActivity {
    TextView txtnameuser, txtEmailProfile,txtHo,txtTen,txtngaysinh,txtdiachi,txtnghe;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        txtnameuser = findViewById(R.id.txtnameuser);
        txtEmailProfile = findViewById(R.id.txtEmailProfile);
        preferences = getSharedPreferences("mypreferences", MODE_PRIVATE);
        String username = preferences.getString("username", "");
        String email = preferences.getString("email", "");
        txtnameuser.setText(username);
        txtEmailProfile.setText("Email: " + email);
        // Lấy các thông tin từ SharedPreferences
        String ho = preferences.getString("ho", "");
        String ten = preferences.getString("ten", "");
        String ngaysinh = preferences.getString("ngaysinh", "");
        String diachi = preferences.getString("diachi", "");
        String nghe = preferences.getString("nghe", "");
        txtHo = findViewById(R.id.txtHo);
        txtTen = findViewById(R.id.txtTen);
        txtngaysinh = findViewById(R.id.txtngaysinh);
        txtdiachi = findViewById(R.id.txtdiachi);
        txtnghe = findViewById(R.id.txtnghe);
        txtHo.setText("Họ: " + ho);
        txtTen.setText("Tên: " + ten);
        txtngaysinh.setText("Ngày sinh: " + ngaysinh);
        txtdiachi.setText("Địa chỉ: " + diachi);
        txtnghe.setText("Nghề nghiệp: " + nghe);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mnu = getMenuInflater();
        mnu.inflate(R.menu.profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void handleChangeProfileChange(View view) {
        Intent intent = new Intent(ProfileView.this, ProfileChange.class);
        startActivity(intent);
    }

    // Method to handle profile view button click
    public void handleProfileView(View view) {
        SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        String email = preferences.getString("email", "");

        // Lấy thông tin từ SharedPreferences
        String ho = preferences.getString("ho", "");
        String ten = preferences.getString("ten", "");
        String ngaysinh = preferences.getString("ngaysinh", "");
        String diachi = preferences.getString("diachi", "");
        String nghe = preferences.getString("nghe", "");

        // Hiển thị thông tin trong TextViews
        txtHo.setText("Họ: " + ho);
        txtTen.setText("Tên: " + ten);
        txtngaysinh.setText("Ngày sinh: " + ngaysinh);
        txtdiachi.setText("Địa chỉ: " + diachi);
        txtnghe.setText("Nghề nghiệp: " + nghe);

        // Hiển thị email trong TextView
        txtEmailProfile.setText("Email: " + email);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Lấy lại dữ liệu từ SharedPreferences và hiển thị thông tin
        displayProfileInfo();
    }

    private void displayProfileInfo() {
        // Lấy thông tin từ SharedPreferences
        String ho = preferences.getString("ho", "");
        String ten = preferences.getString("ten", "");
        String ngaysinh = preferences.getString("ngaysinh", "");
        String diachi = preferences.getString("diachi", "");
        String nghe = preferences.getString("nghe", "");

        // Hiển thị thông tin trong TextViews
        txtHo.setText("Họ: " + ho);
        txtTen.setText("Tên: " + ten);
        txtngaysinh.setText("Ngày sinh: " + ngaysinh);
        txtdiachi.setText("Địa chỉ: " + diachi);
        txtnghe.setText("Nghề nghiệp: " + nghe);

        // Hiển thị email trong TextView
        String email = preferences.getString("email", "");
        txtEmailProfile.setText("Email: " + email);
    }


}
