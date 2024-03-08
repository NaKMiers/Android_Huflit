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
    //Khởi tạo danh sách tin nhắn
    private List<Message> messages;

    //Constructor khởi tạo adapter
    public MessageAdapter(List<Message> messages) {
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
            // Thì đó là tin nhắn nhận,và layout item_container_received_message được nạp vào
            view = inflater.inflate(R.layout.item_container_received_message, parent, false);
            //Tạo viewHolder tin nhắn nhận
            return new ReceivedMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //lấy ra mục dữ liệu tương ứng với vị trí position trong danh sách messages
        Message message = messages.get(position);
        //Nếu holder là 1 đối tượng cua SentMessageViewHolder
        if (holder instanceof SentMessageViewHolder) {
        //Truyền vào phương thức gắn dữ liệu vào giao diện và truyền vào đối tượng tin nhắn message
            ((SentMessageViewHolder) holder).bind(message);
        //Nếu holder là 1 đối tượng của ReceivedMessageViewHolder
        } else if (holder instanceof ReceivedMessageViewHolder) {
        //Truyền vào phương thức gắn dữ liệu vào giao diện và truyền vào đối tượng tin nhắn message
            ((ReceivedMessageViewHolder) holder).bind(message);
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
        // Trả về 0 nếu là tin nhắn gửi, ngược lại trả về 1
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
        public void bind(Message message) {
            textViewMessage.setText(message.getText());
        }
    }

    // ViewHolder cho tin nhắn nhận
    public static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        //Khai báo biến textViewMessage, đại diện cho TextView của tin nhắn nhận
        private TextView textViewMessage;

        public ReceivedMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            //Tìm kiếm và tham chiếu đến phần tử có id receivedMessage(TextView)
            textViewMessage = itemView.findViewById(R.id.receivedMessage);
        }
        //Gán nội dung tin nhắn vào textViewMessage(được lưu trong message.getText())
        public void bind(Message message) {
            textViewMessage.setText(message.getText());
        }
    }
}