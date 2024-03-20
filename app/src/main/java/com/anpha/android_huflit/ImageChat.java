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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.LongDef;
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
    // List
    private RecyclerView recyclerViewImage;
    private ListView imageChatBox;

    //Khởi tạo adapter
    private ChatBoxAdapter boxAdapter;
    private ImageMessageAdapter adapter;
    private ArrayList<ItemChatBox> boxes;
    //Khởi tạo List tin nhắn
    private List<ImageMessage> messages;
    ArrayList<Prompt> prompts = new ArrayList<>();
    ArrayList<String> imageUrls  = new ArrayList<>();

    // Elements
    TextView txtHelp2,txtusername, txtAmount, txtSize,txtAdminimg,txtDevinfo;
    EditText edtImgChat;
    NavigationView navigationView;
    ImageView btnSendImg, navigationIcon, imgavatar, fbIcon, insIcon, twIcon, pinIcon, gitIcon, receivedImage,imgAdminimg,imgDevinfo;
    PopupWindow popupWindow;
    Toolbar toolbarImage;
    Button btnSave,btnLogout;
    DrawerLayout drawerLayout;
    ImageButton btnPlus1, btnInCr, btnMinus1, btnDes;
    ProgressBar loadingMessage;


    // values
    int currentValue = 1;
    Size[] ImageSize;
    String amount = "1";
    String size = "256x256";
    String token, userId;
    int OptionSizeIndex = 0;
    int chunkSize = 2;
    String boxId;
    String API = "https://android-huflit-server.vercel.app";

    // library tools
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_chat);

        requireAuth();

        // mapping
        mapping();

        // events
        addEvents();

        // Khởi tạo Adapter và gán cho RecyclerView
        messages = new ArrayList<>();
        adapter = new ImageMessageAdapter(messages);
        recyclerViewImage.setAdapter(adapter);

        // show boxes
        boxes = new ArrayList<>();
        boxAdapter = new ChatBoxAdapter(ImageChat.this, R.layout.item_chatbox_layout, boxes);
        imageChatBox.setAdapter(boxAdapter);

        // get global data
        SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        String role = preferences.getString("role", "");
        String username = preferences.getString("username", ""); //lưu trữ tên người dùng
        token = preferences.getString("token", ""); //lưu trữ tên người dùng
        userId = preferences.getString("userId", ""); //lưu trữ tên người dùng
        String avatar = preferences.getString("avatar", "");
        Picasso.get().load(API + avatar).into(imgavatar);

        ImageSize = new Size[]{new Size(256, 256), new Size(512, 512), new Size(1024, 1024)};
        amount = preferences.getString("amount", "1"); //lưu trữ tên người dùng
        size = preferences.getString("size", "256x256"); //lưu trữ tên người dùng
        txtusername.setText(username); // đặt tên người dùng trong textview

        txtAmount.setText(amount);
        txtSize.setText(size);

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

        // Item selected
        imageChatBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemChatBox box =  boxes.get(position);
                boxId = box.get_id();

                Log.d("ID-------", boxId);

                prompts.clear();
                messages.clear();
                adapter.notifyDataSetChanged();
                setLoading(false);

                // close sidebar
                drawerLayout.closeDrawer(GravityCompat.START);

                try {
                    GetUserPrompts(API + "/image/get-prompts/" + boxId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        // Click on edit button
        boxAdapter.setEditClickListener(new ChatBoxAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(ItemChatBox itemChatBox) {
                // Lấy vị trí của item được nhấn
                int position = boxes.indexOf(itemChatBox);

                // Kiểm tra vị trí có hợp lệ không
                if (position != -1) {
                    // Lấy convertView của item được nhấn
                    View view = imageChatBox.getChildAt(position - imageChatBox.getFirstVisiblePosition());
                    if (view != null) {
                        // Tìm TextView hiện tại và EditText mới
                        TextView textView = view.findViewById(R.id.chatBox);
                        EditText editText = new EditText(ImageChat.this);

                        // Lấy văn bản hiện tại từ TextView
                        String currentText = textView.getText().toString();

                        // Đặt văn bản hiện tại vào EditText
                        editText.setText(currentText);

                        // Thay thế TextView bằng EditText trong LinearLayout
                        ViewGroup parentLayout = (ViewGroup) textView.getParent();
                        int index = parentLayout.indexOfChild(textView);
                        parentLayout.removeView(textView);

                        parentLayout.addView(editText, index, new ViewGroup.LayoutParams(350, ViewGroup.LayoutParams.WRAP_CONTENT));

                        // Thay đổi văn bản của nút chỉnh sửa thành "Save"
                        ImageView editBtn = view.findViewById(R.id.btnedit);
                        editBtn.setImageResource(R.drawable.save);

                        // Focus vào EditText và di chuyển con trỏ đến cuối văn bản
                        editText.requestFocus();
                        editText.setSelection(editText.getText().length());

                        editBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("Position------", position + "");
                                Log.d("Box ID", itemChatBox.get_id());
                                Log.d("New Value", editText.getText().toString());

                                UpdateBoxName(API + "/box/edit-box/" + itemChatBox.get_id(), editText.getText().toString());
                            }
                        });

                    }
                }
            }
        });

        // Click on delete button
        boxAdapter.setDeleteClickListener(new ChatBoxAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(ItemChatBox itemChatBox) {
                Log.d("------------Delete Clicked", "UserID: " + itemChatBox.getUserId()); // In ra title của box
                Log.d("------------Delete Clicked", "BoxID: " + itemChatBox.get_id()); // In ra title của box

                try {
                    DeleteBox(API + "/box/delete-box/" + itemChatBox.get_id(), itemChatBox);
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // get prompts and boxes
        InitialGetData();
    }

    private void mapping() {
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

        txtHelp2 = findViewById(R.id.txtHelp2);
        edtImgChat = findViewById(R.id.edtImgChat);
        btnSendImg = findViewById(R.id.btnSendImg);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationIcon = findViewById(R.id.navigationIcon);
        imgavatar = popupView.findViewById(R.id.imgavatar);
        txtusername= popupView.findViewById(R.id.txtusername);
        txtAmount = findViewById(R.id.txtAmount);
        txtSize = findViewById(R.id.txtSize);
        btnDes = findViewById(R.id.btnDes);
        btnSave = findViewById(R.id.btnSave);
        btnInCr = findViewById(R.id.btnInCr);
        btnMinus1 = findViewById(R.id.btnMinus1);
        btnPlus1 = findViewById(R.id.btnPlus1);
        fbIcon = popupView.findViewById(R.id.fbIcon);
        insIcon = popupView.findViewById(R.id.insIcon);
        twIcon = popupView.findViewById(R.id.twIcon);
        pinIcon = popupView.findViewById(R.id.pinIcon);
        gitIcon = popupView.findViewById(R.id.gitIcon);
        toolbarImage = findViewById(R.id.toolbarImage);
        imageChatBox = findViewById(R.id.imageChatBox);
        loadingMessage = findViewById(R.id.loadingMessage);
    }

    private void addEvents() {
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

        txtusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageChat.this, ProfileView.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOutUser();
            }
        });
    }

    private void requireAuth() {
        SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        String userId = preferences.getString("userId", ""); //lưu trữ tên người dùng
        if (userId == null || userId == "") {
            Intent intent = new Intent(ImageChat.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    private void LogOutUser() {
        SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit(); // Sửa lỗi gán lại biến preferences
        editor.clear();
        editor.apply();
        Intent intent = new Intent(ImageChat.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    // Chuyển qua Textchat
    public void handleChangeChatMode(View view) {
        Intent intent = new Intent(ImageChat.this, TextChat.class);
        startActivity(intent);
    }

    // Mở sidebar
    public void openSidebarImage(View view) {
        //Nếu đang mở rồi
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            //đóng sidebar
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            // Ngược lại mở sidebar
            drawerLayout.openDrawer(GravityCompat.START);
    }

    // Hàm mở trang web tương ứng
    private void openWebPage(String url) {
        //Tạp Uri từ địa chỉ url (Uri là chuỗi đại diện cho địa chỉ hoặc tài nguyên Internet)
        Uri webpage = Uri.parse(url);
        //Yêu cầu hệ thống mở dữ liệu bằng cách sử dụng ứng dụng mặc định của hệ thống (trình duyệt web)
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        //Nếu có thì sẽ gửi intent và mở trang web
        startActivity(intent);

    }

    // Click on socials
    public void fbLink(View view) {
        openWebPage("https://facebook.com");
    }
    public void insLink(View view) {
        openWebPage("https://instagram.com");
    }
    public void twLink(View view) {
        openWebPage("https://twitter.com");
    }
    public void pinLink(View view) {
        openWebPage("https://pinterest.com");
    }
    public void gitLink(View view) {
        openWebPage("https://github.com");
    }


    // methods with image attributes
    public void desAmount(View view) {
        if (currentValue - 1 >= 1) {
            currentValue--;
            updateTextView();
        }
    }

    public void incrAmount(View view) {
        if (currentValue + 1 <= 4) {
            currentValue++;
            updateTextView();
        }
    }

    public void decSize(View view) {
        if (OptionSizeIndex > 0) {
            OptionSizeIndex = (OptionSizeIndex - 1) % ImageSize.length;
            updateTextView();
        }
    }

    public void incrSize(View view) {
        if (OptionSizeIndex < ImageSize.length - 1) {
            OptionSizeIndex = (OptionSizeIndex + 1) % ImageSize.length;
            updateTextView();
        }
    }

    public void saveImageAttribute(View view) {
        try {
            // Lấy giá trị từ txtAmount và txtSize
            amount = txtAmount.getText().toString();
            size = txtSize.getText().toString();

            SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("amount", amount);
            editor.putString("size", size);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateTextView() {
        txtAmount.setText(String.valueOf(currentValue));
        Size selectedSize = ImageSize[OptionSizeIndex];
        txtSize.setText(selectedSize.getWidth() + "x" + selectedSize.getHeight());
    }

    // Essentials -------------------------------------------
    void setLoading(Boolean value) {
        if (value) {
            loadingMessage.setVisibility(View.VISIBLE);
        } else {
            loadingMessage.setVisibility(View.GONE);
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

    private void addBox(ItemChatBox box) {
        // Thêm các item vào danh sách dữ liệu
        boxes.add(box);
        boxAdapter.notifyDataSetChanged();
    }

    public void handleSentImagePrompt(View view) throws IOException {
        String messageText = edtImgChat.getText().toString().trim();

        if (!messageText.isEmpty()) {
            txtHelp2.setText("");

            CreatePrompt(API + "/image/create-prompt");
        }
    }

    public void handleCreateImageBox(View view) {
        try {
            CreateImageBox(API + "/box/create-box/image");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleClearBoxOfImage(View view) {
        ClearBoxOfImage(API + "/box/clear-boxes/image");
    }

    // IMPORTANT Method -----------------------------------------
    private void InitialGetData() {
        GetImageBoxes(API + "/box/get-boxes/image");
    }

    private void GetImageBoxes(String url){
        if (token.isEmpty()) {
            Toast.makeText(ImageChat.this, "Token không tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        Request request = new Request.Builder()
                .url(url)
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

                    Log.d("onResponse: --------------", myResponse);

                    ImageChat.this.runOnUiThread(new Runnable() {
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
                                    String title = box.optString("title");

                                    // add each prompt to prompt list
                                    ItemChatBox newBox = new ItemChatBox(_id, userId, "image", title);
                                    addBox(newBox);
                                }

                                if (boxes.size() == 0) {
                                    CreateImageBox(API + "/box/create-box/image");
                                    return;
                                }
//
                                // set first box id as initial box
                                ItemChatBox firstBox =  boxes.get(0);
                                boxId = firstBox.get_id();

                                Log.d("---BoxId: ", boxId);

                                // get prompts after got box id
                                if (!boxId.isEmpty()) {
                                    GetUserPrompts(API + "/image/get-prompts/" + boxId);
                                } else {
                                    Toast.makeText(ImageChat.this, "Box ID Không Tồn Tại", Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (JSONException | IOException e)
                            {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            }
        });
    }

    private void CreateImageBox(String url) throws IOException {
        if (token.isEmpty()) {
            Toast.makeText(ImageChat.this, "Token không tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody formBody = new FormBody.Builder()
                .add("nothing", "Nothing")
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

                                Toast.makeText(ImageChat.this, "Đã tạo box mới", Toast.LENGTH_SHORT).show();
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

    private void UpdateBoxName(String url, String value) {
        if (boxId.isEmpty()) {
            Toast.makeText(ImageChat.this, "Box ID không tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }
        if (token.isEmpty()) {
            Toast.makeText(ImageChat.this, "Token không tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody formBody = new FormBody.Builder()
                .add("title", value)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .patch(formBody)
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
                            Log.d("Update Box Name RES: ", myResponse);

                            // clear messages and prompts and boxes to get again
                            prompts.clear();
                            messages.clear();
                            adapter.notifyDataSetChanged();
                            boxes.clear();
                            boxAdapter.notifyDataSetChanged();

                            GetImageBoxes(API + "/box/get-boxes/image");
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

    private void DeleteBox(String url, ItemChatBox box) throws IOException {
        if (token.isEmpty()) {
            Toast.makeText(ImageChat.this, "Token không tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        Request request = new Request.Builder()
                .url(url)
                .delete()
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
                            int index = boxes.indexOf(box);
                            Log.d("----index----", String.valueOf(index));

                            if (index != -1) {
                                Log.d("--------", myResponse);

                                // Xóa box khỏi danh sách
                                boxes.remove(index);

                                // Cập nhật adapter ListView
                                boxAdapter.notifyDataSetChanged();

                                Toast.makeText(ImageChat.this, "Box has been deleted", Toast.LENGTH_SHORT).show();
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

    void ClearBoxOfImage(String url) {
        OkHttpClient client = new OkHttpClient();
        if (token.isEmpty()) {
            Toast.makeText(ImageChat.this, "Token không tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        Request request = new Request.Builder()
                .url(url)
                .delete()
                .addHeader("Authorization", "Bearer " + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    ImageChat.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // clear messages
                            messages.clear();
                            adapter.notifyDataSetChanged();

                            // clear boxes
                            boxes.clear();
                            boxAdapter.notifyDataSetChanged(); // Đối với ArrayAdapter

                            Toast.makeText(ImageChat.this, "Tất cả box đã bị xóa", Toast.LENGTH_SHORT).show();

                            try {
                                CreateImageBox(API + "/box/create-box/image");
                            } catch (IOException e) {
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

    private void GetUserPrompts(String url) throws IOException {
        if (token.isEmpty()) {
            Toast.makeText(ImageChat.this, "Token không tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        Request request = new Request.Builder()
                .url(url)
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
                                    // String chatId = prompt.optString("chatId");
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
                                                    imgUrls.add(imageUrls.get(i + j));
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
        if (boxId.isEmpty()) {
            Toast.makeText(ImageChat.this, "Box ID không tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }
        if (token.isEmpty()) {
            Toast.makeText(ImageChat.this, "Token không tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody formBody = new FormBody.Builder()
                .add("chatId", boxId)
                .add("prompt", edtImgChat.getText().toString().trim())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .addHeader("Authorization", "Bearer " + token) // Add the authorization header with bearer token
                .build();

        // loading true
        setLoading(true);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    ImageChat.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("CreatePrompt IMAGE RES: ", myResponse);

                            try {
                                JSONObject json = new JSONObject(myResponse);
                                JSONObject prompt = json.getJSONObject("prompt");

                                // get prompt values
                                String _id = prompt.optString("_id");
                                String userId = prompt.optString("userId");
                                // String chatId = prompt.optString("chatId");
                                String type = prompt.optString("type");
                                String from = prompt.optString("from");
                                String text = prompt.optString("text");

                                // add new prompt to message list
                                Prompt newPrompt = new Prompt(_id, userId, type, from, text);
                                prompts.add(newPrompt);
                                addNewMessage(newPrompt.text, Objects.equals(newPrompt.from, "user"));

                                // Cuộn xuống dưới cùng khi có tin nhắn mới
                                recyclerViewImage.scrollToPosition(messages.size() - 1);

                                // clear text chat
                                String promptString = edtImgChat.getText().toString();
                                edtImgChat.setText("");

                                // create images after prompt was created
                                CreateImages(API + "/image/create-images", promptString);

                            } catch (JSONException | IOException e ) {
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

    void CreateImages(String url, String prompt) throws IOException {
        // prevent empty prompt
        if (prompt.trim() == "") return;
        if (boxId.isEmpty()) {
            Toast.makeText(ImageChat.this, "Box ID không tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }
        if (token.isEmpty()) {
            Toast.makeText(ImageChat.this, "Token không tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }



        SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        String amount = preferences.getString("amount", "1");
        String size = preferences.getString("size", "256x256");

        RequestBody formBody = new FormBody.Builder()
                .add("prompt", prompt)
                .add("amount", amount)
                .add("size", size)
                .add("chatId", boxId)
                .build();

        Log.d("boxId ------", boxId);
        Log.d("token ------", token);
        Log.d("prompt ------", edtImgChat.getText().toString().trim());
        Log.d("size ------", size);
        Log.d("amount ------", amount);

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
                                JSONObject response = json.getJSONObject("images");
                                // lấy ra mảng image urls
                                JSONArray images = response.getJSONArray("images");

                                for (int i = 0; i < Objects.requireNonNull(images).length(); i++) {
                                    Log.d("url---: ", images.optString(i));
                                }


                                for (int i = 0; i < Objects.requireNonNull(images).length(); i+= chunkSize) {
                                    ArrayList<String> imgUrls = new ArrayList<>();

                                    for (int j = 0; j < chunkSize; j++) {
                                        if (i + j < Objects.requireNonNull(images).length()) {
                                            imgUrls.add(images.optString(i+ j));
                                        }
                                    }

                                    addNewAIMessage(false, imgUrls);
                                }

                                recyclerViewImage.scrollToPosition(messages.size() - 1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } finally {
                                // loading true
                                setLoading(false);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(ImageChat.this, "Create Images Failed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }
}
