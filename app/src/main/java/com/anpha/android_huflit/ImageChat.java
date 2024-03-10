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


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

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
    private RecyclerView recyclerViewImage;
    //Khởi tạo adapter
    private ImageMessageAdapter adapter;
    //Khởi tạo List tin nhắn
    private List<ImageMessage> messages;

    TextView txtHelp2,txtusername, txtAmount, txtSize;
    EditText edtImgChat;
    NavigationView navigationView;
    ImageView btnSendImg, navigationIcon, imgavatar, fbIcon, insIcon, twIcon, pinIcon, gitIcon, receivedImage, CrChat;
    PopupWindow popupWindow;
    Toolbar toolbarImage;
    Button btnSave;
    DrawerLayout drawerLayout;
    ImageButton btnPlus1, btnInCr, btnMinus1, btnDes;
    int currentValue = 1;
    Size[] ImageSize;
    String amount = "1";
    String size = "256x256";
    String token;
    int OptionSizeIndex = 0;
    ArrayList<Prompt> prompts = new ArrayList<>();
    OkHttpClient client = new OkHttpClient();
    private ArrayList<String> images;

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

        recyclerViewImage = findViewById(R.id.recyclerViewImage);
        txtusername=popupView.findViewById(R.id.txtusername);

        //Khởi tạo các biến
        messages = new ArrayList<>();
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
        restoreValuesFromSharedPreferences();

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
        imgavatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageChat.this, ProfileView.class);
                startActivity(intent);
            }
        });
//
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

        try {
            GetUserPrompts("https://android-huflit-server.vercel.app");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

        ImageSize = new Size[]{new Size(256, 256), new Size(512, 512), new Size(1024, 1024)};
        btnInCr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OptionSizeIndex < ImageSize.length - 1) {
                    OptionSizeIndex = (OptionSizeIndex + 1) % ImageSize.length;
                    updateTextView();
                }
            }
        });
        txtusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageChat.this, ProfileView.class);
                startActivity(intent);
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
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Lấy giá trị từ txtAmount và txtSize
                    amount = txtAmount.getText().toString();
                    size = txtSize.getText().toString();
                    CreateImages("https://android-huflit-server.vercel.app");
                    saveValuesToSharedPreferences();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        CrChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatChat();
            }
        });
        // lấy dữ liệu từ SharedPreferces
        SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        String username = preferences.getString("username", ""); //lưu trữ tên người dùng
        token= preferences.getString("token", ""); //lưu trữ tên người dùng
        txtusername.setText(username); // đặt tên người dùng trong textview
    }

    private void getBox(String type){
        OkHttpClient client = new OkHttpClient();

        String url ="https://android-huflit-server.vercel.app/box/get-box/" +type;

        Request request = new Request.Builder()
                .url(url).get().build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String responseData = response.body().string();
                   final  String myResponse = response.body().string();
                   ImageChat.this.runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           try {
                               JSONObject json = new JSONObject(myResponse);
                               JSONArray jsonArray = json.getJSONArray("prompts");
                               for(int i = 0; i < Objects.requireNonNull(jsonArray).length(); i++){
                                   JSONObject prompt = jsonArray.optJSONObject(i);

                                   // get values
                                   String _id = prompt.optString("_id");
                                   String userId = prompt.optString("userId");
                                    String chatId = prompt.optString("chatId");
//                                   String type = prompt.optString("type");
//                                   String from = prompt.optString("from");
//                                   String text = prompt.optString("text");
                                    String createdAt = prompt.optString("createdAt");
                                    String updatedAt = prompt.optString("updatedAt");
                                    JSONArray images = prompt.optJSONArray("images");

                                   // add each prompt to prompt list
                                   Prompt newPrompt = new Prompt(_id,chatId, userId, createdAt, updatedAt,images);
                                   prompts.add(newPrompt);
                               }
                               // show prompts after get
                               for(Prompt prompt: prompts) {
                                   Log.d("Type", prompt.type);
                                   addNewMessage(prompt.text, Objects.equals(prompt.from, "user"));
                               }

                           }catch (JSONException e)
                           {
                               throw new RuntimeException(e);
                           }
                       }
                   });

                }
                else {

                }
            }
        });
    }
    private void CreatChat(){
        OkHttpClient client = new OkHttpClient();

        String url ="https://android-huflit-server.vercel.app/box/create-box/:type";

        RequestBody requestBody = RequestBody.create(null, new byte[0]);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    ChatBox newChatBox = new ChatBox();
                }
                else {

                }
            }
        });

        // lấy dữ liệu từ SharedPreferces
        SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        String username = preferences.getString("username", ""); //lưu trữ tên người dùng
        txtusername.setText(username); // đặt tên người dùng trong textview
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

        try {
            GetUserPrompts("https://android-huflit-server.vercel.app");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void handleSentImagePrompt(View view) throws IOException {
        String messageText = edtImgChat.getText().toString().trim();
        if (!messageText.isEmpty()) {
            txtHelp2.setText("");
//            edtImgChat.setText("");
            CreatePrompt("https://android-huflit-server.vercel.app");
            CreateImages("https://android-huflit-server.vercel.app");
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

    private void addnewAIMessage(boolean sentByUser, String imageURL) {
        //Khởi tạo tin nhắn nhận từ AI với senbyUser là false (không do người dùng gửi) và link ảnh kiểu String
        ImageMessage receivedImage = new ImageMessage(false, imageURL);
        // Thêm tin nhắn nhận vào cuối danh sách
        messages.add(receivedImage);
        // Thông báo cho Adapter biết rằng có một mục mới được thêm vào cuối danh sách
        adapter.notifyItemInserted(messages.size() - 1);
    }

    private void GetUserPrompts(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url + "/image/get-prompts")
                .addHeader("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NWQ1NmVmZWViZTdkNjA2MDk0MTBkNjgiLCJ1c2VybmFtZSI6ImdiYW8xMjMzIiwiZW1haWwiOiJnYmFvQGZhc2ZkYXMiLCJyb2xlIjoidXNlciIsInRoZW1lIjowLCJpYXQiOjE3MDg0ODY0NDN9.S31Nw2bqoH2YWkoc0YD-SC0fF4EKpvRiMOgjqPSDzl0") // Add the authorization header with bearer token
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
                                    String chatId = prompt.optString("chatId");
                                    String createdAt = prompt.optString("createdAt");
                                    String updatedAt = prompt.optString("updatedAt");
                                    JSONArray imagesArray = prompt.optJSONArray("images");

                                    // Convert JSONArray to List<String>
                                    ArrayList<String> images = new ArrayList<>();
                                    if (imagesArray != null) {
                                        for (int j = 0; j < imagesArray.length(); j++) {
                                            images.add(imagesArray.optString(j));
                                        }
                                    }

                                    // add each prompt to prompt list
                                    Prompt newPrompt = new Prompt(_id, userId, type, from, text);
                                    prompts.add(newPrompt);
                                }

                                // show prompts after get
                                for (Prompt prompt : prompts) {
                                    Log.d("Type", prompt.type);
                                    if (Objects.equals(prompt.from, "user")) {
                                        addNewMessage(prompt.text, true);
                                    } else {
                                        Log.d("Image: ", prompt.images.get(0));
                                        addnewAIMessage(false, "https://image.lexica.art/full_webp/2a010649-554e-4628-90b7-919165968082");
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
                .addHeader("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NWQ1NmVmZWViZTdkNjA2MDk0MTBkNjgiLCJ1c2VybmFtZSI6ImdiYW8xMjMzIiwiZW1haWwiOiJnYmFvQGZhc2ZkYXMiLCJyb2xlIjoidXNlciIsInRoZW1lIjowLCJpYXQiOjE3MDg0ODY0NDN9.S31Nw2bqoH2YWkoc0YD-SC0fF4EKpvRiMOgjqPSDzl0") // Add the authorization header with bearer token
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
        txtHelp2.setText("creating...");

        RequestBody formBody = new FormBody.Builder()
                .add("prompt", edtImgChat.getText().toString().trim())
                .add("amount", amount)
                .add("size", size)
//                .add("chatId", "")
                .build();

        Request request = new Request.Builder()
                .url(url + "/image/create-images")
                .post(formBody)
                .addHeader("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NWQ1NmVmZWViZTdkNjA2MDk0MTBkNjgiLCJ1c2VybmFtZSI6ImdiYW8xMjMzIiwiZW1haWwiOiJnYmFvQGZhc2ZkYXMiLCJyb2xlIjoidXNlciIsInRoZW1lIjowLCJpYXQiOjE3MDg0ODY0NDN9.S31Nw2bqoH2YWkoc0YD-SC0fF4EKpvRiMOgjqPSDzl0") // Add the authorization header with bearer token
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
                            // txtHelp2.setText(jsonString);

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
                                if (Objects.requireNonNull(images).length() > 0) {
                                    for (int i = 0; i < images.length(); i++) {
                                        String imageUrl = images.optString(i);
                                        if (i >= 0) {
                                            txtHelp2.setText("");
                                            ;
                                        }
                                        // sentByUser false là do AI gửi, link ảnh kiểu String imageUrl
                                        addnewAIMessage(false, imageUrl);
                                    }
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
            CreateImages("https://android-huflit-server.vercel.app");
            saveValuesToSharedPreferences();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    //Hàm mở trang web tương ứng
    private void openWebPage(String url) {
        //Tạp Uri từ địa chỉ url (Uri là chuỗi đại diện cho địa chỉ hoặc tài nguyên Internet)
        Uri webpage = Uri.parse(url);
        //Yêu cầu hệ thống mở dữ liệu bằng cách sử dụng ứng dụng mặc định của hệ thống (trình duyệt web)
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        //Kiểm tra xem có ứng dụng nào để mở dữ liệu được cung cấp hay không
        if (intent.resolveActivity(getPackageManager()) != null) {
            //Nếu có thì sẽ gửi intent và mở trang web
            startActivity(intent);
        }
    }
}