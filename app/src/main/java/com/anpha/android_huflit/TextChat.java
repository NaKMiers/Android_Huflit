package com.anpha.android_huflit;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.PopupWindow;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.anpha.android_huflit.Message.Message;
import com.anpha.android_huflit.Message.MessageAdapter;
import com.anpha.android_huflit.Models.Prompt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TextChat extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private List<Message> messages;
    PopupWindow popupWindow;
    Toolbar toolbarChat;

    ImageView btnSend, navigationIcon;
    EditText edtTextChat;

    DrawerLayout drawerLayout;
    TextView txtHelp1, txtMode,txtusername;
     Button btnLogOut;
    ArrayList<Prompt> prompts = new ArrayList<>();

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_chat);

        // mapping
        toolbarChat = findViewById(R.id.toolbarChat);
        btnSend = findViewById(R.id.btnSendImg);
        edtTextChat = findViewById(R.id.edtTextChat);
        txtHelp1 = findViewById(R.id.txtHelp1);
        recyclerView = findViewById(R.id.recyclerViewImage);
        navigationIcon = findViewById(R.id.navigationIcon);
        drawerLayout = findViewById(R.id.drawerLayout);
        txtMode = findViewById(R.id.txtMode);
        //



        // initialize variables
        messages = new ArrayList<>();
        adapter = new MessageAdapter(messages);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_chat_menu, null);
        popupWindow = new PopupWindow(
                popupView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true
        );
        btnLogOut=popupView.findViewById(R.id.btnLogOut);
        txtusername=popupView.findViewById(R.id.txtusername);


        edtTextChat.requestFocus();
        edtTextChat.setSelection(edtTextChat.getText().length());


        navigationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOutUser();
            }
        });
       txtusername.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              Intent intent = new Intent(TextChat.this, ProfileView.class);
              startActivity(intent);
           }
       });
       // lấy dữ liệu từ SharedPreferces
        SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        String username = preferences.getString("username", ""); //lưu trữ tên người dùng
        txtusername.setText(username); // đặt tên người dùng trong textview
        toolbarChat.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.iconMenu)
                {
                    popupWindow.showAsDropDown(toolbarChat.findViewById(R.id.iconMenu));
                    return true;
                }
                return false;
            }
        });

        try {
            GetUserPrompts("https://android-huflit-server.vercel.app");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void LogOutUser() {
        SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit(); // Sửa lỗi gán lại biến preferences
        editor.remove("token");
        editor.apply();
        Intent intent = new Intent(TextChat.this, Login.class);
        // Xóa tất cả các hoạt động trước đó khỏi ngăn xếp và chuyển người dùng đến màn hình đăng nhập
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void handleSendChatPrompt(View view) throws IOException {
        String messageText = edtTextChat.getText().toString().trim();
        if (!messageText.isEmpty()) {
            txtHelp1.setText("");

            CreatePrompt("https://android-huflit-server.vercel.app");
            CreateCompletion("https://android-huflit-server.vercel.app");
        }
    }

    private void addNewMessage(String text, boolean isFromUser) {
        Message sentMessage = new Message(text, isFromUser);
        // Thêm tin nhắn gửi vào cuối danh sách
        messages.add(sentMessage);
        // Thông báo cho Adapter biết rằng có một mục mới được thêm vào cuối danh sách
        adapter.notifyItemInserted(messages.size() - 1);
    }


    void GetUserPrompts(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url + "/chat/get-prompts")
                .addHeader("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NWEyMWZlYWQyYjU0MTAzZDBkNmRiMjkiLCJ1c2VybmFtZSI6Im5ha21pZXJzIiwiZW1haWwiOiJkaXdhczExODE1MUBnbWFpbC5jb20iLCJyb2xlIjoidXNlciIsInRoZW1lIjowLCJpYXQiOjE3MDUxMjM4MjJ9.DJkq9FG3xOxUFQTUYwpXP3cXaczpGwgC8xxSG0x77iw") // Add the authorization header with bearer token
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    TextChat.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject(myResponse);
                                JSONArray jsonArray = json.getJSONArray("prompts");
//
                                for(int i = 0; i < Objects.requireNonNull(jsonArray).length(); i++){
                                    JSONObject prompt = jsonArray.optJSONObject(i);

                                    // get values
                                    String _id = prompt.optString("_id");
                                    String userId = prompt.optString("userId");
//                                    String chatId = prompt.optString("chatId");
                                    String type = prompt.optString("type");
                                    String from = prompt.optString("from");
                                    String text = prompt.optString("text");
//                                    String createdAt = prompt.optString("createdAt");
//                                    String updatedAt = prompt.optString("updatedAt");
//                                    JSONArray images = prompt.optJSONArray("images");

                                    // add each prompt to prompt list
                                    Prompt newPrompt = new Prompt(_id, userId, type, from, text);
                                    prompts.add(newPrompt);
                                }

                                // show prompts after get
                                for(Prompt prompt: prompts) {
                                    Log.d("Type", prompt.type);
                                    addNewMessage(prompt.text, Objects.equals(prompt.from, "user"));
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

    void CreatePrompt(String url) throws IOException {
        // prevent empty prompt
        if (edtTextChat.getText().toString().trim() == "") return;

        RequestBody formBody = new FormBody.Builder()
//                .add("chatId", "")
                .add("prompt", edtTextChat.getText().toString().trim())
                .add("password", "test")
                .build();

        Request request = new Request.Builder()
                .url(url + "/chat/create-prompt")
                .post(formBody)
                .addHeader("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NWEyMWZlYWQyYjU0MTAzZDBkNmRiMjkiLCJ1c2VybmFtZSI6Im5ha21pZXJzIiwiZW1haWwiOiJkaXdhczExODE1MUBnbWFpbC5jb20iLCJyb2xlIjoidXNlciIsInRoZW1lIjowLCJpYXQiOjE3MDUxMjM4MjJ9.DJkq9FG3xOxUFQTUYwpXP3cXaczpGwgC8xxSG0x77iw") // Add the authorization header with bearer token
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    TextChat.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject(myResponse);
                                JSONObject prompt = json.getJSONObject("prompt");

                                // get prompt values
                                String _id = prompt.optString("_id");
                                String userId = prompt.optString("userId");
//                                    String chatId = prompt.optString("chatId");
                                String type = prompt.optString("type");
                                String from = prompt.optString("from");
                                String text = prompt.optString("text");

                                // add new prompt to message list
                                Prompt newPrompt = new Prompt(_id, userId, type, from, text);
                                prompts.add(newPrompt);
                                addNewMessage(newPrompt.text, Objects.equals(newPrompt.from, "user"));

                                // clear text chat
                                recyclerView.scrollToPosition(messages.size() - 1);
                                edtTextChat.setText("");

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

    void CreateCompletion(String url) throws IOException {        // prevent empty prompt
        if (edtTextChat.getText().toString().trim() == "") return;

        txtHelp1.setText("Generating...");

        RequestBody formBody = new FormBody.Builder()
                .add("prompt", edtTextChat.getText().toString().trim())
                .add("model", "gpt-4")
                .add("maxTokens", "1500")
                .add("temperature", "1")
//                .add("chatId", "")
                .build();

        Request request = new Request.Builder()
                .url(url + "/chat/create-completion")
                .post(formBody)
                .addHeader("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NWEyMWZlYWQyYjU0MTAzZDBkNmRiMjkiLCJ1c2VybmFtZSI6Im5ha21pZXJzIiwiZW1haWwiOiJkaXdhczExODE1MUBnbWFpbC5jb20iLCJyb2xlIjoidXNlciIsInRoZW1lIjowLCJpYXQiOjE3MDUxMjM4MjJ9.DJkq9FG3xOxUFQTUYwpXP3cXaczpGwgC8xxSG0x77iw") // Add the authorization header with bearer token
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    TextChat.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject(myResponse);
                                JSONObject completion = json.getJSONObject("completion");

                                // get prompt values
                                String _id = completion.optString("_id");
                                String userId = completion.optString("userId");
//                                    String chatId = prompt.optString("chatId");
                                String type = completion.optString("type");
                                String from = completion.optString("from");
                                String text = completion.optString("text");

                                // add new prompt to message list
                                Prompt newPrompt = new Prompt(_id, userId, type, from, text);
                                prompts.add(newPrompt);
                                addNewMessage(newPrompt.text, Objects.equals(newPrompt.from, "user"));

                                // clear text chat
                                recyclerView.scrollToPosition(messages.size() - 1);
                                edtTextChat.setText("");
                                txtHelp1.setText("");
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

    public void handleChangeToImageMode(View view) {
        Intent intent = new Intent(TextChat.this,ImageChat.class);
        startActivity(intent);
    }
}