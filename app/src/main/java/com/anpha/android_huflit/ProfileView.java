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
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
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
    TextView txtnameuser, txtEmailProfile;
    SharedPreferences preferences;

    CircleImageView avatar;
    TextView txtnameuser, txtEmailProfile;

    ImageView selectedAvatarImageView;
    ConstraintLayout profileLayout;
    PopupWindow popupWindow;

    LinearLayout informationView;

    TextView txtHo, txtTen, txtngaysinh, txtdiachi, txtnghe;


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

        avatar = findViewById(R.id.avatar);
        profileLayout = findViewById(R.id.profileLayout);
        informationView = findViewById(R.id.informationView);

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
        if (requestCode == 1 && resultCode == 2) {
            String surname = data.getStringExtra("surName");
            String name = data.getStringExtra("name");
            String birthday = data.getStringExtra("birthday");
            String job = data.getStringExtra("job");
            String address = data.getStringExtra("address");

            txtHo.setText("HỌ: " + surname);
            txtTen.setText("TÊN: " + name);
            txtngaysinh.setText("NGÀY SINH: " + birthday);
            txtnghe.setText("NGHỀ NGHIỆP: " + job);
            txtdiachi.setText("ĐỊA CHỈ: " + address);
        }
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
