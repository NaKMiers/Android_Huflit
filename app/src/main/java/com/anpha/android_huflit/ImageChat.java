package com.anpha.android_huflit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anpha.android_huflit.ChatBox.ChatBoxAdapter;
import com.anpha.android_huflit.ChatBox.ItemChatBox;
import com.anpha.android_huflit.Message.ChatBox;
import com.anpha.android_huflit.Message.ImageMessage;
import com.anpha.android_huflit.Message.ImageMessageAdapter;
import com.anpha.android_huflit.Models.Prompt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.material.navigation.NavigationView;

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
    private RecyclerView recyclerViewImage, imageChatBox;
    //Khởi tạo adapter
    private ChatBoxAdapter boxAdapter;

    private ImageMessageAdapter adapter;

    private List<ItemChatBox> dataList;
    //Khởi tạo List tin nhắn
    private List<ImageMessage> messages;

    TextView txtHelp2,txtusername, txtAmount, txtSize,txtAdminimg,txtDevinfo;
    EditText edtImgChat;
    NavigationView navigationView;
    ImageView btnSendImg, navigationIcon, imgavatar, fbIcon, insIcon, twIcon, pinIcon, gitIcon, receivedImage, CrChat,imgAdminimg,imgDevinfo;
    PopupWindow popupWindow;
    Toolbar toolbarImage;
    Button btnSave,btnLogout;
    DrawerLayout drawerLayout;
    ImageButton btnPlus1, btnInCr, btnMinus1, btnDes;
    int currentValue = 1;
    Size[] ImageSize;
    String amount = "1";
    String size = "256x256";
    String token, userId;
    int OptionSizeIndex = 0;
    ArrayList<Prompt> prompts = new ArrayList<>();
    ArrayList<String> imageUrls  = new ArrayList<>();

    OkHttpClient client = new OkHttpClient();

    int chunkSize = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_chat);
        toolbarImage = findViewById(R.id.toolbarImage);



        //Nạp layout từ tệp popup_image_chat_menu
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_image_chat_menu, null);
        //Tạo popupWindow
        popupWindow = new PopupWindow(
                popupView,
                //Độ rộng
                LinearLayout.LayoutParams.MATCH_PARENT,
                //Độ cao
                LinearLayout.LayoutParams.WRAP_CONTENT,
                //Cho phép tương tác với các phần tử khác trên màn hình
                true
        );
        imgAdminimg=popupView.findViewById(R.id.imgAdminimg);
        txtAdminimg=popupView.findViewById(R.id.txtAdminimg);
         btnLogout=popupView.findViewById(R.id.btnLogOut);
        recyclerViewImage = findViewById(R.id.recyclerViewImage);
        txtusername=popupView.findViewById(R.id.txtusername);
        imgDevinfo=popupView.findViewById(R.id.imgDevinfo);
        txtDevinfo=popupView.findViewById(R.id.txtDevinfo);

        //Khởi tạo các biến
        imageChatBox = findViewById(R.id.imageChatBox);
        imageChatBox.setLayoutManager(new LinearLayoutManager(this));
        /// Khởi tạo danh sách dữ liệu chatBox
        dataList = new ArrayList<>();

        // Khởi tạo Adapter và gán cho RecyclerView
        boxAdapter = new ChatBoxAdapter(dataList, this);
        imageChatBox.setAdapter(boxAdapter);
        //Khởi tạo danh sách dữ liệu tin nhắn
        messages = new ArrayList<>();
        // Khởi tạo Adapter và gán cho RecyclerView
        adapter = new ImageMessageAdapter(messages);
        recyclerViewImage.setAdapter(adapter);
        txtHelp2 = findViewById(R.id.txtHelp2);
        edtImgChat = findViewById(R.id.edtImgChat);
        btnSendImg = findViewById(R.id.btnSendImg);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationIcon = findViewById(R.id.navigationIcon);
        imgavatar =popupView.findViewById(R.id.imgavatar);
        txtusername=popupView.findViewById(R.id.txtusername);
        txtAmount = findViewById(R.id.txtAmount);
        txtSize = findViewById(R.id.txtSize);
        btnDes = findViewById(R.id.btnDes);
        btnSave = findViewById(R.id.btnSave);
        btnInCr = findViewById(R.id.btnInCr);
        btnMinus1 = findViewById(R.id.btnMinus1);
        btnPlus1 = findViewById(R.id.btnPlus1);
        CrChat = findViewById(R.id.CrChat);
        fbIcon = popupView.findViewById(R.id.fbIcon);
        insIcon = popupView.findViewById(R.id.insIcon);
        twIcon = popupView.findViewById(R.id.twIcon);
        pinIcon = popupView.findViewById(R.id.pinIcon);
        gitIcon = popupView.findViewById(R.id.gitIcon);

        txtAmount.setText(amount);
        txtSize.setText(size);
        SharedPreferences mypreferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        String role = mypreferences.getString("role", "");

//        navigationIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(drawerLayout.isDrawerOpen(GravityCompat.START))
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                else {
//                    drawerLayout.openDrawer(GravityCompat.START);
//                }
//            }
//        });
        imgavatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageChat.this, ProfileView.class);
                startActivity(intent);
            }
        });
        imgDevinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageChat.this, DevInfo.class);
                startActivity(intent);
            }
        });
       txtDevinfo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(ImageChat.this, DevInfo.class);
               startActivity(intent);
           }
       });

        toolbarImage.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Nếu nhấn vào item có id iconMenu
                if (item.getItemId() == R.id.iconMenu) {
                    // Mở popupWindow ngay dưới iconMenu
                    popupWindow.showAsDropDown(toolbarImage.findViewById(R.id.iconMenu));
                    return true;
                }
                return false;
            }
        });
        txtAdminimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageChat.this,AdminUser.class);
                startActivity(intent);
            }
        });
        imgAdminimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageChat.this,AdminUser.class);
                startActivity(intent);
            }
        });
        // lấy dữ liệu từ SharedPreferces
        SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        String username = preferences.getString("username", ""); //lưu trữ tên người dùng
        token = preferences.getString("token", ""); //lưu trữ tên người dùng
        userId = preferences.getString("userId", ""); //lưu trữ tên người dùng

        amount = preferences.getString("amount", "1"); //lưu trữ tên người dùng
        size = preferences.getString("size", "256x256"); //lưu trữ tên người dùng
        txtusername.setText(username); // đặt tên người dùng trong textview

//        SharedPreferences.Editor editor = preferences.edit();
//        editor.apply();
        txtAmount.setText(amount);
        txtSize.setText(size);

        //Khởi tạo các biến
        imageChatBox = findViewById(R.id.imageChatBox);
        imageChatBox.setLayoutManager(new LinearLayoutManager(this));
        /// Khởi tạo danh sách dữ liệu chatBox
        dataList = new ArrayList<>();


        // Khởi tạo Adapter và gán cho RecyclerView
        boxAdapter = new ChatBoxAdapter(dataList, this);
        imageChatBox.setAdapter(boxAdapter);

        try {
            GetUserPrompts("https://android-huflit-server.vercel.app");
            GetImageBoxes("https://android-huflit-server.vercel.app");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Kiểm tra và ẩn/hiển thị các phần tử dựa trên vai trò của người dùng
        if (role.equals("admin")) {
            // Nếu người dùng có vai trò là admin, hiển thị các phần tử imgAdmin và txtAdmin
            imgAdminimg.setVisibility(View.VISIBLE);
            txtAdminimg.setVisibility(View.VISIBLE);
        } else {
            // Nếu không phải admin, ẩn đi các phần tử imgAdmin và txtAdmin
            imgAdminimg.setVisibility(View.GONE);
            txtAdminimg.setVisibility(View.GONE);
        }
//        btnMinus1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (currentValue > 1) {
//                    currentValue--;
//                    updateTextView();
//                }
//            }
//        });
//        btnPlus1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                currentValue++;
//                updateTextView();
//            }
//        });

        ImageSize = new Size[]{new Size(256, 256), new Size(512, 512), new Size(1024, 1024)};
//        btnInCr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (OptionSizeIndex < ImageSize.length - 1) {
//                    OptionSizeIndex = (OptionSizeIndex + 1) % ImageSize.length;
//                    updateTextView();
//                }
//            }
//        });
        txtusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageChat.this, ProfileView.class);
                startActivity(intent);
            }
        });
//        btnDes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (OptionSizeIndex > 0) {
//                    OptionSizeIndex = (OptionSizeIndex - 1) % ImageSize.length;
//                    updateTextView();
//                }
//            }
//        });
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    // Lấy giá trị từ txtAmount và txtSize
//                    amount = txtAmount.getText().toString();
//                    size = txtSize.getText().toString();
//                    CreateImages("https://android-huflit-server.vercel.app");
//                    saveValuesToSharedPreferences();
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        CrChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CreateImageBox();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOutUser();
            }
        });

    }
    public void logOutUser(View view) {
        LogOutUser();
    }
    private void addBox(ItemChatBox box) {
        // Thêm các item vào danh sách dữ liệu
        dataList.add(box);
        boxAdapter.notifyItemInserted(dataList.size() - 1);

    }
    private void requireAuth() {
        SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        String userId = preferences.getString("userId", ""); //lưu trữ tên người dùng
        if (userId == null || userId == "") {
            Intent intent = new Intent(ImageChat.this, Login.class);
            startActivity(intent);
            finish();
        }
    }
    private void GetImageBoxes(String url){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url + "/box/get-boxes/image")
                .addHeader("Authorization", "Bearer " + token) // Add the authorization header with bearer token
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(ImageChat.this, "Get boxes failure!!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    final String myResponse = response.body().string();
                    ImageChat.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("asdasdasd", myResponse);

                            try {
                                JSONObject json = new JSONObject(myResponse);
                                JSONArray jsonArray = json.getJSONArray("boxes");

                                for(int i = 0; i < Objects.requireNonNull(jsonArray).length(); i++){
                                    JSONObject box = jsonArray.optJSONObject(i);

                                    // get values
                                    String _id = box.optString("_id");
                                    String userId = box.optString("userId");
//                                   String type = prompt.optString("type");
//                                   String from = prompt.optString("from");
                                    String title = box.optString("title");
//                                    String createdAt = box.optString("createdAt");
//                                    String updatedAt = box.optString("updatedAt");

                                    // add each prompt to prompt list
                                    ItemChatBox newBox = new ItemChatBox(_id, userId, "image", title);
                                    addBox(newBox);
                                }
                            } catch (JSONException e)
                            {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            }
        });
    }
    private void LogOutUser() {
        SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit(); // Sửa lỗi gán lại biến preferences
//        editor.remove("token");
        editor.clear();
        editor.apply();
        Intent intent = new Intent(ImageChat.this, Login.class);
//         Xóa tất cả các hoạt động trước đó khỏi ngăn xếp và chuyển người dùng đến màn hình đăng nhập
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    private void CreateImageBox() throws IOException {
        OkHttpClient client = new OkHttpClient();

        String url = "https://android-huflit-server.vercel.app/box/create-box/image";

        RequestBody formBody = new FormBody.Builder()
                .add("prompt", "Nothing")
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

                    ImageChat.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject(myResponse);
                                JSONObject boxJSON = json.getJSONObject("newBox");

                                // get prompt values
                                String _id = boxJSON.optString("_id");
                                String userId = boxJSON.optString("userId");
                                String title = boxJSON.optString("title");
//                                String createdAt = boxJSON.optString("createdAt");
//                                String updatedAt = boxJSON.optString("updatedAt");

                                ItemChatBox newBox = new ItemChatBox(_id, userId, "image", title);
                                addBox(newBox);
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


    private void saveValuesToSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("amount", txtAmount.getText().toString());
        editor.putString("size", txtSize.getText().toString());

        // Lưu thay đổi
        editor.apply();
    }

    private void restoreValuesFromSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String savedAmount = preferences.getString("amount", "");
        String savedSize = preferences.getString("size", "");

        // Set giá trị cho txtAmount và txtSize
        txtAmount.setText(savedAmount);
        txtSize.setText(savedSize);
    }

    //Update lại số lượng và size ảnh
    private void updateTextView() {
        txtAmount.setText(String.valueOf(currentValue));
        Size selectedSize = ImageSize[OptionSizeIndex];
        txtSize.setText(selectedSize.getWidth() + "x" + selectedSize.getHeight());

//        try {
//            GetUserPrompts("https://android-huflit-server.vercel.app");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void handleSentImagePrompt(View view) throws IOException {
        String messageText = edtImgChat.getText().toString().trim();
        if (!messageText.isEmpty()) {
            txtHelp2.setText("");
//            edtImgChat.setText("");

            CreatePrompt("https://android-huflit-server.vercel.app");

        }
    }

    public void handleSendChatPrompt(View view) throws IOException {
        String messageText = edtImgChat.getText().toString().trim();
        if (!messageText.isEmpty()) {
            txtHelp2.setText("");

            CreatePrompt("https://android-huflit-server.vercel.app");
            CreateImages("https://android-huflit-server.vercel.app");
        }
    }

    private void addNewMessage(String text, boolean isFromUser) {
        // Khởi tạo tin nhắn gửi với constructor 2 tham số là text (chữ) và isFromUser (Do người dùng gửi)
        ImageMessage sentMessage = new ImageMessage(text, isFromUser);
        // Thêm tin nhắn gửi vào cuối danh sách
        messages.add(sentMessage);
        // Thông báo cho Adapter biết rằng có một mục mới được thêm vào cuối danh sách
        adapter.notifyItemInserted(messages.size() - 1);
    }

    private void addNewAIMessage(boolean sentByUser, ArrayList<String> imageUrls) {
        //Khởi tạo tin nhắn nhận từ AI với senbyUser là false (không do người dùng gửi) và link ảnh kiểu String
        ImageMessage receivedImage = new ImageMessage(sentByUser, imageUrls);
        // Thêm tin nhắn nhận vào cuối danh sách
        messages.add(receivedImage);
        // Thông báo cho Adapter biết rằng có một mục mới được thêm vào cuối danh sách
        adapter.notifyItemInserted(messages.size() - 1);
    }

    private void GetUserPrompts(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url + "/image/get-prompts")
                .addHeader("Authorization", "Bearer " + token) // Add the authorization header with bearer token
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
                                JSONArray jsonArray = json.getJSONArray("prompts");
//
                                for (int i = 0; i < Objects.requireNonNull(jsonArray).length(); i++) {
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
                                    JSONArray imagesArray = prompt.optJSONArray("images");

                                    // Convert JSONArray to ArrayList<String>
                                    ArrayList<String> images = new ArrayList<>();
                                    if (imagesArray != null) {
                                        for (int j = 0; j < imagesArray.length(); j++) {
                                            images.add(imagesArray.optString(j));
                                        }
                                    }

                                    // add each prompt to prompt list
                                    Prompt newPrompt = new Prompt(_id, userId, type, from, text, images);
                                    prompts.add(newPrompt);
                                }
//
                                // show prompts after get
                                for (Prompt prompt : prompts) {
                                    if (Objects.equals(prompt.from, "user")) {
                                        addNewMessage(prompt.text, true);
                                    } else {
                                        ArrayList<String> imageUrls = prompt.getImages();
                                        for (int i = 0; i < imageUrls.size(); i += chunkSize) {
                                            ArrayList<String> imgUrls = new ArrayList<>();

                                            for (int j = 0; j < chunkSize; j++) {
                                                if (i + j < imageUrls.size()) {
                                                    imgUrls.add(imageUrls.get(j));
                                                }
                                            }

                                            addNewAIMessage(false, imgUrls);
                                        }
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

        Request request = new Request.Builder()
                .url(url + "/image/create-prompt")
                .post(formBody)
                .addHeader("Authorization", "Bearer " + token) // Add the authorization header with bearer token
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
                                addNewMessage(newPrompt.text, Objects.equals(newPrompt.from, "user"));

                                // Cuộn xuống dưới cùng khi có tin nhắn mới
                                recyclerViewImage.scrollToPosition(messages.size() - 1);
                                // clear text chat
                                edtImgChat.setText("");
                                CreateImages("https://android-huflit-server.vercel.app");
                            } catch (JSONException  | IOException e ) {
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
//        txtHelp2.setText("creating...");


        SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        String amount = preferences.getString("amount", "1"); //lưu trữ tên người dùng
        String size = preferences.getString("size", "256x256"); //lưu trữ tên người dùng

        RequestBody formBody = new FormBody.Builder()
                .add("prompt", edtImgChat.getText().toString().trim())
                .add("amount", amount)
                .add("size", size)
//                .add("chatId", "")
                .build();

        Request request = new Request.Builder()
                .url(url + "/image/create-images")
                .post(formBody)
                .addHeader("Authorization", "Bearer " + token) // Add the authorization header with bearer token
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

                            try {
                                JSONObject json = new JSONObject(jsonString);
                                JSONObject response = json.getJSONObject("images");
                                // lấy ra mảng image urls
                                JSONArray images = response.getJSONArray("images");



                                // ở chỗ này, thay vì chỉ dùng images[0] thì hãy dùng vòng lặp trong trường hợp có nhiều hơn 1 image
//                                if(Objects.requireNonNull(images).length() > 0) {
//                                    String imageUrl = images.optString(0);
//
//                                    // tempImageView chỉ là hiển thị tạm thời thôi, m tự chỉnh cho nó hiển thị ở đúng vị trí
//                                    addnewAIMessage(false,imageUrl);
//                                }
                                Toast.makeText(ImageChat.this, String.valueOf(Objects.requireNonNull(images).length()), Toast.LENGTH_LONG).show();

                                for (int i = 0; i < Objects.requireNonNull(images).length(); i+= chunkSize) {
                                    ArrayList<String> imgUrls = new ArrayList<>();

                                    for (int j = 0; j < chunkSize; j++) {
                                        if (i + j < Objects.requireNonNull(images).length()) {
                                            imgUrls.add(images.optString(i));
                                        }
                                    }

                                    addNewAIMessage(false, imgUrls);
                                }
                            } catch (JSONException e) {
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


    //Chuyển qua Textchat
    public void handleChangeChatMode(View view) {
        Intent intent = new Intent(ImageChat.this, TextChat.class);
        startActivity(intent);
    }

    //Mở sidebar
    public void openSidebarImage(View view) {
        //Nếu đang mở rồi
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            //đóng sidebar
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            // Ngược lại mở sidebar
            drawerLayout.openDrawer(GravityCompat.START);
    }

    //Giảm số lượng ảnh
    public void desAmount(View view) {
        if (currentValue > 1) {
            currentValue--;
            updateTextView();
        }
    }

    //Tăng số lượng ảnh
    public void incrAmount(View view) {
        currentValue++;
        updateTextView();
    }

    //Giảm size ảnh
    public void decSize(View view) {
        if (OptionSizeIndex > 0) {
            OptionSizeIndex = (OptionSizeIndex - 1) % ImageSize.length;
            updateTextView();
        }
    }

    //Tăng size ảnh
    public void incrSize(View view) {
        if (OptionSizeIndex < ImageSize.length - 1) {
            OptionSizeIndex = (OptionSizeIndex + 1) % ImageSize.length;
            updateTextView();
        }
    }

    //Lưu thuộc tính của ảnh
    public void saveImageAttribute(View view) {
        try {
            // Lấy giá trị từ txtAmount và txtSize
            amount = txtAmount.getText().toString();
            size = txtSize.getText().toString();

            SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("amount", amount);
            editor.putString("size", size);
            editor.apply();

////            CreateImages("https://android-huflit-server.vercel.app");
//            saveValuesToSharedPreferences();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //Hàm mở trang web tương ứng
    private void openWebPage(String url) {
        //Tạp Uri từ địa chỉ url (Uri là chuỗi đại diện cho địa chỉ hoặc tài nguyên Internet)
        Uri webpage = Uri.parse(url);
        //Yêu cầu hệ thống mở dữ liệu bằng cách sử dụng ứng dụng mặc định của hệ thống (trình duyệt web)
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        //Nếu có thì sẽ gửi intent và mở trang web
        startActivity(intent);

    }

    //Nhấn vào icon Facebook
    public void fbLink(View view) {
        openWebPage("https://facebook.com");
    }
    //Nhấn vào icon Instagram
    public void insLink(View view) {
        openWebPage("https://instagram.com");
    }
    //Nhấn vào icon Twitter
    public void twLink(View view) {
        openWebPage("https://twitter.com");
    }
    //Nhấn vào icon Pinterest
    public void pinLink(View view) {
        openWebPage("https://pinterest.com");
    }
    //Nhấn vào icon Github
    public void gitLink(View view) {
        openWebPage("https://github.com");
    }

}
