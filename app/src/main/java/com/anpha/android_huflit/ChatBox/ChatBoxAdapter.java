package com.anpha.android_huflit.ChatBox;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anpha.android_huflit.R;

import java.util.List;

public class ChatBoxAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ItemChatBox> dataList;
    private Context context;

    public ChatBoxAdapter(List<ItemChatBox> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatbox_layout, parent, false);
        return new RecyclerView.ViewHolder(view) {};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemChatBox item = dataList.get(position);
        TextView textView = holder.itemView.findViewById(R.id.chatBox);
        ImageView btnedit = holder.itemView.findViewById(R.id.btnedit);
        ImageView btndelete = holder.itemView.findViewById(R.id.btndelete);

        textView.setText(item.getItemText());
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
