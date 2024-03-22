package com.anpha.android_huflit;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anpha.android_huflit.Models.Theme;

import java.util.ArrayList;

public class ThemesAdapter extends ArrayAdapter<Theme> {
    Activity context;
    int idLayout;
    ArrayList<Theme> myList;

    public ThemesAdapter(Activity context, int idLayout, ArrayList<Theme> myList) {
        super(context, idLayout, myList);
        this.context = context;
        this.idLayout = idLayout;
        this.myList = myList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(idLayout, null);

        Theme theme = myList.get(position);
        TextView txtColor = convertView.findViewById(R.id.txtColor);
        txtColor.setText(theme.getTitle());
        return convertView;
    }
}