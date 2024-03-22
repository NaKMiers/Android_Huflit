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

    // ViewHolder cho tin nhắn nhận với hình ảnh
    public static class ReceivedImageMessageViewHolder extends RecyclerView.ViewHolder {
        //Khai báo biến imageViewMessage, đại diện cho ImageView trong tin nhắn nhận
        private GridView receivedImageGrid;
        //Context truyền vào khi tạo mục viewHolder
        private Context context;

        public ReceivedImageMessageViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            //Tìm kiếm và tham chiếu đến phần tử có id receivedImage (imageView)
//            imageViewMessage = itemView.findViewById(R.id.receivedImage);
            receivedImageGrid = itemView.findViewById(R.id.receivedImageGrid);
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
            receivedImageGrid.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopupMenu(v);
                    return true;
                }
            });

        }

        public void bind(ImageMessage message) {
            ArrayList<String> imageUrls = message.getImageUrls();

            if (imageUrls != null && !imageUrls.isEmpty()) {
                // Create and set adapter for GridView
                ImageGridAdapter adapter = new ImageGridAdapter((Activity) context, imageUrls);
                receivedImageGrid.setAdapter(adapter);
            }
        }

        private void showPopupMenu(View v) {
            // Khởi tạo PopupMenu
            PopupMenu popupMenu = new PopupMenu(context, v);
            // Inflate file menu
            MenuInflater mnuPopup = popupMenu.getMenuInflater();
            mnuPopup.inflate(R.menu.option_menu, popupMenu.getMenu());
            // Hiển thị PopupMenu
            popupMenu.show();

            // Đặt lắng nghe sự kiện cho các MenuItem
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    // Xác định MenuItem được click bằng ID
                    int itemId = item.getItemId();
                    if (itemId == R.id.saveImage) {
                        // Thực hiện lệnh lưu ảnh ở đây
                        saveImage(receivedImageGrid.getDrawingCache());
                        return true; // Báo hiệu rằng sự kiện click đã được xử lý
                    } else if (itemId == R.id.copyImage) {
                        // Thực hiện lệnh copy ảnh ở đây
                        copyImage("hihihihihi");
                        return true; // Báo hiệu rằng sự kiện click đã được xử lý
                    } else {
                        return false; // Trả về false nếu không xử lý được sự kiện click
                    }
                }
            });
        }

        // Hàm thực hiện lệnh lưu ảnh
        private void saveImage(Bitmap bitmap) {
            if (bitmap != null) {
                try {
                    // Tạo thư mục để lưu ảnh (bạn có thể thay đổi thư mục lưu theo nhu cầu)
                    File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "HUFLITBIRD");
                    if (!directory.exists()) {
                        directory.mkdirs(); // Tạo thư mục nếu nó không tồn tại
                    }

                    // Tạo tên file cho ảnh
                    String fileName = "image_" + System.currentTimeMillis() + ".jpg";

                    // Tạo đường dẫn đầy đủ cho file ảnh
                    File file = new File(directory, fileName);

                    // Tạo luồng đầu ra để ghi dữ liệu vào file
                    FileOutputStream outputStream = new FileOutputStream(file);

                    // Lưu ảnh dướng dạng JPEG với chất lượng 100%
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

                    // Đóng luồng đầu ra
                    outputStream.close();

                    // Thông báo cho người dùng biết rằng ảnh đã được lưu thành công
                    Toast.makeText(context, "Image saved successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    // Xử lý nếu có lỗi xảy ra trong quá trình lưu ảnh
                    e.printStackTrace();
                    Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Thông báo cho người dùng biết rằng không có ảnh để lưu
                Toast.makeText(context, "No image to save", Toast.LENGTH_SHORT).show();
            }
        }


        // Hàm thực hiện lệnh copy ảnh
//        private void copyImage(Bitmap bitmap) {
//            // Kiểm tra xem Bitmap có tồn tại không
//            if (bitmap != null) {
//                // Tạo một ClipData để chứa dữ liệu cắt
//                ClipData clipData = ClipData.newPlainText("image", "Image from your app");
//
//                // Tạo một Item cho ClipData, với một đối tượng ClipData.Item chứa ảnh dưới dạng Parcelable
//                clipData.addItem(new ClipData.Item(new BitmapDrawable(context.getResources(), bitmap).toString()));
//
//                // Nhận dịch vụ ClipboardManager
//                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//
//                // Đặt dữ liệu cắt vào Clipboard
//                clipboardManager.setPrimaryClip(clipData);
//
//                // Thông báo cho người dùng biết rằng ảnh đã được sao chép vào Clipboard thành công
//                Toast.makeText(context, "Image copied to Clipboard", Toast.LENGTH_SHORT).show();
//            } else {
//                // Thông báo cho người dùng biết rằng không có ảnh để sao chép
//                Toast.makeText(context, "No image to copy", Toast.LENGTH_SHORT).show();
//            }
//        }
        private void copyImage(String textToCopy) {
            if ( textToCopy!= null && !textToCopy.isEmpty()) {
            // Tạo một ClipData để chứa dữ liệu văn bản cần sao chép
            ClipData clipData = ClipData.newPlainText("text", textToCopy);

            // Nhận dịch vụ ClipboardManager
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

            // Đặt dữ liệu cắt vào Clipboard
            clipboardManager.setPrimaryClip(clipData);

            // Thông báo cho người dùng biết rằng văn bản đã được sao chép vào Clipboard thành công
            Toast.makeText(context, "Text copied to Clipboard", Toast.LENGTH_SHORT).show();
        } else {
            // Thông báo cho người dùng biết rằng không có văn bản để sao chép
            Toast.makeText(context, "No text to copy", Toast.LENGTH_SHORT).show();
        }
        }

    }
}