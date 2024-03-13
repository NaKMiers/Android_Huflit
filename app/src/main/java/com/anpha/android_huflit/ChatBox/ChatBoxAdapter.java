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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anpha.android_huflit.R;

import org.w3c.dom.Text;

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
    String token,userID;

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
        textView.setText(item.getType()); // Đặt giá trị từ item vào textView

        SharedPreferences preferences = context.getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        String savedText = preferences.getString("textValue" + position, "");
        token = preferences.getString("token", ""); //lưu trữ tên người dùng
        userID = preferences.getString("userId", ""); //lưu trữ tên người dùng
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
                onDeleteBox(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private void onDeleteBox(final int position) {
        ItemChatBox item = dataList.get(position);
        String boxId = item.get_id(); // Lấy ID của hộp chat cần xóa
        OkHttpClient client = new OkHttpClient();

        String url = "https://android-huflit-server.vercel.app/box/delete-box";
        Request request = new Request.Builder()
                .url(url + "/" + boxId)
                .delete()
                .addHeader("Authorization", "Bearer " + token)
                .build();
//
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Xử lý khi yêu cầu gửi không thành công
                e.printStackTrace();
            }
//
//            @Override
public void onResponse(Call call, Response response) throws IOException {
    if (response.isSuccessful()) {
        // Xóa hộp chat khỏi dataList
        dataList.remove(position);
//         Thực hiện cập nhật giao diện trong luồng chính
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataList.size());
                // Xóa TextView chatBox
            }
        });
//        onDeleteBox(position);
        Log.e("DeleteBox", "Request ok: " + response.code());
    } else {
        // Xử lý khi yêu cầu gửi thành công nhưng không thành công
        Log.e("DeleteBox", "Request unsuccessful: " + response.code());
    }
}
        });
    }
    public void clearTextValue() {
        SharedPreferences preferences = context.getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
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
