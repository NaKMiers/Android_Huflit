package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.annotation.NonNull;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import android.app.Application;
import com.google.firebase.FirebaseApp;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ForgotPassword extends AppCompatActivity {
    private static final String TAG = "ForgotPassword"; //giúp theo dõi, quản lý log
    EditText edtemailforgotpassword;
    Button btnokforgotpassword;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //khởi tạo Firebase
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_forgot_password);
        addControls();
    }

    //Ánh xạ
    private void addControls() {
        edtemailforgotpassword = findViewById(R.id.edtemailforgotpassword);
        btnokforgotpassword = findViewById(R.id.btnokforgotpassword);

    }
    
    public void onClickForgotPassword(View view) {
        sendPasswordResetEmail();
        // Lấy thông tin email từ trường nhập liệu
        String email = edtemailforgotpassword.getText().toString();

        //nếu người dùng bỏ trống
        if (email.isEmpty()) {
            edtemailforgotpassword.setError("Vui lòng nhập địa chỉ email của bạn !!!");
            return;
        }

        // Tạo request body với thông tin đăng nhập
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .build();

        //tạo request post
        Request request = new Request.Builder()
                .url("https://android-huflit-server.vercel.app/auth/forgot-password")
                .post(requestBody)
                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@androidx.annotation.NonNull Call call, @androidx.annotation.NonNull IOException e) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(ForgotPassword.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                if(response.isSuccessful()){
//                    final String myResponse = response.body().string();
//                    Log.d("---------Forgot----------",myResponse);
//
//                    //Lưu email vào SharedPreferences
//                    android.content.SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString("email",email);
//                    editor.apply();
//                }
//                else {
//                    // Đăng nhập thất bại
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(ForgotPassword.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//            }
//            }
//        });
    }




    private void sendPasswordResetEmail() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = "luuhonghuu530@gmail.com";

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPassword.this,"Email sent.",Toast.LENGTH_SHORT) .show();
                        }
                        else {
                            Toast.makeText(ForgotPassword.this,"lỗi",Toast.LENGTH_SHORT) .show();
                        }
                    }
                });
    }
}


