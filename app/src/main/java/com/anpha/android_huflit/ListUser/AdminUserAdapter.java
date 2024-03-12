package com.anpha.android_huflit.ListUser;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anpha.android_huflit.R;

public class AdminUserAdapter extends ArrayAdapter<User> {
    Activity context;
    int resource;
    public AdminUserAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View customView = layoutInflater.inflate(this.resource, null);
        ImageView imageUser = customView.findViewById(R.id.imageUser);
        TextView IDUser =   customView.findViewById(R.id.IDUser);
        TextView UserName = customView.findViewById(R.id.UserName);
        TextView EmailUser = customView.findViewById(R.id.EmailUser);
        User au = getItem(position);
        imageUser.setImageResource(au.getImageUser());
        IDUser.setText(au.getIDUser()+"");
        UserName.setText(au.getUserName()+"");
        EmailUser.setText(au.getEmailUser()+"");
        return customView;
    }
}
