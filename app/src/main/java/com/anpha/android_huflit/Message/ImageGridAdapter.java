package com.anpha.android_huflit.Message;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.anpha.android_huflit.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class ImageGridAdapter extends ArrayAdapter<String> {
    private Activity context;
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
        } else {
            imageView = (ImageView) convertView;
        }

        // Load image using Picasso or any other library
        Picasso.get().load(mImageUrls.get(position)).into(imageView);

        return imageView;
    }

}
