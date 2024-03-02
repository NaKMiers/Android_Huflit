package com.anpha.android_huflit.Message;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;

import com.anpha.android_huflit.R;

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
            return new ReceivedImageMessageViewHolder(view, parent.getContext());
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
        return messages.get(position).isSentByUser() ? 0 : 1;
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
        private Context context;

        public ReceivedImageMessageViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            imageViewMessage = itemView.findViewById(R.id.receivedImage);
            this.context = context;
            ImageView imageOption = itemView.findViewById(R.id.imageOption);
            imageOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupMenu(v);
                }
            });
        }

        private void showPopupMenu(View v) {
            PopupMenu popupMenu = new PopupMenu(context, v);
            popupMenu.getMenu().add("Save image");
            popupMenu.getMenu().add("Copy image");
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    String option = ((String) item.getTitle()).toString();
                    if (option.equals("Save image"));

                    else if (option.equals("Copy image"));

                    return true;
                }
            });
            popupMenu.show();
        }

        public void bind(ImageMessage message) {
            if(message.getImageURL() != null && !message.getImageURL().isEmpty()){
                Picasso.get().load(message.getImageURL()).into(imageViewMessage);
            }
        }
    }
}