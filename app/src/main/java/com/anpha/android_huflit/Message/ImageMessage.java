package com.anpha.android_huflit.Message;

import com.anpha.android_huflit.Models.Image;

import java.util.ArrayList;

public class ImageMessage {
    // attributes
    private String text;
    private Boolean sentByUser;
    private ArrayList<String> imageUrls;

    // constructors
    public ImageMessage(String text, Boolean sentByUser, ArrayList<String> imageUrls) {
        if(text != null && text.trim() != "") {
            this.text = text;
        }
        this.sentByUser = sentByUser;
        if(!imageUrls.isEmpty()) {
            this.imageUrls = imageUrls;
        }
    }

    // methods
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getSentByUser() {
        return sentByUser;
    }

    public void setSentByUser(Boolean sentByUser) {
        this.sentByUser = sentByUser;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
