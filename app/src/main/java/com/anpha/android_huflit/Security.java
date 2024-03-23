package com.anpha.android_huflit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Security extends AppCompatActivity {
    EditText edtOldPassSecurity, edtNewPassSecurity, edtNewPassAgainSecurity;
    Button btnSaveSecurity;
    OkHttpClient client = new OkHttpClient();
    Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        mappings();
    }

    private void mappings() {
        edtOldPassSecurity = findViewById(R.id.edtOldPassSecurity);
        edtNewPassSecurity = findViewById(R.id.edtNewPassSecurity);
        edtNewPassAgainSecurity = findViewById(R.id.edtNewPassAgainSecurity);
        btnSaveSecurity = findViewById(R.id.btnSaveSecurity);
    }

    public void HandleSaveSecurity(View view) throws IOException{
        String oldPassword = edtOldPassSecurity.getText().toString();
        String newPassword = edtNewPassSecurity.getText().toString();
        String againPassword = edtNewPassAgainSecurity.getText().toString();
        SharedPreferences preferences = getSharedPreferences("myPreferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //lấy mật khẩu cũ
        String Password = preferences.getString("Password", "");
        //lưu newPassword vào SharedPreferences
        editor.putString("newPassword", newPassword);
        editor.apply();
        Log.d("oldPassword",Password);
        Log.d("newPassword",newPassword);

        if (!oldPassword.equals(Password)) {
            Toast.makeText(this, "Mật khẩu cũ không đúng.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(againPassword)) {
            Toast.makeText(Security.this, "Mật khẩu không trùng khớp.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!conditionPassword(newPassword)) {
            Toast.makeText(Security.this, "Mật khẩu phải có ít nhất 6 ký tự và chứa ít nhất một chữ hoa, một chữ thường và một chữ số.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(oldPassword.equals(Password) && newPassword.equals(againPassword) && conditionPassword(newPassword))
        {
            RequestBody formBody = new FormBody.Builder()
                    .add("oldPassword",oldPassword)
                    .add("newPassword",newPassword)
                    .build();

            String SecurityUrl = "https://android-huflit-server.vercel.app/user/change-password";

             request = new Request.Builder()
                    .url(SecurityUrl)
                    .post(formBody)
                    .build();

            Toast.makeText(Security.this,"Đổi mật khẩu thành công!!!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Security.this,Login.class);
            startActivity(intent);
        }
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }
        });
        Log.d("ChangePassword","New Password saved in SharedPreferences: "+newPassword);

    }

    private boolean conditionPassword(String password) {
        // Kiểm tra độ dài mật khẩu có ít nhất 6 ký tự
        if (password.length() < 6) {
            return false;
        }

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;

        // Kiểm tra từng ký tự trong mật khẩu
        for (char c : password.toCharArray()) {
            // Nếu là chữ cái viết hoa
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            }
            // Nếu là chữ cái viết thường
            else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            }
            // Nếu là chữ số
            else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }
        // Kiểm tra xem mật khẩu có đủ điều kiện không
        return hasUpperCase && hasLowerCase && hasDigit;
    }
}
