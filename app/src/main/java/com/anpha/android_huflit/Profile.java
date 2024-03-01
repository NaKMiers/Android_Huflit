package com.anpha.android_huflit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class Profile extends AppCompatActivity {

    View informationView;
    ConstraintLayout layoutProfile;
    ImageView selectedAvatarImageView, mainAvatar;
    TextView txtChange, txtSurName, txtName, txtBirthday, txtJob, txtAddress;

    PopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mainAvatar = findViewById(R.id.mainAvatar);
        txtChange = findViewById(R.id.txtChange);
        txtSurName = findViewById(R.id.txtSurName);
        txtName = findViewById(R.id.txtName);
        txtBirthday = findViewById(R.id.txtBirthday);
        txtJob = findViewById(R.id.txtJob);
        txtAddress = findViewById(R.id.txtAddress);
        layoutProfile = findViewById(R.id.layoutProfile);
        informationView = findViewById(R.id.informationView);

        mainAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAvatarSelectionPopup();
            }
        });
        txtChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đổi Drawable thành Bitmap
                BitmapDrawable bitmapDrawable = (BitmapDrawable) mainAvatar.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                // Tạo một byte array output stream để ghi bitmap vào
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                // Chuyển đổi ByteArrayOutputStream thành mảng byte
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                Intent i = new Intent (Profile.this, profileChange.class);
                i.putExtra("currentAvatar", byteArray);
                startActivityForResult(i,1);
            }
        });

    }

    private void showAvatarSelectionPopup() {
        View popupView = LayoutInflater.from(this).inflate(R.layout.avatar_selection_popup,null);
        ImageView currentAvatar = popupView.findViewById(R.id.currentAvatar);
        currentAvatar.setImageDrawable(mainAvatar.getDrawable());
        popupWindow = new PopupWindow(
                popupView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                true
        );
        popupWindow.showAtLocation(popupView, Gravity.CENTER,0,0);
        informationView.setAlpha(0.4f);
        int dimColor = Color.parseColor("#80000000");
        layoutProfile.setBackgroundColor(dimColor);
    }
    public void onNewAvatarSelected(View view) {
        selectedAvatarImageView = (ImageView) view;
        Drawable drawable = selectedAvatarImageView.getDrawable();
        mainAvatar.setImageDrawable(drawable);
        if (popupWindow != null) {
            popupWindow.dismiss();
            layoutProfile.setBackgroundColor(Color.TRANSPARENT);
            informationView.setAlpha(1.0f);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 2){
            String surname = data.getStringExtra("surName");
            String name = data.getStringExtra("name");
            String birthday = data.getStringExtra("birthday");
            String job = data.getStringExtra("job");
            String address = data.getStringExtra("address");

            txtSurName.setText("HỌ: " +surname);
            txtName.setText("TÊN: " +name);
            txtBirthday.setText("NGÀY SINH: " +birthday);
            txtJob.setText("NGHỀ NGHIỆP: " +job);
            txtAddress.setText("ĐỊA CHỈ: " +address);
        }
    }


}