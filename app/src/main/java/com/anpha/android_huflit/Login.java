package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

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
  // OkHttpClient client = new OkHttpClient();
       OkHttpClient client=new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // ánh xạ id
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
    }


   public void handleLogin(View view) {
       // Lấy thông tin đăng nhập từ các EditText
       String username = edtusername.getText().toString();
       String password = edtpasswordlogin.getText().toString();
       //thông báo khi người dùng bỏ trống
       if (username.isEmpty()) {
           edtusername.setError("Vui lòng nhập tên đăng nhập của bạn!!!");
           return;
       }

       if (password.isEmpty()) {
           edtpasswordlogin.setError("Vui lòng nhập mật khẩu của bạn !!!");
           return;
       }

       // Tạo request body với thông tin đăng nhập
       RequestBody requestBody = new FormBody.Builder()
               .add("emailOrUsername",username)
               .add("password", password)
               .build();

       // Tạo request POST
       Request request = new Request.Builder()
               .url("https://android-huflit-server.vercel.app/auth/login")
               .post(requestBody)
               .build();

       // Thực hiện request
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
               // Xử lý khi nhận được phản hồi từ server
               if (response.isSuccessful()) {

                   final String myResponse = response.body().string();
                   try {
                       Log.d("--------- Logined In RES -------", myResponse);
                       JSONObject json = new JSONObject(myResponse);


                       // get data from response
                       JSONObject userJson = json.optJSONObject("user");
                       String token = json.optString("token");
                       Log.d("-----token", token);

                       String username = userJson.optString("username");
                       String avatar = userJson.optString("avatar");
                       String email = userJson.optString("email");
                       String id = userJson.optString("_id");
                       String role = userJson.optString("role");
                       String authType = userJson.optString("authType");

    //                    Lưu username vào SharedPreferences
                       SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
                       SharedPreferences.Editor editor = preferences.edit();
                       editor.putString("username", username);
                       editor.putString("avatar", avatar);
                       editor.putString("token", token);
                       editor.putString("userId", id);
                       editor.putString("email", email);
                       editor.putString("role", role);
                       editor.putString("authType", authType);
                       editor.apply();

                       Class<?> nextActivity = role.equals("admin") ? TextChat.class : TextChat.class;
                       Intent intent = new Intent(Login.this, nextActivity);
                       startActivity(intent);
                   } catch (JSONException e) {
                       throw new RuntimeException(e);
                   }
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
}
