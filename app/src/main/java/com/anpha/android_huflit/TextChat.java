package com.anpha.android_huflit;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.util.ArrayList;
import java.util.List;

public class TextChat extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private List<Message> messages;
    PopupWindow popupWindow;
    Toolbar toolbarChat;

    ImageView btnSend;
    EditText edtTextChat;

    TextView txtHelp1;
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

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = edtTextChat.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    txtHelp1.setText("");
                    // Tạo tin nhắn gửi
                    Message sentMessage = new Message(messageText, true);
                    // Thêm tin nhắn gửi vào cuối danh sách
                    messages.add(sentMessage);
                    // Thông báo cho Adapter biết rằng có một mục mới được thêm vào cuối danh sách
                    adapter.notifyItemInserted(messages.size() - 1);

                    // Tự động gửi tin nhắn nhận
                    Message receivedMessage = new Message("Tin nhắn nhận tự động", false);
                    // Thêm tin nhắn nhận vào cuối danh sách
                    messages.add(receivedMessage);
                    // Thông báo cho Adapter biết rằng có một mục mới được thêm vào cuối danh sách
                    adapter.notifyItemInserted(messages.size() - 1);

                    // Cuộn RecyclerView đến vị trí cuối cùng (để hiển thị tin nhắn mới)
                    recyclerView.scrollToPosition(messages.size() - 1);

                    // Xóa nội dung của EditText sau khi gửi tin nhắn
                    edtTextChat.setText("");
                }
            }
        });
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_chat_menu, null);
        popupWindow = new PopupWindow(
                popupView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1600,
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
}