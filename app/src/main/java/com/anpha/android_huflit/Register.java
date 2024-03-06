package com.anpha.android_huflit;

import  androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SharedMemory;
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
        String email = edtemail.getText().toString();
        String username = edtusername.getText().toString();
        String password = edtpasswordlogin.getText().toString();
        String confirmPassword = edtpassagainregister.getText().toString();
        saveUserData(email,username,password);



        //thông báo yêu cầu nhập thông tin của người dùng
        if(email.isEmpty())
        {
            edtemail.setError("Vui lòng nhập email của bạn !!!");
            return;
        }
        if(username.isEmpty())
        {
            edtusername.setError("Vui lòng nhập tên đăng nhập của bạn ");
            return;
        }
        if(password.isEmpty())
        {
            edtpasswordlogin.setError("Vui lòng nhập mật khẩu của bạn !!!");
            return;
        }
        if(confirmPassword.isEmpty())
        {
            edtpassagainregister.setError("Vui lòng nhập lại mật khẩu của bạn !!! ");
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
                       saveUserData(email,username,password);
                    SharedPreferences preferences1 = getSharedPreferences("mypreferences1", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences1.edit();
                    editor.putString("email", email);
                    editor.apply();
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

    private void saveUserData(String email, String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email",email);
        editor.putString("username",username);
        editor.putString("password",password);
        editor.apply();
    }
}