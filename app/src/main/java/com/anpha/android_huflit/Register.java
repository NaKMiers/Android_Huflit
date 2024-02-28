package com.anpha.android_huflit;

import  androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Register extends AppCompatActivity {
    EditText edtemail,edtphone,edtusername,edtpasswordlogin,edtpassagainregister;
    Button btnregister;
    TextView txtsigninregister;

    // Thực hiện request
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtemail=findViewById(R.id.edtemailregister);
        edtphone=findViewById(R.id.edtphoneregister);
        edtpasswordlogin =findViewById(R.id.edtpasswordregister);
        edtpassagainregister=findViewById(R.id.edtpassagainregister);
        edtusername=findViewById(R.id.edtusernameregister);
        btnregister=findViewById(R.id.btnregister);
        txtsigninregister=findViewById(R.id.txtsigninregister);

        txtsigninregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(Register.this,Login.class);
                startActivity(register);
            }
        });
    }

    public void handleRegister(View view) {
        String email = edtemail.getText().toString().trim();
        String username = edtusername.getText().toString();
        String password = edtpasswordlogin.getText().toString();
        String confirmPassword = edtpassagainregister.getText().toString();

        // Kiểm tra điều kiện nhập đầy đủ thông tin và mật khẩu khớp nhau
        if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty())
        {
            Toast.makeText(Register.this,"Please fill in all fields",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(Register.this, "Password and Confirm Password must match", Toast.LENGTH_SHORT).show();
            return;
        }
        // tạo request body với thông tin đăng ký
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("username", username)
                .add("password", password)
                .build();

        //tạo request POST
        Request request = new Request.Builder()
                .url("https://android-huflit-server.vercel.app/auth/register")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Register.this,"Registation failed",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful())
                {
                    final String myResponse = response.body().string();
                    Log.d("--------- Register RES -------", myResponse);

                    // Đăng ký thành công, chuyển sang màn hình đăng nhập
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);

                    // lưu dữ liệu registed user toàn cục

                }
                else
                {
                    // Đăng ký thất bại
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Register.this,"Registration failed",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}