package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anpha.android_huflit.Models.Prompt;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Security extends AppCompatActivity {
    // Elements
    ImageView avatar, homeBtn, profileBtn, themeBtn, securityBtn, logoutBtn;
    EditText odlPasswordEdt, newPasswordEdt, reNewPasswordEdt;
    Button saveBtn;

    // libraby tools
    OkHttpClient client = new OkHttpClient();

    // values
    String token, userId;
    String API = "https://android-huflit-server.vercel.app";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        int themeId = preferences.getInt("themeId", R.style.Theme1);
        setTheme(themeId);

        setContentView(R.layout.activity_security);

        requireAuth();

        // mapping
        mapping();

        // events
        addEvents();

        // Set user information
        userId = preferences.getString("userId", ""); //lưu trữ tên người dùng
        token = preferences.getString("token", "");
    }

    private void mapping() {
        homeBtn = findViewById(R.id.homeBtn);
        profileBtn = findViewById(R.id.profileBtn);
        themeBtn = findViewById(R.id.themeBtn);
        securityBtn = findViewById(R.id.securityBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        avatar = findViewById(R.id.avatar);
        odlPasswordEdt = findViewById(R.id.odlPasswordEdt);
        newPasswordEdt = findViewById(R.id.newPasswordEdt);
        reNewPasswordEdt = findViewById(R.id.reNewPasswordEdt);
        saveBtn = findViewById(R.id.saveBtn);
    }

    private void addEvents() {
        homeBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, TextChat.class);
            startActivity(intent);
        });
        profileBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProfileView.class);
            startActivity(intent);
        });
        themeBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, ThemeView.class);
            startActivity(intent);
        });
        securityBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Security.class);
            startActivity(intent);
        });
        logoutBtn.setOnClickListener(view -> {
            SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
        saveBtn.setOnClickListener(view -> {
            String oldPassword = odlPasswordEdt.getText().toString().trim();
            String newPassword = newPasswordEdt.getText().toString().trim();
            String reNewPassword = reNewPasswordEdt.getText().toString().trim();

            if (oldPassword.isEmpty()) {
                Toast.makeText(this, "Mật khẩu cũ không được trống", Toast.LENGTH_SHORT).show();
                return;
            }

            if (newPassword.isEmpty()) {
                Toast.makeText(this, "Mật khẩu mới không được trống", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(reNewPassword)) {
                Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d("oldPassword", oldPassword);
            Log.d("newPassword", newPassword);
            Log.d("reNewPassword", reNewPassword);

            try {
                ChangePassword(API + "/user/change-password", oldPassword, newPasswordEdt.getText().toString().trim());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private void requireAuth() {
        SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        String userId = preferences.getString("userId", ""); //lưu trữ tên người dùng
        if (userId.isEmpty()) {
            Intent intent = new Intent(Security.this, Login.class);
            startActivity(intent);
            finish();
        }
    }


    void ChangePassword(String url, String oldPassword, String newPassword) throws IOException {        // prevent empty prompt
        if (token.isEmpty()) {
            Toast.makeText(Security.this, "Token không tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody formBody = new FormBody.Builder()
                .add("oldPassword", oldPassword)
                .add("newPassword", newPassword)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .patch(formBody)
                .addHeader("Authorization", "Bearer " + token)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    Security.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject(myResponse);
                                String message = json.optString("message");

                                // show success message
                                Toast.makeText(Security.this, message, Toast.LENGTH_SHORT).show();

                                // reset form
                                odlPasswordEdt.setText("");
                                newPasswordEdt.setText("");
                                reNewPasswordEdt.setText("");

                                Intent intent = new Intent(Security.this, ProfileView.class);
                                startActivity(intent);
                                finish();

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                } else {
                    final String myResponse = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject(myResponse);
                                String message = json.optString("message");

                                // show success message
                                Toast.makeText(Security.this, message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
}