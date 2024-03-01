package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.core.content.IntentSanitizer;

public class profileChange extends AppCompatActivity {

    ImageView avatarProfileChange;
    EditText edtSurname, edtName, edtBirthday, edtJob, edtAddress;

    Button btnSaveInfo;
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
        Intent i = getIntent();
        // Nhận mảng byte chứa hình ảnh từ Intent
        byte[] byteArray = getIntent().getByteArrayExtra("currentAvatar");
        // Chuyển đổi mảng byte thành Bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        avatarProfileChange.setImageBitmap(bitmap);
        btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("surName",edtSurname.getText().toString());
                i.putExtra("name",edtName.getText().toString());
                i.putExtra("birthday",edtBirthday.getText().toString());
                i.putExtra("job",edtJob.getText().toString());
                i.putExtra("address",edtAddress.getText().toString());
                setResult(2,i);
                finish();
            }
        });
    }
}