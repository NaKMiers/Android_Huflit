package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.anpha.android_huflit.ListUser.AdminUserAdapter;
import com.anpha.android_huflit.ListUser.User;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anpha.android_huflit.ListUser.AdminUserAdapter;
import com.anpha.android_huflit.ListUser.User;
import com.anpha.android_huflit.Message.Message;
import com.anpha.android_huflit.Models.Prompt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AdminUser extends AppCompatActivity {
    ListView ListUser;
    AdminUserAdapter adminUserAdapter;
    ArrayList<User> userList;
    String token;
    TextView userListTV;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);
        mapping();

        userList = new ArrayList<>();
        adminUserAdapter = new AdminUserAdapter(AdminUser.this, R.layout.item_user, userList);
        ListUser.setAdapter(adminUserAdapter);

        SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        token = preferences.getString("token", ""); //lưu trữ tên người dùng


        try {
            LoadDataFromApi("https://android-huflit-server.vercel.app");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void mapping() {
        ListUser = findViewById(R.id.ListUser);
        userListTV = findViewById(R.id.userListTV);
    }

    void LoadDataFromApi(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url + "/admin/user/all")
                .addHeader("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NWE1Y2YyM2NjOGQ3YzczYjc2MjE0Y2IiLCJ1c2VybmFtZSI6Im5oYXRsaW5oIiwiZW1haWwiOiJsZXRpZW5uaGF0bGluaDA4MDcyMDAzQGdtYWlsLmNvbSIsInJvbGUiOiJhZG1pbiIsInRoZW1lIjowLCJpYXQiOjE3MDkwOTI5ODR9.uQEttdetujJBcxykELqpPjXxqC6H_QgtVOh_BOdCeno") // Add the authorization header with bearer token
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();


                    AdminUser.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject(myResponse);
                                JSONArray jsonArray = json.getJSONArray("users");

                                for(int i = 0; i < Objects.requireNonNull(jsonArray).length(); i++){
                                    JSONObject user = jsonArray.optJSONObject(i);

                                    // get values
                                    String _id = user.optString("_id");
                                    String email = user.optString("email");
                                    String username = user.optString("username");
                                    String avatar = user.optString("avatar");

//                                     add each prompt to prompt list
                                    User newUser = new User(_id, email, username, avatar);
                                    userList.add(newUser);
                                    adminUserAdapter.notifyDataSetChanged();
                                }
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

    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AdminUser.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}



