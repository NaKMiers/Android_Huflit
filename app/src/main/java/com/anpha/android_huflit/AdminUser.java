package com.anpha.android_huflit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.anpha.android_huflit.ListUser.AdminUserAdapter;
import com.anpha.android_huflit.ListUser.User;

public class AdminUser extends AppCompatActivity {
    ListView ListUser;
    AdminUserAdapter adminUserAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);
        mapping();
        loadData();
    }
    private void loadData() {
        adminUserAdapter.add(new User(R.drawable.owl,"123","huu","luuhonghuu530@gmail.com"));
        adminUserAdapter.add(new User(R.drawable.owl,"153","linh","linhga123@gmail.com"));
    }

    private void mapping() {
        ListUser = findViewById(R.id.ListUser);
        adminUserAdapter = new AdminUserAdapter(this,R.layout.item_user);
        ListUser.setAdapter(adminUserAdapter);
    }
}
