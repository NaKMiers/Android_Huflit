package com.anpha.android_huflit.Message;

//Class đại diện cho tin nhắn
public class ImageMessage {
    //Tin nhắn là kiểu chuỗi
    private String text;
    //Biến để xác định có phải do người dùng gửi (true là người dùng)
    private boolean sentByUser;
    //Link ảnh kiểu chuỗi
    private String imageURL;

    public ImageMessage() {
        this.text = text;
        this.sentByUser = sentByUser;
        this.imageURL =  imageURL;
    }

    //Constructor cho tin nhắn người dùng gửi
    public ImageMessage(String text, boolean sentByUser) {
        this.text = text;
        this.sentByUser = sentByUser;
    }

    //Constructor cho tin nhắn AI gửi
    public ImageMessage(boolean sentByUser, String imageURL) {
        this.sentByUser = sentByUser;
        this.imageURL = imageURL;
    }

    //Các getter và setter
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}