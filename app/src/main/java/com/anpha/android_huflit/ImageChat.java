package com.anpha.android_huflit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.anpha.android_huflit.Message.ImageMessage;
import com.anpha.android_huflit.Message.ImageMessageAdapter;
import com.anpha.android_huflit.Models.Prompt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.squareup.picasso.Picasso;

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

public class ImageChat extends AppCompatActivity {
    private RecyclerView recyclerViewImage;
    private ImageMessageAdapter adapter;
    private List<ImageMessage> messages;

    TextView txtHelp2;
    EditText edtImgChat;
    ImageView receivedImage;
    ImageView btnSendImg;
    PopupWindow popupWindow;
    Toolbar toolbarImage;
    ImageButton btnPlus1, btnInCr, btnMinus1, btnDes;
    TextView txtAmount, txtSize,txtMode2;
    int currentValue = 1;
    Size[] ImageSize;
    int OptionSizeIndex = 0;
    ArrayList<Prompt> prompts = new ArrayList<>();
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_chat);

        toolbarImage = findViewById(R.id.toolbarImage);

        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_image_chat_menu, null);
        popupWindow = new PopupWindow(
                popupView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true
        );
        recyclerViewImage = findViewById(R.id.recyclerViewImage);
        messages = new ArrayList<>();
        adapter = new ImageMessageAdapter(messages);
        recyclerViewImage.setAdapter(adapter);
        txtHelp2 = findViewById(R.id.txtHelp2);
        edtImgChat = findViewById(R.id.edtImgChat);
        btnSendImg = findViewById(R.id.btnSendImg);
        btnMinus1 = popupView.findViewById(R.id.btnMinus1);
        btnInCr = popupView.findViewById(R.id.btnInCr);
        btnPlus1 = popupView.findViewById(R.id.btnPlus1);
        btnDes = popupView.findViewById(R.id.btnDes);
        txtAmount = popupView.findViewById(R.id.txtAmount);
        txtSize = popupView.findViewById(R.id.txtSize);
        txtMode2 = findViewById(R.id.txtMode2);
        toolbarImage.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.iconMenu) {
                    popupWindow.showAsDropDown(toolbarImage.findViewById(R.id.iconMenu));
                    return true;
                }
                return false;
            }
        });

        btnMinus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentValue > 1) {
                    currentValue--;
                    updateTextView();
                }
            }
        });
        btnPlus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentValue++;
                updateTextView();
            }
        });
        ImageSize = new Size[]{new Size(256, 256), new Size(512, 512), new Size(104, 1024)};
        btnInCr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OptionSizeIndex < ImageSize.length - 1) {
                    OptionSizeIndex = (OptionSizeIndex + 1) % ImageSize.length;
                    updateTextView();
                }
            }
        });
        btnDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OptionSizeIndex > 0) {
                    OptionSizeIndex = (OptionSizeIndex - 1) % ImageSize.length;
                    updateTextView();
                }
            }
        });

        try {
            GetUserImagePrompts("https://android-huflit-server.vercel.app");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void updateTextView() {
        txtAmount.setText(String.valueOf(currentValue));
        Size selectedSize = ImageSize[OptionSizeIndex];
        txtSize.setText(selectedSize.getWidth() + "x" + selectedSize.getHeight());

        try {
            GetUserImagePrompts("https://android-huflit-server.vercel.app");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleSentImagePrompt(View view) throws IOException {
        String messageText = edtImgChat.getText().toString().trim();
        if (!messageText.isEmpty()) {
            CreatePrompt("https://android-huflit-server.vercel.app");
            CreateImages("https://android-huflit-server.vercel.app");
        }
    }

    public void handleSendChatPrompt(View view) throws IOException {
        String messageText = edtImgChat.getText().toString().trim();
        if (!messageText.isEmpty()) {
            CreatePrompt("https://android-huflit-server.vercel.app");
            CreateImages("https://android-huflit-server.vercel.app");
        }
    }

    private void addNewMessage(String text, Boolean isFromUser, ArrayList<String> imageUrls) {
        ImageMessage message = new ImageMessage(text, isFromUser, imageUrls);

        // Thêm tin nhắn gửi vào cuối danh sách
        messages.add(message);
        // Thông báo cho Adapter biết rằng có một mục mới được thêm vào cuối danh sách
        adapter.notifyItemInserted(messages.size() - 1);
    }


    private void GetUserImagePrompts(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url + "/image/get-prompts")
                .addHeader("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NWEyMWZlYWQyYjU0MTAzZDBkNmRiMjkiLCJ1c2VybmFtZSI6Im5ha21pZXJzIiwiZW1haWwiOiJkaXdhczExODE1MUBnbWFpbC5jb20iLCJyb2xlIjoidXNlciIsInRoZW1lIjowLCJpYXQiOjE3MDUxMjM4MjJ9.DJkq9FG3xOxUFQTUYwpXP3cXaczpGwgC8xxSG0x77iw")
                .build();

        txtHelp2.setText("");

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    ImageChat.this.runOnUiThread(new Runnable() {
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
                                    String type = prompt.optString("type");
                                    String from = prompt.optString("from");
                                    String text = prompt.optString("text");
//                                    String chatId = prompt.optString("chatId");
//                                    String createdAt = prompt.optString("createdAt");
//                                    String updatedAt = prompt.optString("updatedAt");
                                    JSONArray images = prompt.getJSONArray("images");

                                    ArrayList<String> imageUrls = new ArrayList<>();

                                    if(Objects.requireNonNull(images).length() > 0) {
                                        for(int j = 0; j < Objects.requireNonNull(images).length(); j++) {
                                            String imageUrl = images.optString(j);
                                            imageUrls.add(imageUrl);
                                        }
                                    }

                                    Prompt newPrompt = new Prompt(_id, userId, type, from, text, imageUrls);
                                    prompts.add(newPrompt);
                                }

                                // show prompts after get
                                for(Prompt prompt: prompts) {
                                    if (Objects.equals(prompt.from, "user")) {
                                        addNewMessage(prompt.text, Objects.equals(prompt.from, "user"), new ArrayList<>());
                                    } else {
                                        addNewMessage(prompt.text, Objects.equals(prompt.from, "user"), prompt.images);
                                    }
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
        if (edtImgChat.getText().toString().trim() == "") return;

        RequestBody formBody = new FormBody.Builder()
//                .add("chatId", "")
                .add("prompt", edtImgChat.getText().toString().trim())
//                .add("password", "test")
                .build();

        txtHelp2.setText("Creating...");

        Request request = new Request.Builder()
                .url(url + "/image/create-prompt")
                .post(formBody)
                .addHeader("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NWEyMWZlYWQyYjU0MTAzZDBkNmRiMjkiLCJ1c2VybmFtZSI6Im5ha21pZXJzIiwiZW1haWwiOiJkaXdhczExODE1MUBnbWFpbC5jb20iLCJyb2xlIjoidXNlciIsInRoZW1lIjowLCJpYXQiOjE3MDUxMjM4MjJ9.DJkq9FG3xOxUFQTUYwpXP3cXaczpGwgC8xxSG0x77iw") // Add the authorization header with bearer token
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    ImageChat.this.runOnUiThread(new Runnable() {
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
//                                String text = prompt.optString("text");

                                // add new prompt to message list
                                Prompt newPrompt = new Prompt(_id, userId, type, from, text);
                                prompts.add(newPrompt);
                                addNewMessage(newPrompt.text, Objects.equals(newPrompt.from, "user"), new ArrayList<>());

                                // clear text chat
                                recyclerViewImage.scrollToPosition(messages.size() - 1);
                                edtImgChat.setText("");
                                txtHelp2.setText("");

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

    void CreateImages(String url) throws IOException {
        // prevent empty prompt
        if (edtImgChat.getText().toString().trim() == "") return;

        RequestBody formBody = new FormBody.Builder()
                .add("prompt", edtImgChat.getText().toString().trim())
                .add("amount", "1")
                .add("size", "256x256")
//                .add("chatId", "")
                .build();

        Request request = new Request.Builder()
                .url(url + "/image/create-images")
                .post(formBody)
                .addHeader("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NWEyMWZlYWQyYjU0MTAzZDBkNmRiMjkiLCJ1c2VybmFtZSI6Im5ha21pZXJzIiwiZW1haWwiOiJkaXdhczExODE1MUBnbWFpbC5jb20iLCJyb2xlIjoidXNlciIsInRoZW1lIjowLCJpYXQiOjE3MDUxMjM4MjJ9.DJkq9FG3xOxUFQTUYwpXP3cXaczpGwgC8xxSG0x77iw") // Add the authorization header with bearer token
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    ImageChat.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String jsonString = myResponse;

                            try
                            {
                                JSONObject json = new JSONObject(jsonString);
                                JSONObject response = json.getJSONObject("images");
                                // lấy ra mảng image urls
                                JSONArray images = response.getJSONArray("images");

                                if(Objects.requireNonNull(images).length() > 0) {
                                    ArrayList<String> imageUrls = new ArrayList<>();


                                    for(int i = 0; i < Objects.requireNonNull(images).length(); i++) {
                                        String imageUrl = images.optString(i);
                                        imageUrls.add(imageUrl);
                                    }

                                    addNewMessage(null, false, imageUrls);
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
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


    public void handleChangeChatMode(View view) {
        Intent intent = new Intent(ImageChat.this, TextChat.class);
        startActivity(intent);
    }

}