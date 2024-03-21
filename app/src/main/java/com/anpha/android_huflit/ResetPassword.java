package com.anpha.android_huflit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.util.Log;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class ResetPassword extends AppCompatActivity {
    EditText edtnewpassword, edtpassagainreset;
    Button btnokreset;
    OkHttpClient client = new OkHttpClient();
    String API = "https://android-huflit-server.vercel.app";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        mappings();

        SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        String emailReset = preferences.getString("emailReset", "");

    }

    private void mappings() {
        edtnewpassword = findViewById(R.id.edtnewpassword);
        edtpassagainreset = findViewById(R.id.edtpassagainreset);
        btnokreset = findViewById(R.id.btnokreset);
    }

    public void handleSaveRP(View view) throws IOException {
        String newPassword = edtnewpassword.getText().toString();
        String confirmPassword = edtpassagainreset.getText().toString();

        Request request;
        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(ResetPassword.this, "Mật khẩu không trùng khớp. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
            return; // Thoát khỏi phương thức nếu mật khẩu không trùng khớp
        } else {
            SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
            String emailReset = preferences.getString("emailReset", "");

            RequestBody formBody = new FormBody.Builder()
                    .add("newPassword", newPassword)
                    .add("email", emailReset)
                    .build();

            String resetPasswordUrl = "https://android-huflit-server.vercel.app/auth/reset-password";

            request = new Request.Builder()
                    .url(resetPasswordUrl)
                    .post(formBody)
                    .build();
            Log.d("emailReset", emailReset);

            Intent intent = new Intent(ResetPassword.this, Login.class);
            startActivity(intent);
        }
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // Xử lý khi gặp lỗi trong quá trình gửi yêu cầu
                ResetPassword.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(ResetPassword.this, "Có lỗi xảy ra. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                        

                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // Xử lý phản hồi từ máy chủ
                if (response.isSuccessful()) {
                    final String Response = response.body().string();
                    Log.d("Update Password RES: ", Response);
                    // Phản hồi thành công, chuyển hướng đến màn hình đăng nhập
                    Intent intent = new Intent(ResetPassword.this, Login.class);
                    startActivity(intent);
                } else {
                    // Phản hồi không thành công, hiển thị thông báo lỗi
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ResetPassword.this, "Có lỗi xảy ra khi đặt lại mật khẩu. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    try {
//                        String responseBody = response.body().string();
//                        Log.d("--------- Reset Password RESPONSE -------", responseBody);
//
//                        JSONObject jsonResponse = new JSONObject(responseBody);
//                        JSONObject userJson = jsonResponse.optJSONObject("user");
//
//                        if (userJson != null) {
//                            String newPassword = userJson.optString("email");
//
//                            // Lưu newPassword vào SharedPreferences
//                            SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = preferences.edit();
//                            editor.putString("newPassword", newPassword);
//                            editor.apply();
//
//                            // Chuyển hướng sang màn hình Login
//                            Intent intent = new Intent(ResetPassword.this, Login.class);
//                            startActivity(intent);
//                        } else {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(ResetPassword.this, "Không tìm thấy dữ liệu người dùng.", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                    } catch (JSONException e) {
//                        throw new RuntimeException(e);
//                    }
//                    } else {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(ResetPassword.this, "Có lỗi xảy ra khi đặt lại mật khẩu. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//        });
}
