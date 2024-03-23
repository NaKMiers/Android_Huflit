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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.util.Log;

public class ResetPassword extends AppCompatActivity {
    EditText edtnewpassword, edtpassagainreset;
    Button btnokreset;
    OkHttpClient client = new OkHttpClient();
//    String API = "https://android-huflit-server.vercel.app";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        mappings();


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
            SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
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

            Toast.makeText(ResetPassword.this,"Đổi mật khẩu thành công!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ResetPassword.this, Login.class);
            startActivity(intent);
        }
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });
    }
}
