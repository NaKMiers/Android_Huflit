package com.anpha.android_huflit.adapter_theme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anpha.android_huflit.Models.Theme;
import com.anpha.android_huflit.R;

import java.util.ArrayList;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_color;
        TextView txtcolor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_color = itemView.findViewById(R.id.image_color);
            txtcolor = itemView.findViewById(R.id.txtcolor);

        }
    }

    //khai báo biến
    Context context; // Change Activity to Context
    ArrayList<Theme> arr_theme;


    public ThemeAdapter(Context context,ArrayList<Theme> arr_theme) {
        this.context = context;
        this.arr_theme = arr_theme;
    }

    //trả về ViewHolder có chứa danh mục
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Hiện thị view
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewTheme = layoutInflater.inflate(R.layout.item_theme, parent, false);//ToRoot = false là hiện thị bằng thủ công
        ViewHolder viewHolderTheme=new ViewHolder(viewTheme);
        return viewHolderTheme;
    }


//Sử lý dữ liệu chọn layout hiện thị
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Theme tm=arr_theme.get(position);
        holder.image_color.setImageResource(tm.getImage_color());
        holder.txtcolor.setText(tm.getTxtcolor());
    }

    //kích thước của danh sách
    @Override
    public int getItemCount() {
        return arr_theme.size();
    }
}

