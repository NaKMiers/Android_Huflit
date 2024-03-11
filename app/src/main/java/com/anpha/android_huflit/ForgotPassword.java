package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

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
    EditText emailFPEdt;
    Button sendFPBtn;
    OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mapping();
        generateRandomString(6);
    }

    //Ánh xạ
    void mapping(){
        emailFPEdt = findViewById(R.id.emailFPEdt);
        sendFPBtn = findViewById(R.id.sendFPBtn);
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


    void SendMail(String url) throws IOException {
        // prevent empty prompt
        if (emailFPEdt.getText().toString().trim() == "") return;

        RequestBody formBody = new FormBody.Builder()
//                .add("chatId", "")
                .add("email", emailFPEdt.getText().toString().trim())
                .add("code",generateRandomString(6))
                .build();

        Request request = new Request.Builder()
                .url(url + "/auth/forgot-password")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    ForgotPassword.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {


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



