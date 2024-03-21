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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anpha.android_huflit.Models.Prompt;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ForgotPassword extends AppCompatActivity {
    EditText emailFPEdt, codeFPEdt;
    TextView emailBackground, codeBackground;
    Button sendFPBtn;
    LinearLayout inputEmailBox, inputCodeBox;
    OkHttpClient client = new OkHttpClient();

    String code;
    Boolean isSent = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mapping();
        generateRandomString(6);

        isSent = true;
        SharedPreferences preferences = getSharedPreferences("myPreferences", MODE_PRIVATE);
        String emailReset = preferences.getString("emailReset", ""); //lưu trữ tên người dùng
        emailBackground.setText(emailReset);
    }

    //Ánh xạ
    void mapping(){
        emailFPEdt = findViewById(R.id.emailFPEdt);
        codeFPEdt = findViewById(R.id.codeFPEdt);
        sendFPBtn = findViewById(R.id.sendFPBtn);
        emailBackground = findViewById(R.id.emailBackground);
        codeBackground = findViewById(R.id.codeBackground);
        inputEmailBox = findViewById(R.id.inputEmailBox);
        inputCodeBox = findViewById(R.id.inputCodeBox);
    }

    // Hàm tạo chuỗi ngẫu nhiên
    private String generateRandomString(int length) {
        // Ký tự cho phép trong chuỗi
        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        // Tạo đối tượng Random
        Random random = new Random();

        // StringBuilder để xây dựng chuỗi ngẫu nhiên
        StringBuilder randomString = new StringBuilder();

        // Tạo chuỗi ngẫu nhiên bằng cách lựa chọn ký tự từ danh sách allowedCharacters
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            char randomChar = allowedCharacters.charAt(randomIndex);
            randomString.append(randomChar);
        }

        // Trả về chuỗi ngẫu nhiên dưới dạng String
        Log.d("dsfdsg", randomString.toString());
        return randomString.toString();
    }

    public void handleSendFP(View view) throws IOException {
        if (isSent) {
            // nếu nhập code đúng
//            if (codeFPEdt.getText().toString().trim() == code)
            if (codeFPEdt.getText().toString().trim().equals(code)) {
                if (codeFPEdt.getText().toString().equals(code)) {
                    // reset when code is available
                    // Làm tiếp chỗ reset này

                    //Bước 1: Tạo cái request để reset password tương tự lại send mail
                    String token = "YOUR_TOKEN";
                    String newPassword = "YOUR NEW PASSWORD";
                    RequestBody formBody = new FormBody.Builder()

                            .add("email", emailFPEdt.getText().toString().trim())
                            .add("token", token)
                            .add("newPassword", newPassword)
                            .build();
//
//                Request request = new Request.Builder()
//                        .url(url + "/auth/reset-password")
//                        .post(formBody)
//                        .build();
                    String resetPasswordUrl = "https://android-huflit-server.vercel.app";

                    Request request = new Request.Builder()
                            .url(resetPasswordUrl) // Set the URL based on your endpoint
                            .post(formBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if (response.isSuccessful()) {
                                // Bước 2: Sau khi nhập phản hồi từ yêu cầu đổi reset pass
                                Intent intent = new Intent(ForgotPassword.this, ResetPassword.class);
                                startActivity(intent);
                            }
                            // Nếu nhập code sai
                            else {
                                Toast.makeText(ForgotPassword.this, "Code không hợp lệ. Hãy nhập lại!!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        }
                    });

                } else {
                    // Báo lỗi rồi xử lí gì gì đó để người dùng nhập lại (Hãy đặt mình vào vị trí người dùng sẽ tự biết phải làm gì)
                    Toast.makeText(ForgotPassword.this, "Mã xác nhận không đúng", Toast.LENGTH_SHORT).show();
                }

            } else {
// send email to get code
                SendMail("https://android-huflit-server.vercel.app");
            }
        }
    }

    void SendMail(String url) throws IOException {
        // prevent empty prompt
        if (emailFPEdt.getText().toString().trim().equals("")) return;
        //code = generateRandomString(6);
//        code = generateRandomString(6);
        code = "ASDASD";

        RequestBody formBody = new FormBody.Builder()
                .add("email", emailFPEdt.getText().toString().trim())
                .add("code", code)
                .build();

        Request request = new Request.Builder()
                .url(url + "/auth/forgot-password")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    final String myResponse = response.body().string();

                    ForgotPassword.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("asdaslkdjalskd", myResponse);
                            sendFPBtn.setText("Reset");

                            // hide email input
                            emailBackground.setVisibility(View.INVISIBLE);
                            inputEmailBox.setVisibility(View.INVISIBLE);
                            // show code input
                            codeBackground.setVisibility(View.VISIBLE);
                            inputCodeBox.setVisibility(View.VISIBLE);

                            isSent = true;
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