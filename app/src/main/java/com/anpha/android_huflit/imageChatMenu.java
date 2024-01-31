package com.anpha.android_huflit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

public class imageChatMenu extends AppCompatActivity {
    TextView txtAmount, txtSize;
    ImageButton btnMinus1, btnMinus2, btnPlus1, btnPlus2;

    int currentValue = 1;

    Toolbar toolbarImageChatMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_chat_menu);

        btnMinus1 = findViewById(R.id.btnMinus1);
        btnMinus2 = findViewById(R.id.btnMinus2);
        btnPlus1 = findViewById(R.id.btnPlus1);
        btnPlus2 = findViewById(R.id.btnPlus2);

        txtAmount = findViewById(R.id.txtAmount);
        txtSize = findViewById(R.id.txtSize);

        Toolbar toolbarImageChatMenu = findViewById(R.id.toolbarImageChatMenu);
        toolbarImageChatMenu.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.iconMenu) {
                    finish();
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

    }
    private void updateTextView() {
        txtAmount.setText(String.valueOf(currentValue));
    }
}