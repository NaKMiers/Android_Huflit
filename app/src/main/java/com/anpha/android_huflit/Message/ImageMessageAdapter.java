package com.anpha.android_huflit.Message;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;

import com.anpha.android_huflit.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ImageMessage> messages;

    public ImageMessageAdapter(List<ImageMessage> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == 0) {
            // Tin nhắn gửi
            view = inflater.inflate(R.layout.item_container_sent_message, parent, false);
            return new SentMessageViewHolder(view);
        } else {
            // Tin nhắn nhận
            view = inflater.inflate(R.layout.item_container_received_image, parent, false);
            return new ReceivedImageMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageMessage message = messages.get(position);

        if (holder instanceof SentMessageViewHolder) {
            ((SentMessageViewHolder) holder).bind(message);
        } else if (holder instanceof ReceivedImageMessageViewHolder) {
            ((ReceivedImageMessageViewHolder) holder).bind((ImageMessage) message);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).getSentByUser() ? 0 : 1;
    }

    // ViewHolder cho tin nhắn gửi
    public static class SentMessageViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewMessage;

        public SentMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.textMessage);
        }

        public void bind(ImageMessage message) {
            textViewMessage.setText(message.getText());
        }
    }

    // ViewHolder cho tin nhắn nhận với hình ảnh
    public static class ReceivedImageMessageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewMessage;

        public ReceivedImageMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewMessage = itemView.findViewById(R.id.receivedImage);
        }

        public void bind(ImageMessage message) {
            if(message.getImageURL() != null && !message.getImageURL().isEmpty()){
                Picasso.get().load(message.getImageURL()).into(imageViewMessage);
            }
        }
    }
}
