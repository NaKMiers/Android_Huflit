package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileView extends AppCompatActivity {
    // Elements
    ImageView avatar, homeBtn, profileBtn, themeBtn, securityBtn, logoutBtn;
    TextView editBtn;
    TextView fullnameTv, emailTv, usernameTv, birthdayTv, jobTv, addressTv;

    // values
    String token, userId;
    String API = "https://android-huflit-server.vercel.app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        requireAuth();

        // mapping
        mapping();

        // events
        addEvents();


        // Set user information
        SharedPreferences preferences = getSharedPreferences("myPreferences", MODE_PRIVATE);
        String username = preferences.getString("username", ""); //lưu trữ tên người dùng
        userId = preferences.getString("userId", ""); //lưu trữ tên người dùng
        token = preferences.getString("token", "");
        String email =preferences.getString("email", ""); //lưu trữ tên người dùng
        String role =preferences.getString("role","");
        String authType =preferences.getString("authType","");
        String avt = preferences.getString("avatar", "");
        String firstname = preferences.getString("firstname", "");
        String lastname = preferences.getString("lastname", "");
        String birthday = preferences.getString("birthday", "");
        String job = preferences.getString("job", "");
        String address = preferences.getString("address", "");

        Picasso.get()
                .load(API + avt)
                .error(R.drawable.boy)
                .into(avatar);

        fullnameTv.setText(firstname + " " + lastname);
        emailTv.setText(email);
        usernameTv.setText(username);
        birthdayTv.setText(birthday);
        jobTv.setText(job);
        addressTv.setText(address);
    }

    private void mapping() {
        homeBtn = findViewById(R.id.homeBtn);
        profileBtn = findViewById(R.id.profileBtn);
        themeBtn = findViewById(R.id.themeBtn);
        securityBtn = findViewById(R.id.securityBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        avatar = findViewById(R.id.avatar);
        editBtn = findViewById(R.id.editBtn);
        fullnameTv = findViewById(R.id.fullnameTv);
        emailTv = findViewById(R.id.emailTv);
        usernameTv = findViewById(R.id.usernameTv);
        birthdayTv = findViewById(R.id.birthdayTv);
        jobTv = findViewById(R.id.jobTv);
        addressTv = findViewById(R.id.addressTv);
    }

    private void addEvents() {
        homeBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, TextChat.class);
            startActivity(intent);
        });
        profileBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProfileView.class);
            startActivity(intent);
        });
        themeBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, ThemeView.class);
            startActivity(intent);
        });
        securityBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Security.class);
            startActivity(intent);
        });
        logoutBtn.setOnClickListener(view -> {
            SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(ProfileView.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void requireAuth() {
        SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        String userId = preferences.getString("userId", ""); //lưu trữ tên người dùng
        if (userId == null || userId == "") {
            Intent intent = new Intent(ProfileView.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mnu = getMenuInflater();
        mnu.inflate(R.menu.profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void toProfileChange(View view) {
        Intent intent = new Intent(ProfileView.this, ProfileChange.class);
        startActivity(intent);
    }


    //Hàm đổi avatar khi nhấn vào các lựa chọn hình
    public void onNewAvatarSelected(View view) {
//        //Lấy ImageView được chọn và gán vào biến selectedAvatarImageView
//        selectedAvatarImageView = (ImageView) view;
//        //Lấy hình ảnh hiển thị trên selectedAvatarImageView và lưu vào biến drawable
//        Drawable drawable = selectedAvatarImageView.getDrawable();
//        //Thiết lập hình ảnh của mainAvatar bằng hình ảnh được chọn
//        avatar.setImageDrawable(drawable);
//        //Nếu popupWindow đã khởi tạo
//        if (popupWindow != null) {
//            //Đóng popupWindow
//            popupWindow.dismiss();
//            //Đặt màu nền cho layoutProfile là trong suốt
//            profileLayout.setBackgroundColor(Color.TRANSPARENT);
//            //Chỉnh lại alpha cho informationView
//            informationView.setAlpha(1.0f);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Thay đổi thông tin theo các thông tin đã đổi ở profileChange
//        if (requestCode == 1 && resultCode == 2) {
//            String id = data.getStringExtra("id");
//            String email = data.getStringExtra("email");
//            String birthday = data.getStringExtra("birthday");
//            String job = data.getStringExtra("job");
//            String address = data.getStringExtra("address");
//
//        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Lấy lại dữ liệu từ SharedPreferences và hiển thị thông tin
//        displayProfileInfo();
    }
}
