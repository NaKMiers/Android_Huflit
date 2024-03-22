package com.anpha.android_huflit;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anpha.android_huflit.Models.Theme;

import java.util.ArrayList;

public class ThemeAdapter extends ArrayAdapter<Theme> {
    Activity context;
    int layout;
    ArrayList<Theme> themes;

    public ThemeAdapter(Activity context, int layout, ArrayList<Theme> themes) {
        super(context, layout, themes);
        this.context = context;
        this.layout = layout;
        this.themes = themes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layout, null);

        Theme theme = themes.get(position);
        TextView themeTitle = convertView.findViewById(R.id.themeTitle);
        themeTitle.setText(theme.getTitle());

        return convertView;
    }
}
