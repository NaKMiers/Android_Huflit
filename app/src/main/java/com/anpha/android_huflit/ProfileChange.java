package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.health.ProcessHealthStats;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anpha.android_huflit.Models.Prompt;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileChange extends AppCompatActivity {
   // elements
    ImageView avatar;
    TextView usernameTv, emailTv;
    EditText lastnameEdt, firstnameEdt, birthdayEdt, jobEdt, addressEdt;
    Button saveBtn, cancelBtn;

    // libraby tools
    OkHttpClient client = new OkHttpClient();

    // values
    String userId;
    String token;
    String API = "https://android-huflit-server.vercel.app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        int themeId = preferences.getInt("themeId", R.style.Theme1);
        setTheme(themeId);

        setContentView(R.layout.activity_profile_change);

        // mapping
        mapping();

        // events
        addEvents();

        // Set user information
        String username = preferences.getString("username", ""); //lưu trữ tên người dùng
        userId = preferences.getString("userId", ""); //lưu trữ tên người dùng
        token = preferences.getString("token", "");
        String email = preferences.getString("email", ""); //lưu trữ tên người dùng
        String avt = preferences.getString("avatar", "");
        String firstname = preferences.getString("firstname", "");
        String lastname = preferences.getString("lastname", "");
        String birthday = preferences.getString("birthday", "");
        String job = preferences.getString("job", "");
        String address = preferences.getString("address", "");

        // set view
        Picasso.get()
                .load(API + avt)
                .error(R.drawable.boy)
                .into(avatar);
        usernameTv.setText(username);
        emailTv.setText(email);
        lastnameEdt.setText(lastname);
        firstnameEdt.setText(firstname);
        birthdayEdt.setText(birthday);
        jobEdt.setText(job);
        addressEdt.setText(address);
    }

    private void mapping() {
        avatar = findViewById(R.id.avatar);
        usernameTv = findViewById(R.id.usernameTv);
        emailTv = findViewById(R.id.emailTv);
        lastnameEdt = findViewById(R.id.lastnameEdt);
        firstnameEdt = findViewById(R.id.firstnameEdt);
        birthdayEdt = findViewById(R.id.birthdayEdt);
        jobEdt = findViewById(R.id.jobEdt);
        addressEdt = findViewById(R.id.addressEdt);
        saveBtn = findViewById(R.id.saveBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
    }

    private void addEvents() {
        saveBtn.setOnClickListener(view -> {
            UpdateProfile(API + "/user/update-profile");
        });

        cancelBtn.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileChange.this, ProfileView.class);
            startActivity(intent);
        });
    }

    private void UpdateProfile(String url) {
        // prevent empty prompt
        if (token.isEmpty()) {
            Toast.makeText(ProfileChange.this, "Token không tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        // new birthday
        String birthday = birthdayEdt.getText().toString();
        String[] parts = birthday.split("/");
        String day = parts[0];
        String month = parts[1];
        String year = parts[2];
        String newBirthday = year + "-" + month + "-" + day;


        RequestBody formBody = new FormBody.Builder()
                .add("firstname", firstnameEdt.getText().toString())
                .add("lastname", lastnameEdt.getText().toString())
                .add("birthday", newBirthday)
                .add("job", jobEdt.getText().toString())
                .add("address", addressEdt.getText().toString())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    Log.d("Update Profile RES: ", myResponse);


                    ProfileChange.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                JSONObject json = new JSONObject(myResponse);
                                JSONObject userJson = json.optJSONObject("user");

                                // get prompt values
                                String firstname = userJson.optString("firstname");
                                String lastname = userJson.optString("lastname");
                                String birthday = userJson.optString("birthday");
                                String address = userJson.optString("address");
                                String job = userJson.optString("job");

                                // Định dạng của ngày đầu vào
                                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                                inputFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Đặt múi giờ là UTC

                                // Định dạng của ngày đầu ra
                                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                Date birthdayBefore = inputFormat.parse(birthday);
                                assert birthdayBefore != null;
                                String birthdayAfter = outputFormat.format(birthdayBefore);

                                // Lưu username vào SharedPreferences
                                SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("firstname", firstname);
                                editor.putString("lastname", lastname);
                                editor.putString("birthday", birthdayAfter);
                                editor.putString("job", job);
                                editor.putString("address", address);
                                editor.apply();

                                Toast.makeText(ProfileChange.this, "Đã cập nhật Profile", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ProfileChange.this, ProfileView.class);
                                startActivity(intent);

                            } catch (JSONException | ParseException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(ProfileChange.this, "Token không tồn tại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}