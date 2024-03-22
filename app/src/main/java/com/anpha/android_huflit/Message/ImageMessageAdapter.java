package com.anpha.android_huflit.Message;

import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.anpha.android_huflit.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Lớp adapter ImageMessageAdapter mở rộng từ lớp RecyclerView.Adapter<RecyclerView.ViewHolder>
public class ImageMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //Khởi tạo danh sách tin nhắn
    private List<ImageMessage> messages;
    //Constructor khởi tạo adapter
    public ImageMessageAdapter(List<ImageMessage> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    // Được gọi khi cần tạo 1 viewHolder mới để hiển thị dữ liệu (viewHolder sẽ được gắn vào ViewGroup parent sau khi tạo)
    // Tham số viewType xác định loại dữ liệu dựa trên phương thức getItemViewType
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        //Nếu viewType == 0
        if (viewType == 0) {
            // Thì đó là tin nhắn gửi,và layout item_container_sent_message được nạp vào
            view = inflater.inflate(R.layout.item_container_sent_message, parent, false);
            //Tạo viewHolder tin nhắn gửi
            return new SentMessageViewHolder(view);
            //Ngược lại
        } else {
            // Thì đó là tin nhắn nhận,và layout item_container_received_image được nạp vào
            view = inflater.inflate(R.layout.item_container_received_image, parent, false);
            //Tạo viewHolder tin nhắn nhận
            return new ReceivedImageMessageViewHolder(view, parent.getContext());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //lấy ra mục dữ liệu tương ứng với vị trí position trong danh sách messages
        ImageMessage message = messages.get(position);
        //Nếu holder là 1 đối tượng cua SentMessageViewHolder
        if (holder instanceof SentMessageViewHolder) {
            //Truyền vào phương thức gắn dữ liệu vào giao diện và truyền vào đối tượng tin nhắn message
            ((SentMessageViewHolder) holder).bind(message);
            //Nếu holder là 1 đối tượng của ReceivedImageMessageViewHolder
        } else if (holder instanceof ReceivedImageMessageViewHolder) {
            //Truyền vào phương thức gắn dữ liệu vào giao diện và truyền vào đối tượng tin nhắn message
            ((ReceivedImageMessageViewHolder) holder).bind((ImageMessage) message);
        }
    }

    //Trả về số lượng tin nhắn trong danh sách messages
    @Override
    public int getItemCount() {
        return messages.size();
    }

    //Kiểm tra ở vị trí position có được gửi bởi người dùng hay không thông qua phương thức isSentByUser()
    //Nếu tin nhắn người dùng gửi thì trả về số 0, ngược lại trả về 1
    @Override
    public int getItemViewType(int position) {
        return messages.get(position).isSentByUser() ? 0 : 1;
    }

    // ViewHolder cho tin nhắn gửi
    public static class SentMessageViewHolder extends RecyclerView.ViewHolder {
        //Khai báo biến textViewMessage, đại diện cho TextView của tin nhắn gửi
        private TextView textViewMessage;

        public SentMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            //Tìm kiếm và tham chiếu đến phần tử có id textMessage (TextView)
            textViewMessage = itemView.findViewById(R.id.textMessage);
        }

        //Gán nội dung tin nhắn vào textViewMessage(được lưu trong message.getText())
        public void bind(ImageMessage message) {
            textViewMessage.setText(message.getText());
        }
    }


    public static class ReceivedImageMessageViewHolder extends RecyclerView.ViewHolder {
        private GridView receivedImageGrid;
        private Context context;

        public ReceivedImageMessageViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
//
        }

        public void bind(ImageMessage message) {
            ArrayList<String> imageUrls = message.getImageUrls();

            if (imageUrls != null && !imageUrls.isEmpty()) {
                ImageGridAdapter adapter = new ImageGridAdapter((Activity) context, imageUrls);
                receivedImageGrid.setAdapter(adapter);
            }
        }

    }
}
