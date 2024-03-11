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


import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileView extends AppCompatActivity {

    CircleImageView avatar;
    TextView txtUsername, txtID, txtName, txtEmail, txtRole, txtAuthType;
    ImageView selectedAvatarImageView;
    ConstraintLayout profileLayout;
    PopupWindow popupWindow;

    LinearLayout informationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        // mapping
        txtUsername = findViewById(R.id.txtUsername);
        txtID = findViewById(R.id.txtID);
        txtName = findViewById(R.id.txtSetting);
        txtEmail = findViewById(R.id.txtEmail);
        txtRole = findViewById(R.id.txtRole);
        txtAuthType = findViewById(R.id.txtAuthType);

        // Set user information
        SharedPreferences preferences = getSharedPreferences("mypreferences", MODE_PRIVATE);
        String username = preferences.getString("username", ""); //lưu trữ tên người dùng
        String id = preferences.getString("userId", ""); //lưu trữ tên người dùng
        String email =preferences.getString("email", ""); //lưu trữ tên người dùng
        String role =preferences.getString("role","");
        String authType =preferences.getString("authType","");


        txtID.setText(" Id: " +  id);
        txtEmail.setText(" Email: " + email);
        txtName.setText(" Username: " + username);
        txtUsername.setText(username);
        txtRole.setText(" Role: " +role);
        txtAuthType.setText(" AuthType: " + authType);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mnu = getMenuInflater();
        mnu.inflate(R.menu.profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void toProfileChange(View view) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) avatar.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        //Tạo một byte array output stream để ghi bitmap vào
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        // Chuyển đổi ByteArrayOutputStream thành mảng byte
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        Intent i = new Intent(ProfileView.this, ProfileChange.class);
        i.putExtra("currentAvatar", byteArray);
        startActivityForResult(i, 1);
    }
    private void requireAuth() {
        SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        String userId = preferences.getString("userId", ""); //lưu trữ tên người dùng
        if (userId == null || userId == "") {
            Intent intent = new Intent(ProfileView.this, Login.class);
            startActivity(intent);
            finish();
            return;
        }
    }

    public void changeAvatar(View view) {
        showAvatarSelectionPopup();
    }

    private void showAvatarSelectionPopup() {
        //Khởi tạo popupView nạp layout từ avatar_selection_popup
        View popupView = LayoutInflater.from(this).inflate(R.layout.avatar_selection_popup, null);
        //Lấy imageView id là currentAvatar
        ImageView currentAvatar = popupView.findViewById(R.id.currentAvatar);
        //Thiết lập hình ảnh từ mainAvatar cho currentAvatar
        currentAvatar.setImageDrawable(avatar.getDrawable());
        //Khởi tạo popupWindow
        popupWindow = new PopupWindow(
                popupView,
                //Độ rộng
                LinearLayout.LayoutParams.MATCH_PARENT,
                //Độ cao
                LinearLayout.LayoutParams.MATCH_PARENT,
                //Cho phép tương tác với các phần tử khác trên màn hình
                true
        );
        //Khởi tạo popupWindow tại vị trí trung tâm màn hình
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        //Set độ mờ cho informationView
        informationView.setAlpha(0.4f);
        //Tạo biến màu mờ
        int dimColor = Color.parseColor("#80000000");
        //Sau đó set cho layoutFile
        profileLayout.setBackgroundColor(dimColor);
    }
       

    //Hàm đổi avatar khi nhấn vào các lựa chọn hình
    public void onNewAvatarSelected(View view) {
        //Lấy ImageView được chọn và gán vào biến selectedAvatarImageView
        selectedAvatarImageView = (ImageView) view;
        //Lấy hình ảnh hiển thị trên selectedAvatarImageView và lưu vào biến drawable
        Drawable drawable = selectedAvatarImageView.getDrawable();
        //Thiết lập hình ảnh của mainAvatar bằng hình ảnh được chọn
        avatar.setImageDrawable(drawable);
        //Nếu popupWindow đã khởi tạo
        if (popupWindow != null) {
            //Đóng popupWindow
            popupWindow.dismiss();
            //Đặt màu nền cho layoutProfile là trong suốt
            profileLayout.setBackgroundColor(Color.TRANSPARENT);
            //Chỉnh lại alpha cho informationView
            informationView.setAlpha(1.0f);
        }
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

    public void handleChangeProfileChange(View view) {
        Intent intent = new Intent(ProfileView.this, ProfileChange.class);
        startActivity(intent);
    }
}