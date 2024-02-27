package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

   public class Login extends AppCompatActivity {
    Button btnsignin, btnlogin;
    EditText edtusername, edtpasswordlogin;
    TextView txtforgetpassword, txtloginwithgg;
    ImageView imgGG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnlogin = findViewById(R.id.btnlogin);
        btnsignin = findViewById(R.id.btnsignup);
       edtusername = findViewById(R.id.edtusernameregister);
        edtpasswordlogin = findViewById(R.id.edtpasswordregister);
        txtforgetpassword = findViewById(R.id.txtforgetpassword);
        txtloginwithgg = findViewById(R.id.txtloginwithgg);
        imgGG = findViewById(R.id.imgGG);

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin = new Intent(Login.this, Register.class);
                startActivity(signin);
            }
        });

        txtforgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotpassword = new Intent(Login.this, ForgotPassword.class);
                startActivity(forgotpassword);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin đăng nhập từ các EditText
                String username = edtusername.getText().toString();
                String password = edtpasswordlogin.getText().toString();
                if (username.isEmpty()) {
                    edtusername.setError("Please enter your email");
                    return;
                }

                if (password.isEmpty()) {
                    edtpasswordlogin.setError("Please enter your password");
                    return;
                }
//                if (username.isEmpty() || password.isEmpty()) {
//                    // Hiển thị thông báo nếu người dùng chưa nhập đủ thông tin
//                    Toast.makeText(Login.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                // Tạo request body với thông tin đăng nhập
                RequestBody requestBody = new FormBody.Builder()
                        .add("username",username)
                        .add("password", password)
                        .build();

                // Tạo request POST
                Request request = new Request.Builder()
                        .url("https://android-huflit-server.vercel.app/auth/login")
                        .post(requestBody)
                        .build();

                // Thực hiện request
                OkHttpClient client = new OkHttpClient();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        // Xử lý khi request thất bại
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        // Xử lý khi nhận được response từ server
                        if (response.isSuccessful()) {
                            // Đăng nhập thành công, chuyển sang màn hình text-chat
                            Intent intent = new Intent(Login.this, TextChat.class);
                            startActivity(intent);
                        } else {
                            // Đăng nhập thất bại
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });
    }


}
