package com.anpha.android_huflit.ChatBox;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anpha.android_huflit.R;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChatBoxAdapter extends RecyclerView.Adapter<ChatBoxAdapter.ViewHolder> {
    private List<ItemChatBox> dataList;
    private Context context;

    public ChatBoxAdapter(List<ItemChatBox> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatbox_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ItemChatBox item = dataList.get(position);
        TextView textView = holder.textView;
        textView.setText(item.getItemText()); // Đặt giá trị từ item vào textView

        SharedPreferences preferences = context.getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        String savedText = preferences.getString("textValue" + position, "");
        if (!savedText.isEmpty()) {
            textView.setText(savedText); // Nếu có giá trị từ SharedPreferences, đặt giá trị đó vào textView
        }

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo dialog input
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Nhập giá trị mới");

                // Tạo đối tượng EditText để người dùng nhập giá trị mới
                final EditText input = new EditText(context);
                builder.setView(input);

                // Thiết lập nút "OK" để cập nhật textView và lưu giá trị mới
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lấy giá trị mới từ EditText
                        String newText = input.getText().toString();

                        // Cập nhật textView
                        textView.setText(newText);

                        // Lưu giá trị mới vào SharedPreferences
                        SharedPreferences preferences = context.getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("textValue" + position, newText);
                        editor.apply();
                    }
                });

                // Hiển thị dialog
                builder.show();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện xóa

                    }
                });
            }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView btnEdit;
        ImageView btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.chatBox);
            btnEdit = itemView.findViewById(R.id.btnedit);
            btnDelete = itemView.findViewById(R.id.btndelete);
        }
    }
}