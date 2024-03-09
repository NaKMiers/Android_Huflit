package com.anpha.android_huflit.Message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anpha.android_huflit.R;

import java.util.ArrayList;
import java.util.List;

public class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.BoxViewHolder> {
    private List<Box> boxes;

    public BoxAdapter(List<Box> boxes) {
        this.boxes = boxes;
    }

    @NonNull
    @Override
    public BoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_box, parent, false);
        return new BoxViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BoxViewHolder holder, int position) {
        Box box = boxes.get(position);
        holder.bind(box);
    }
    public void addBox(Box newBox) {
        boxes.add(newBox);
        notifyDataSetChanged(); // Thông báo là có sự thay đổi trong dữ liệu
    }
    @Override
    public int getItemCount() {
        return boxes.size();
    }

    public static class BoxViewHolder extends RecyclerView.ViewHolder {
        private TextView txtBoxId;
        private TextView txtBoxName;

        public BoxViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBoxId = itemView.findViewById(R.id.txtBoxId);
            txtBoxName = itemView.findViewById(R.id.txtBoxName);
        }

        public void bind(Box box) {
            txtBoxId.setText("Box ID: " + box.getBoxId());
            txtBoxName.setText("Box Name: " + box.getBoxName());
            // Các bước khác để hiển thị thông tin của box nếu có
        }
    }
}

