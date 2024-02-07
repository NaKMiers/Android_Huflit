package com.anpha.android_huflit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    TextView txtChange, txtSurName, txtName, txtBirthday, txtJob, txtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtChange = findViewById(R.id.txtChange);
        txtSurName = findViewById(R.id.txtSurName);
        txtName = findViewById(R.id.txtName);
        txtBirthday = findViewById(R.id.txtBirthday);
        txtJob = findViewById(R.id.txtJob);
        txtAddress = findViewById(R.id.txtAddress);
        txtChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (Profile.this, profileChange.class);
                startActivityForResult(i,1);
            }
        });

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