package com.anpha.android_huflit.Message;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anpha.android_huflit.R;

import java.util.List;
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Message> messages;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Kiểm tra viewType để xác định layout cho tin nhắn gửi hoặc nhận
        if (viewType == 0) {
            // Tin nhắn gửi
            view = inflater.inflate(R.layout.item_container_sent_message, parent, false);
            return new SentMessageViewHolder(view);
        } else {
            // Tin nhắn nhận
            view = inflater.inflate(R.layout.item_container_received_message, parent, false);
            return new ReceivedMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);

        // Hiển thị tin nhắn tương ứng
        if (holder instanceof SentMessageViewHolder) {
            ((SentMessageViewHolder) holder).bind(message);
        } else if (holder instanceof ReceivedMessageViewHolder) {
            ((ReceivedMessageViewHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        // Trả về 0 nếu là tin nhắn gửi, ngược lại trả về 1
        return messages.get(position).isSentByUser() ? 0 : 1;
    }

    // ViewHolder cho tin nhắn gửi
    public static class SentMessageViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewMessage;

        public SentMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.textMessage);
        }

        public void bind(Message message) {
            textViewMessage.setText(message.getText());
        }
    }

    // ViewHolder cho tin nhắn nhận
    public static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewMessage;

        public ReceivedMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.receivedMessage);
        }

        public void bind(Message message) {
            textViewMessage.setText(message.getText());
        }
    }
}