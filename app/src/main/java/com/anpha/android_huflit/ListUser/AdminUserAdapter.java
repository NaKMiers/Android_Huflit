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

import java.util.ArrayList;

public class AdminUserAdapter extends ArrayAdapter<User> {
    Activity context;
    ArrayList<User> userList;
    int IdLayout;

    public AdminUserAdapter(@NonNull Activity context, int IdLayout, ArrayList<User> userList) {
        super(context, IdLayout);
        this.context=context;
        this.IdLayout = IdLayout;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.item_user, null);

//        ImageView imageUser = customView.findViewById(R.id.imageUser);
//        TextView IDUser =   customView.findViewById(R.id.IDUser);
//        TextView UserName = customView.findViewById(R.id.UserName);
//        TextView EmailUser = customView.findViewById(R.id.EmailUser);
        User user = userList.get(position);
//
        ImageView avatar = convertView.findViewById(R.id.imageUser);
        TextView IDUser =   customView.findViewById(R.id.IDUser);
        TextView UserName = customView.findViewById(R.id.UserName);
        TextView EmailUser = customView.findViewById(R.id.EmailUser);

        IDUser.setText(user.getIDUser() + "");
        UserName.setText(user.getUserName() + "");
        EmailUser.setText(user.getEmailUser() + "");


        return customView;
    }
}
