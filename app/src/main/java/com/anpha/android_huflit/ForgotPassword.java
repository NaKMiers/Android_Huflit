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
    Boolean isSent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        int themeId = preferences.getInt("themeId", R.style.Theme1);
        setTheme(themeId);

        setContentView(R.layout.activity_forgot_password);

        mapping();
        generateRandomString(6);
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
            if (codeFPEdt.getText().toString().trim().equals(code)) {
                // reset when code is available
                // Làm tiếp chỗ reset này
                // Bước 1: Tạo cái request để reset password tương tự lại send mail

                // Bước 2: Sau khi nhập phản hồi từ yêu cầu đổi reset pass
                //      - Nếu thành công: chuyển tới trang login
                //      - Nếu thất bại: báo lỗi bằng Toast ra màn hình rồi xử lí sao j j đó để người dùng nhập lại
            }
            // Nếu nhập code sai
            else {
                // Báo lỗi rồi xử lí gì gì đó để người dùng nhập lại (Hãy đặt mình vào vị trí người dùng sẽ tự biết phải làm gì)
            }

        } else {
            // send email to get code
            SendMail("https://android-huflit-server.vercel.app");
        }
    }

    void SendMail(String url) throws IOException {
        // prevent empty prompt
        if (emailFPEdt.getText().toString().trim() == "") return;
        code = generateRandomString(6);

        RequestBody formBody = new FormBody.Builder()
//                .add("chatId", "")
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
                if (response.isSuccessful()) {
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

