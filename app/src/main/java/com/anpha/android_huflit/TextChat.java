package com.anpha.android_huflit;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
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

    ImageView btnSend;
    EditText edtTextChat;

    TextView txtHelp1;

    public static final MediaType JSON = MediaType.get("application/json");
    OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_chat);


        recyclerView = findViewById(R.id.recyclerView);
        messages = new ArrayList<>();
        adapter = new MessageAdapter(messages);
        recyclerView.setAdapter(adapter);
        toolbarChat = findViewById(R.id.toolbarChat);
        btnSend = findViewById(R.id.btnSend);
        edtTextChat = findViewById(R.id.edtTextChat);
        txtHelp1 = findViewById(R.id.txtHelp1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        edtTextChat.requestFocus();
        edtTextChat.setSelection(edtTextChat.getText().length());


        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_chat_menu, null);
        popupWindow = new PopupWindow(
                popupView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true
        );

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
    }

    public void handleSendChatPrompt(View view) throws IOException {
        String messageText = edtTextChat.getText().toString().trim();
        if (!messageText.isEmpty()) {
            txtHelp1.setText("");
//            // Tạo tin nhắn gửi
//            Message sentMessage = new Message(messageText, true);
//            // Thêm tin nhắn gửi vào cuối danh sách
//            messages.add(sentMessage);
//            // Thông báo cho Adapter biết rằng có một mục mới được thêm vào cuối danh sách
//            adapter.notifyItemInserted(messages.size() - 1);
//
//            // Tự động gửi tin nhắn nhận
//            Message receivedMessage = new Message("Tin nhắn nhận tự động", false);
//            // Thêm tin nhắn nhận vào cuối danh sách
//            messages.add(receivedMessage);
//            // Thông báo cho Adapter biết rằng có một mục mới được thêm vào cuối danh sách
//            adapter.notifyItemInserted(messages.size() - 1);
//
//            // Cuộn RecyclerView đến vị trí cuối cùng (để hiển thị tin nhắn mới)
//            recyclerView.scrollToPosition(messages.size() - 1);

            // Xóa nội dung của EditText sau khi gửi tin nhắn
            edtTextChat.setText("");

            SendChatPrompt("https://android-huflit-server.vercel.app/chat/create-completion");
        }
    }

    void SendChatPrompt(String url) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("prompt", "Hello")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .addHeader("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NWEyMWZlYWQyYjU0MTAzZDBkNmRiMjkiLCJ1c2VybmFtZSI6Im5ha21pZXJzIiwiZW1haWwiOiJkaXdhczExODE1MUBnbWFpbC5jb20iLCJyb2xlIjoidXNlciIsInRoZW1lIjowLCJpYXQiOjE3MDUxMjM4MjJ9.DJkq9FG3xOxUFQTUYwpXP3cXaczpGwgC8xxSG0x77iw") // Add the authorization header with bearer token
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    TextChat.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtHelp1.setText(myResponse);
                        }
                    });
                }
            }
        });
    }
}