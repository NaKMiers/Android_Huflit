package com.anpha.android_huflit.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.anpha.android_huflit.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class ImageGridAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private ArrayList<String> mImageUrls;

    public ImageGridAdapter(Context context, ArrayList<String> imageUrls) {
        super(context, 0, imageUrls);
        mContext = context;
        mImageUrls = imageUrls;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = convertView.findViewById(R.id.imageItemMessageView);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.image_item_message, parent, false);
            imageView = convertView.findViewById(R.id.imageItemMessageView);
            convertView.setTag(imageView);
        }
        // Load image using your preferred method (e.g., Picasso, Glide)
        // Example with Picasso:
         Picasso.get().load(mImageUrls.get(position)).into(imageView);

        return convertView;
    }
}
