package com.anpha.android_huflit.Message;

public class ImageMessage {
    private String text;
    private boolean sentByUser;

    private int imageResource;

    public ImageMessage(String text, boolean sentByUser, int imageResource) {
        this.text = text;
        this.sentByUser = sentByUser;
        this.imageResource = imageResource;
    }

    public ImageMessage(String text, boolean sentByUser) {
        this.text = text;
        this.sentByUser = sentByUser;
    }

    public ImageMessage(boolean sentByUser, int imageResource) {
        this.sentByUser = sentByUser;
        this.imageResource = imageResource;
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

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
