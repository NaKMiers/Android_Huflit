package com.anpha.android_huflit;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Size;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.PopupWindow;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.TextView;

public class ImageChat extends AppCompatActivity {

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
                1730,
                true
        );

        btnMinus1 = popupView.findViewById(R.id.btnMinus1);
        btnInCr = popupView.findViewById(R.id.btnInCr);
        btnPlus1 = popupView.findViewById(R.id.btnPlus1);
        btnDes = popupView.findViewById(R.id.btnDes);
        txtAmount = popupView.findViewById(R.id.txtAmount);
        txtSize = popupView.findViewById(R.id.txtSize);

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

