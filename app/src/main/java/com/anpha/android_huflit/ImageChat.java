package com.anpha.android_huflit;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Size;
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

import com.anpha.android_huflit.Message.ImageMessage;
import com.anpha.android_huflit.Message.ImageMessageAdapter;
import com.anpha.android_huflit.Message.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImageChat extends AppCompatActivity {

    private RecyclerView recyclerViewImage;

    private ImageMessageAdapter adapter;

    private List<ImageMessage> messages;

    TextView txtHelp2;

    EditText edtImgChat;

    ImageView btnSendImg;
    PopupWindow popupWindow;

    Toolbar toolbarImage;

    ImageButton btnPlus1, btnInCr, btnMinus1, btnDes;

    TextView txtAmount, txtSize;
    int currentValue = 1;
    Size[] ImageSize;

    int OptionSizeIndex = 0;

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
        btnSendImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = edtImgChat.getText().toString().trim();
                if(!messageText.isEmpty()){
                    txtHelp2.setText("");
                    ImageMessage sentMessage = new ImageMessage(messageText,true);
                    messages.add(sentMessage);
                    adapter.notifyItemInserted(messages.size() - 1);

                    ImageMessage reivedMessage = new ImageMessage(false,R.drawable.boy);
                    messages.add(reivedMessage);
                    adapter.notifyItemInserted(messages.size() - 1);
                    recyclerViewImage.scrollToPosition(messages.size() - 1);

                    edtImgChat.setText("");
                }
            }
        });

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
        ImageSize = new Size[]{new Size(256, 256), new Size(526, 526)};
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
    }


        private void updateTextView() {
            txtAmount.setText(String.valueOf(currentValue));
            Size selectedSize = ImageSize[OptionSizeIndex];
            txtSize.setText( selectedSize.getWidth() + "x" + selectedSize.getHeight());
        }

}

