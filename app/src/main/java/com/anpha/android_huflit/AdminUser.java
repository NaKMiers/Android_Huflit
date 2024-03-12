package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.anpha.android_huflit.ListUser.AdminUserAdapter;
import com.anpha.android_huflit.ListUser.User;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.anpha.android_huflit.ListUser.AdminUserAdapter;
import com.anpha.android_huflit.ListUser.User;
import com.anpha.android_huflit.Message.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AdminUser extends AppCompatActivity {
    ListView ListUser;
    AdminUserAdapter adminUserAdapter;
    ArrayList<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);
        loadDataFromApi();
        mapping();
//        loadData();

        userList = new ArrayList<>();
        adminUserAdapter = new AdminUserAdapter(this, R.layout.item_user, userList);


    }
//    private void loadData() {
//        adminUserAdapter.add(new User(R.drawable.owl,"123","huu","luuhonghuu530@gmail.com"));
//        adminUserAdapter.add(new User(R.drawable.owl,"153","linh","linhga123@gmail.com"));
//
//    }

    private void mapping() {
        ListUser = findViewById(R.id.ListUser);
        adminUserAdapter = new AdminUserAdapter(this, R.layout.item_user);
        ListUser.setAdapter(adminUserAdapter);
    }


    private void loadDataFromApi() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://android-huflit-server.vercel.app/admin/user/all")
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();

                    try {
                        JSONObject json = new JSONObject(responseData);
                        JSONArray jsonArray = json.getJSONArray("users");

                        userList = new ArrayList<>();
                        for (int i = 0; i < Objects.requireNonNull(jsonArray).length(); i++) {
                            JSONObject user = jsonArray.getJSONObject(i);
                            String id = user.optString("_id");
                            String username = user.optString("username");
                            String email = user.optString("email");
                            String avatar = user.optString("avatar");
                            // Assuming you have the appropriate constructor for User class
                            userList.add(new User(id, username, email, avatar));
                        }





                    } catch (JSONException e) {
                        e.printStackTrace();
                        showToast("Error parsing JSON");
                    }
                } else {

                    showToast("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                showToast("Failed to fetch data from API");
                Log.e("API_CALL", "Failed to fetch data from API: " + e.getMessage());
                showToast("Failed to fetch data from API");
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



