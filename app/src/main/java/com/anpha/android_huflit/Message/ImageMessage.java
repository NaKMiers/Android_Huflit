package com.anpha.android_huflit.Message;

public class ImageMessage {
    private String text;
    private boolean sentByUser;

    private String imageURL;

    public ImageMessage() {
        this.text = text;
        this.sentByUser = sentByUser;
        this.imageURL =  imageURL;
    }

    public ImageMessage(String text, boolean sentByUser) {
        this.text = text;
        this.sentByUser = sentByUser;
    }

    public ImageMessage(boolean sentByUser, String imageURL) {
        this.sentByUser = sentByUser;
        this.imageURL = imageURL;
    }

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