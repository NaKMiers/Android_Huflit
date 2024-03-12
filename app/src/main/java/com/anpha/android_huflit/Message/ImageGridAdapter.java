package com.anpha.android_huflit.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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
//        ImageView imageView;
        convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false);
//            imageView = convertView.findViewById(R.id.imageView);
//            convertView.setTag(imageView);

        // Load image using your preferred method (e.g., Picasso, Glide)
        // Example with Picasso:
        // Picasso.get().load(mImageUrls.get(position)).into(imageView);

        return convertView;
    }
}
