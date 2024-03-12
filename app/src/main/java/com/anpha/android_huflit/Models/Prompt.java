package com.anpha.android_huflit.Models;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Prompt {
    public String _id;
    public String userId;
    public String chatId;
    public String type;
    public String from;
    public String text;
    public ArrayList<String> images;
    public Date createdAt;
    public Date updatedAt;

    public Prompt(String _id, String userId, String type, String from, String text) {
        this._id = _id;
        this.userId = userId;
//        this.chatId = chatId;
        this.type = type;
        this.from = from;
        this.text = text;
//        this.createdAt = new Date(createdAt);
//        this.updatedAt = new Date(updatedAt);

//        for(int i = 0; i < Objects.requireNonNull(images).length(); i++){
//            String src = images.optString(i);
//            this.images.add(src);
//        }
    }

    public Prompt(String _id, String userId, String type, String from, String text, ArrayList<String> images) {
        this._id = _id;
        this.userId = userId;
        this.type = type;
        this.from = from;
        this.text = text;

        if (!images.isEmpty()) {
            this.images = images;
        }
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
