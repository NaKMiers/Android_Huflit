package com.anpha.android_huflit.ChatBox;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.anpha.android_huflit.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChatBoxAdapter extends ArrayAdapter<ItemChatBox> {
    Activity context;
    int layout;
    ArrayList<ItemChatBox> boxes;

    private OnEditClickListener editClickListener;
    private OnDeleteClickListener deleteClickListener;

    public ChatBoxAdapter(Activity context, int layout, ArrayList<ItemChatBox> boxes) {
        super(context, layout, boxes);
        this.context = context;
        this.layout = layout;
        this.boxes = boxes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        convertView = layoutInflater.inflate(layout, null);

        ItemChatBox box = boxes.get(position);
        TextView titleTv = convertView.findViewById(R.id.chatBox);
        ImageView editBtn = convertView.findViewById(R.id.btnedit);
        ImageView deleteBtn = convertView.findViewById(R.id.btndelete);
        titleTv.setText(box.getItemText());

        // Đặt sự kiện onClick cho nút edit
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editClickListener != null) {
                    editClickListener.onEditClick(box); // Gọi phương thức onEditClick với ItemChatBox tương ứng
                }
            }
        });

        // Đặt sự kiện onClick cho nút delete
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteClickListener != null) {
                    deleteClickListener.onDeleteClick(box); // Gọi phương thức onDeleteClick với ItemChatBox tương ứng
                }
            }
        });

        return convertView;
    }

    // Định nghĩa interface cho các sự kiện onClick
    public interface OnEditClickListener {
        void onEditClick(ItemChatBox itemChatBox);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(ItemChatBox itemChatBox);
    }

    // Setter để thiết lập các Listener
    public void setEditClickListener(OnEditClickListener listener) {
        this.editClickListener = listener;
    }

    public void setDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }
}