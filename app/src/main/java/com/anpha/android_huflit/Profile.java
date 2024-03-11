package com.anpha.android_huflit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
//
//public class Profile extends AppCompatActivity {
//
//    View informationView;
//    ConstraintLayout layoutProfile;
//    ImageView selectedAvatarImageView, mainAvatar;
//    TextView txtChange, txtSurName, txtName, txtBirthday, txtJob, txtAddress;
//
//    PopupWindow popupWindow;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);
//
//        mainAvatar = findViewById(R.id.mainAvatar);
//        txtChange = findViewById(R.id.txtChange);
//        txtSurName = findViewById(R.id.txtSurName);
//        txtName = findViewById(R.id.txtName);
//        txtBirthday = findViewById(R.id.txtBirthday);
//        txtJob = findViewById(R.id.txtJob);
//        txtAddress = findViewById(R.id.txtAddress);
//        layoutProfile = findViewById(R.id.layoutProfile);
//        informationView = findViewById(R.id.informationView);
//
//    }
//
//    //Hàm khởi tạo popupWindow để chọn avatar
//    private void showAvatarSelectionPopup() {
//        //Khởi tạo popupView nạp layout từ avatar_selection_popup
//        View popupView = LayoutInflater.from(this).inflate(R.layout.avatar_selection_popup,null);
//        //Lấy imageView id là currentAvatar
//        ImageView currentAvatar = popupView.findViewById(R.id.currentAvatar);
//        //Thiết lập hình ảnh từ mainAvatar cho currentAvatar
//        currentAvatar.setImageDrawable(mainAvatar.getDrawable());
//        //Khởi tạo popupWindow
//        popupWindow = new PopupWindow(
//                popupView,
//                //Độ rộng
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                //Độ cao
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                //Cho phép tương tác với các phần tử khác trên màn hình
//                true
//        );
//        //Khởi tạo popupWindow tại vị trí trung tâm màn hình
//        popupWindow.showAtLocation(popupView, Gravity.CENTER,0,0);
//        //Chỉnh độ mờ
//        informationView.setAlpha(0.4f);
//        //Tạo biến màu mờ
//        int dimColor = Color.parseColor("#80000000");
//        //Sau đó set cho layoutFile
//        layoutProfile.setBackgroundColor(dimColor);
//    }
//    //Hàm đổi avatar khi nhấn vào các lựa chọn hình
//    public void onNewAvatarSelected(View view) {
//        //Lấy ImageView được chọn và gán vào biến selectedAvatarImageView
//        selectedAvatarImageView = (ImageView) view;
//        //Lấy hình ảnh hiển thị trên selectedAvatarImageView và lưu vào biến drawable
//        Drawable drawable = selectedAvatarImageView.getDrawable();
//        //Thiết lập hình ảnh của mainAvatar bằng hình ảnh được chọn
//        mainAvatar.setImageDrawable(drawable);
//        //Nếu popupWindow đã khởi tạo
//        if (popupWindow != null) {
//            //Đóng popupWindow
//            popupWindow.dismiss();
//            //Đặt màu nền cho layoutProfile là trong suốt
//            layoutProfile.setBackgroundColor(Color.TRANSPARENT);
//            //Đặt độ trong suốt về giá trị mặc định 1.0
//            informationView.setAlpha(1.0f);
//        }
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //Thay đổi thông tin theo các thông tin đã đổi ở profileChange
//        if(requestCode == 1 && resultCode == 2){
//            String surname = data.getStringExtra("surName");
//            String name = data.getStringExtra("name");
//            String birthday = data.getStringExtra("birthday");
//            String job = data.getStringExtra("job");
//            String address = data.getStringExtra("address");
//
//            txtSurName.setText("HỌ: " + surname);
//            txtName.setText("TÊN: " + name);
//            txtBirthday.setText("NGÀY SINH: " + birthday);
//            txtJob.setText("NGHỀ NGHIỆP: " + job);
//            txtAddress.setText("ĐỊA CHỈ: " + address);
//        }
//    }
//    //Sự kiện khi nhấn vào avatar
//    public void changeAvatar(View view) {
//        showAvatarSelectionPopup();
//    }
//
//    //Sự kiện khi nhấn vào TextView (thay đổi)
//    public void infoChange(View view) {
//        // Chuyển đổi Drawable thành Bitmap
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) mainAvatar.getDrawable();
//        Bitmap bitmap = bitmapDrawable.getBitmap();
//        // Tạo một byte array output stream để ghi bitmap vào
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//        // Chuyển đổi ByteArrayOutputStream thành mảng byte
//        byte[] byteArray = byteArrayOutputStream.toByteArray();
//        Intent i = new Intent (Profile.this, ProfileChange.class);
//        i.putExtra("currentAvatar", byteArray);
//        startActivityForResult(i,1);
//    }
//
//}