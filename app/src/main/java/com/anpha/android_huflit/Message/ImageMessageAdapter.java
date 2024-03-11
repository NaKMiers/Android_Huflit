package com.anpha.android_huflit.Message;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.anpha.android_huflit.ImageChat;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;

import com.anpha.android_huflit.R;

import java.io.File;
import java.io.FileOutputStream;
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

    // ViewHolder cho tin nhắn nhận với hình ảnh
    public static class ReceivedImageMessageViewHolder extends RecyclerView.ViewHolder {
        //Khai báo biến imageViewMessage, đại diện cho ImageView trong tin nhắn nhận
        private ImageView imageViewMessage;
        //Context truyền vào khi tạo mục viewHolder
        private Context context;

        public ReceivedImageMessageViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            //Tìm kiếm và tham chiếu đến phần tử có id receivedImage (imageView)
            imageViewMessage = itemView.findViewById(R.id.receivedImage);
            this.context = context;
            //Tìm kiếm và tham chiếu đến phần tử có id imageOption(icon 3 chấm)
            ImageView imageOption = itemView.findViewById(R.id.imageOption);
            //Sự kiện khi nhấn vào icon 3 chấm
            imageOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupMenu(v);
                }
            });
        }

        private void showPopupMenu(View v) {
            //Khởi tạo PopupMenu
            PopupMenu popupMenu = new PopupMenu(context, v);
            //Thêm 2 mục là Save image và Copy image
            popupMenu.getMenu().add("Save image");
            popupMenu.getMenu().add("Copy image");
            //Sự kiện khi nhn vào các mục của popupMenu
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    //Lấy tiêu đề của mục được chọn trong menu
                    String option = item.getTitle().toString();
                    //Nếu chọn Save image
                    if (option.equals("Save image")) {
                        saveImage();
                    }
                        //Xử lí sự kiện
                        //Nếu chọn Copy image
                    else if (option.equals("Copy image")) ;
                    //Xử lí sự kiện

                    return true;
                }
            });
            //Hiển thị popupMenu
            popupMenu.show();
        }
        private void saveImage() {
            // Bật chế độ lấy cache cho imageViewMessage
            imageViewMessage.setDrawingCacheEnabled(true);

            // Lấy bitmap từ cache
            Bitmap bitmap = imageViewMessage.getDrawingCache();

            // Kiểm tra xem bitmap có null hay không
            if (bitmap != null) {
                // Tạo một đường dẫn cụ thể cho tệp ảnh
                File root = Environment.getExternalStorageDirectory();
                File file = new File(root.getAbsolutePath() + "/DCIM/Camera/img.jpg");

                try {
                    // Kiểm tra và tạo mới tệp nếu nó không tồn tại (nếu bạn muốn ghi đè, bạn có thể loại bỏ điều kiện này)
//                    if (!file.exists()) {
//                        file.createNewFile();
//                    }

                    FileOutputStream ostream = new FileOutputStream(file);

                    // Nén và ghi bitmap vào tệp
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                    ostream.close();

                    // Tắt chế độ lấy cache sau khi đã sử dụng
                    imageViewMessage.setDrawingCacheEnabled(false);

                    // Hiển thị thông báo hoặc thực hiện các hành động khác sau khi lưu thành công
                } catch (Exception e) {
                    e.printStackTrace();
                    // Xử lý lỗi khi lưu ảnh
                }
            }
        }



        public void bind(ImageMessage message) {
            //Nếu link ảnh không phải null và không trống
            if (message.getImageUrls() != null && !message.getImageUrls().isEmpty()) {
                //Lấy ra địa chỉ đầu tiên từ danh sách các URI
                Uri imageUrl = Uri.parse(message.getImageUrls().get(0));
                //Dùng thư viện Picasso và tải ảnh vào imageViewMessage
                Picasso.get().load(imageUrl).into(imageViewMessage);
            }
        }

    }
}