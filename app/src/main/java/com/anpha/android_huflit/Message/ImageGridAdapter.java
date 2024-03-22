package com.anpha.android_huflit.Message;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.anpha.android_huflit.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

class ImageGridAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> mImageUrls;

    public ImageGridAdapter(Activity context, ArrayList<String> imageUrls) {
        super(context, 0, imageUrls);
        this.context = context;
        mImageUrls = imageUrls;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200)); // Set your desired image size here
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corners)); // Áp dụng rounded_corners.xml làm background
            imageView.setClipToOutline(true);
            //imageView holder

            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String imageUrlToSaveOrCopy = mImageUrls.get(position); // Lưu URL của ảnh khi người dùng nhấn giữ
                    showPopupMenu(v, imageUrlToSaveOrCopy);
                    return true;
                }
            });

        } else {
            imageView = (ImageView) convertView;
        }

        // Load image using Picasso or any other library
        Picasso.get()
                .load(mImageUrls.get(position))
                .error(R.drawable.not_found_image)
                .into(imageView);

        return imageView;

    }

    private void showPopupMenu(View v, String imageUrlToSaveOrCopy) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        MenuInflater mnuPopup = popupMenu.getMenuInflater();
        mnuPopup.inflate(R.menu.option_menu, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.saveImage) {
                    saveImage(imageUrlToSaveOrCopy);
                    return true;
                } else if (itemId == R.id.copyImage) {
                    copyImage(imageUrlToSaveOrCopy);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void saveImage(String imageUrl) {
        // Tải ảnh từ URL và lưu vào bộ nhớ của thiết bị
        Picasso.get()
                .load(imageUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        saveBitmapToStorage(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        Toast.makeText(context, "Failed to load image", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
    }

    private void copyImage(String imageUrl) {
        // Tải ảnh từ URL và sao chép vào Clipboard của thiết bị
        Picasso.get()
                .load(imageUrl)
                .into(new com.squareup.picasso.Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        copyBitmapToClipboard(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        Toast.makeText(context, "Failed to load image", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
    }

    private void saveBitmapToStorage(Bitmap bitmap) {
        if (bitmap != null) {
            try {
                File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "HUFLITBIRD");
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                String fileName = "image_" + System.currentTimeMillis() + ".jpg";
                File file = new File(directory, fileName);
                FileOutputStream outputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.close();
                Toast.makeText(context, "Image saved successfully", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "No image to save", Toast.LENGTH_SHORT).show();
        }
    }

    private void copyBitmapToClipboard(Bitmap bitmap) {
        if (bitmap != null) {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

            // Convert Bitmap to Uri
            Uri imageUri = getImageUri(context, bitmap);

            // Create ClipData
            ClipData clipData = ClipData.newUri(context.getContentResolver(), "image", imageUri);

            // Set ClipData to Clipboard
            clipboardManager.setPrimaryClip(clipData);

            // Show Toast
            Toast.makeText(context, "Image copied to Clipboard", Toast.LENGTH_SHORT).show();
        } else {
            // Show Toast if no image is available
            Toast.makeText(context, "No image to copy", Toast.LENGTH_SHORT).show();
        }
    }

    // Function to get Uri from Bitmap
    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Image", null);
        return Uri.parse(path);
    }
}