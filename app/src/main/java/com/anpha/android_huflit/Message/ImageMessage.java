package com.anpha.android_huflit.Message;

import java.util.List;

// Class đại diện cho tin nhắn
public class ImageMessage {
    // Tin nhắn là kiểu chuỗi
    private String text;
    // Biến để xác định có phải do người dùng gửi (true là người dùng)
    private boolean sentByUser;
    // Danh sách các đường dẫn URL hình ảnh
    private List<String> imageUrls;

    public ImageMessage() {
        this.text = text;
        this.sentByUser = sentByUser;
        this.imageUrls = imageUrls;
    }

    // Constructor cho tin nhắn người dùng gửi
    public ImageMessage(String text, boolean sentByUser) {
        this.text = text;
        this.sentByUser = sentByUser;
    }

    // Constructor cho tin nhắn AI gửi
    public ImageMessage(boolean sentByUser, List<String> imageUrls) {
        this.sentByUser = sentByUser;
        this.imageUrls = imageUrls;
    }

    // Các getter và setter
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSentByUser() {
        return sentByUser;
    }

    public void setSentByUser(boolean sentByUser) {
        this.sentByUser = sentByUser;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}