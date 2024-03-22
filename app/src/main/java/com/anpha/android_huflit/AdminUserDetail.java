package com.anpha.android_huflit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anpha.android_huflit.ListUser.User;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AdminUserDetail extends AppCompatActivity {
    // elements
    TextView usernameTv, emailTv, fullnameTv, birthdayTv, jobTv, addressTv, roleTv, authTypeTv;
    Button deleteBtn;

    // values
    String token, userId;
    String API = "https://android-huflit-server.vercel.app";

    // library tools
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        int themeId = preferences.getInt("themeId", R.style.Theme1);
        setTheme(themeId);

        setContentView(R.layout.activity_admin_user_detail);

        requireAdmin();

        // mapping
        mapping();

        // get user intent
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");

        userId = user.getIDUser();

        usernameTv.setText(user.getUserName());
        fullnameTv.setText(user.getFirstname() + " " + user.getLastname());
        emailTv.setText(user.getEmailUser());
        birthdayTv.setText(user.getBirthday());
        jobTv.setText(user.getJob());
        addressTv.setText(user.getAdderss());
        roleTv.setText(user.getRole());
        authTypeTv.setText(user.getAuthType());
    }

    private void mapping() {
        usernameTv = findViewById(R.id.usernameTv);
        fullnameTv = findViewById(R.id.fullnameTv);
        roleTv = findViewById(R.id.roleTv);
        emailTv = findViewById(R.id.emailTv);
        birthdayTv = findViewById(R.id.birthdayTv);
        jobTv = findViewById(R.id.jobTv);
        addressTv = findViewById(R.id.addressTv);
        authTypeTv = findViewById(R.id.authTypeTv);
        deleteBtn = findViewById(R.id.deleteBtn);
    }

    private void requireAdmin() {
        SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        String role = preferences.getString("role", "");

        if (!role.equals("admin")) {
            Intent intent = new Intent(AdminUserDetail.this, AdminUser.class);
            startActivity(intent);
            finish();
        }

        token = preferences.getString("token", "");
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có chắc chắn muốn xóa người dùng này?")
                .setCancelable(false)
                .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Thực hiện xóa người dùng
                        DeleteUser(API + "/admin/user/" + userId + "/delete");
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Đóng dialog nếu người dùng chọn "Hủy"
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void handleDeleteUser(View view) {
        if (userId == null || userId.isEmpty()) {
            Toast.makeText(this, "User không tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Token không tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }
        showDeleteConfirmationDialog();
    }

    private void DeleteUser(String url) {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .addHeader("Authorization", "Bearer " + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    AdminUserDetail.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("Delete User RES", myResponse);

                            Toast.makeText(AdminUserDetail.this, "User đã bị xóa", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(AdminUserDetail.this, AdminUser.class);
                            startActivity(intent);
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

    public void handleBack(View view) {
        Intent intent = new Intent(AdminUserDetail.this, AdminUser.class);
        startActivity(intent);
    }
}