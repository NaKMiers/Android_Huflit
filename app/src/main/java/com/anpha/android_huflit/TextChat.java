package com.anpha.android_huflit;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.Toast;

import com.anpha.android_huflit.ChatBox.ChatBoxAdapter;
import com.anpha.android_huflit.ChatBox.ItemChatBox;
import com.anpha.android_huflit.Message.Message;
import com.anpha.android_huflit.Message.MessageAdapter;
import com.anpha.android_huflit.Models.Prompt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.SplittableRandom;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TextChat extends AppCompatActivity {
    private RecyclerView recyclerView, chatBox;
    private MessageAdapter adapter;

    private ChatBoxAdapter boxAdapter;

    private List<ItemChatBox> dataList;
    private List<Message> messages;
    PopupWindow popupWindow;
    Toolbar toolbarChat;

    ImageView btnSend, navigationIcon,imgavatar, fbIcon, insIcon, twIcon, pinIcon, gitIcon,imgChangetheme,imgDevinfo,imgAdmin;
    EditText edtTextChat;

    DrawerLayout drawerLayout;
    TextView txtHelp1, txtMode, txtusername,txtChangetheme,txtDevinfo,txtAdmin,txtHiuser;
     Button btnLogOut;
    ArrayList<Prompt> prompts = new ArrayList<>();

    OkHttpClient client = new OkHttpClient();

    String token, userId;

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
        chatBox = findViewById(R.id.chatBox);
        txtHiuser=findViewById(R.id.txtHiuser);




        // Khởi tạo danh sách tin nhắn
        messages = new ArrayList<>();
        // Khởi tạo adapter mới kết nối dữ liệu từ danh sách tin nhắn
        adapter = new MessageAdapter(messages);
        // Gán adapter vào recyclerView
        recyclerView.setAdapter(adapter);
        // Sắp xếp các mục tin nhắn dọc
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //Nạp layout từ tệp popup_chat_menu
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_chat_menu, null);
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
        btnLogOut=popupView.findViewById(R.id.btnLogOut);
        txtusername = popupView.findViewById(R.id.txtusername);
        imgavatar=popupView.findViewById(R.id.imgavatar);
        fbIcon = popupView.findViewById(R.id.fbIcon);
        insIcon = popupView.findViewById(R.id.insIcon);
        twIcon = popupView.findViewById(R.id.twIcon);
        pinIcon = popupView.findViewById(R.id.pinIcon);
        gitIcon = popupView.findViewById(R.id.gitIcon);
        txtChangetheme=popupView.findViewById(R.id.txtChangetheme);
        imgChangetheme=popupView.findViewById(R.id.imgChangetheme);
        txtDevinfo=popupView.findViewById(R.id.txtDevinfo);
        imgDevinfo=popupView.findViewById(R.id.imgDevinfo);
        imgAdmin=popupView.findViewById(R.id.imgAdmin);
        txtAdmin=popupView.findViewById(R.id.txtAdmin);

        //Nhận sự kiện nhập liệu
        edtTextChat.requestFocus();
        //Thiết lập con trỏ ở cuối văn bản EditText
        edtTextChat.setSelection(edtTextChat.getText().length());
        // Lấy vai trò của người dùng từ Shared Preferences
        SharedPreferences mypreferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        String role = mypreferences.getString("role", "");

        // Kiểm tra và ẩn/hiển thị các phần tử dựa trên vai trò của người dùng
        if (role.equals("admin")) {
            // Nếu người dùng có vai trò là admin, hiển thị các phần tử imgAdmin và txtAdmin
            imgAdmin.setVisibility(View.VISIBLE);
            txtAdmin.setVisibility(View.VISIBLE);
        } else {
            // Nếu không phải admin, ẩn đi các phần tử imgAdmin và txtAdmin
            imgAdmin.setVisibility(View.GONE);
            txtAdmin.setVisibility(View.GONE);
        }

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
        imgavatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TextChat.this,ProfileView.class);
                startActivity(intent);
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
          txtChangetheme.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(TextChat.this, ThemeView.class);
                  startActivity(intent);
              }
          });
          imgChangetheme.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(TextChat.this, ThemeView.class);
                  startActivity(intent);
              }
          });
          imgDevinfo.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(TextChat.this, DevInfo.class);
                  startActivity(intent);
              }
          });
          txtDevinfo.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(TextChat.this, DevInfo.class);
                  startActivity(intent);
              }
          });




        //Mở popupWindow (chỉ set sự kiện được trong java)
        toolbarChat.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Nếu nhấn vào item có id iconMenu
                if(item.getItemId() == R.id.iconMenu)
                {
                    // Mở popupWindow ngay dưới iconMenu
                    popupWindow.showAsDropDown(toolbarChat.findViewById(R.id.iconMenu));
                    return true;
                }
                return false;
            }
        });


        // check
        SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        String username = preferences.getString("username", ""); //lưu trữ tên người dùng
        token = preferences.getString("token", ""); //lưu trữ tên người dùng
        userId = preferences.getString("userId", ""); //lưu trữ tên người dùng
        txtusername.setText(username); // đặt tên người dùng trong textview
        txtHiuser.setText(" Hi " +username );


        chatBox.setLayoutManager(new LinearLayoutManager(this));
        // Khởi tạo danh sách dữ liệu
        dataList = new ArrayList<>();
        // Khởi tạo Adapter và gán cho RecyclerView
        boxAdapter = new ChatBoxAdapter(dataList, this);
        chatBox.setAdapter(boxAdapter);

        // check auth login
        requireAuth();

        try {
            GetUserPrompts("https://android-huflit-server.vercel.app");
            GetChatBoxes("https://android-huflit-server.vercel.app");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void requireAuth() {
        SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        String userId = preferences.getString("userId", ""); //lưu trữ tên người dùng
        if (userId == null || userId == "") {
            Intent intent = new Intent(TextChat.this, Login.class);
            startActivity(intent);
//            finish();
//            return;
        }
    }

    private void LogOutUser() {
        SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit(); // Sửa lỗi gán lại biến preferences
//        editor.remove("token");
        editor.clear();
        editor.apply();
        Intent intent = new Intent(TextChat.this, TextChat.class);
//         Xóa tất cả các hoạt động trước đó khỏi ngăn xếp và chuyển người dùng đến màn hình đăng nhập
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void handleSendChatPrompt(View view) throws IOException {
        SharedPreferences preferences = getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        String messageText = edtTextChat.getText().toString().trim();
        if (token.isEmpty()) {
            Intent intent = new Intent(TextChat.this, Login.class);
            startActivity(intent);
            return; // Kết thúc phương thức để không thực hiện gửi tin nhắn nếu chưa đăng nhập
        }

        if (!messageText.isEmpty()) {
            txtHelp1.setText("");

            CreatePrompt("https://android-huflit-server.vercel.app");
            CreateCompletion("https://android-huflit-server.vercel.app");
        }
    }

    private void addNewMessage(String text, boolean isFromUser) {
        //Khởi tạo lớp Message sentMessage với tham số là text kiểu chuỗi và isFromUser(do người dùng gửi)
        Message sentMessage = new Message(text, isFromUser);
        // Thêm tin nhắn gửi vào cuối danh sách
        messages.add(sentMessage);
        // Thông báo cho Adapter biết rằng có một mục mới được thêm vào cuối danh sách
        adapter.notifyItemInserted(messages.size() - 1);
    }

    private void addBox(ItemChatBox box) {
        // Thêm các item vào danh sách dữ liệu
        dataList.add(box);
        boxAdapter.notifyItemInserted(dataList.size() - 1);
    }

    void GetUserPrompts(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url + "/chat/get-prompts")
                .addHeader("Authorization", "Bearer " + token) // Add the authorization header with bearer token
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

//                                     add each prompt to prompt list
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

    private void GetChatBoxes(String url){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url + "/box/get-boxes/chat")
                .addHeader("Authorization", "Bearer " + token) // Add the authorization header with bearer token
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(TextChat.this, "Get boxes failure!!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    final String myResponse = response.body().string();
                    TextChat.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
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
                                    ItemChatBox newBox = new ItemChatBox(_id, userId, "chat", title);
                                    addBox(newBox);
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
                .addHeader("Authorization", "Bearer " + token)
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


                                // Cuộn xuống dưới cùng khi có tin nhắn mới
                                recyclerView.scrollToPosition(messages.size() - 1);
                                // clear text chat
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
                .addHeader("Authorization", "Bearer " + token) // Add the authorization header with bearer token
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    TextChat.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtHelp1.setText(myResponse);

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

                                //Cuộn xuống khi có tin nhắn mới
                                recyclerView.scrollToPosition(messages.size() - 1);
                                // clear text chat
                                edtTextChat.setText("");
                                // clear dòng chữ "How can I help you?"
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

    //Chuyển qua ImageChat
    public void handleChangeToImageMode(View view) {
        Intent intent = new Intent(TextChat.this,ImageChat.class);
        startActivity(intent);
        finish();
    }

    //Mở sidebar
    public void openSidebar(View view) {
        //Nếu sidebar đang mở
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            // Đóng lại
            drawerLayout.closeDrawer(GravityCompat.START);
        else {
            // Còn không thì mở sidebar
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    //Đăng xuất
    public void logOutUser(View view) {
        LogOutUser();
    }

    //Đi tới profileView
    public void toProfileView(View view) {
        Intent intent = new Intent(TextChat.this, ProfileView.class);
        startActivity(intent);
    }

//    Nhấn vào icon Facebook
    public void toFb(View view) {
        openWebPage("https://facebook.com");
    }
    //Nhấn vào icon Instagram
    public void toIns(View view) {
        openWebPage("https://instagram.com");
    }
    //Nhấn vào icon Tw
    public void toTw(View view) {
        openWebPage("https://twitter.com");
    }
    //Nhấn vào icon Pinterest
    public void toPin(View view) {
        openWebPage("https://pinterest.com");
    }
    //Nhấn vào icon Github
    public void toGit(View view) {
        openWebPage("https://github.com");
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
}