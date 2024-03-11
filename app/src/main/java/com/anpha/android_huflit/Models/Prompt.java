package com.anpha.android_huflit.Models;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;

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

    public Prompt(String _id, String userId, String type, String from, String text, JSONArray images) {
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

    public Prompt(String _id, String userId, String type, String from, String text) {
        this._id = _id;
        this.userId = userId;
        this.type = type;
        this.from = from;
        this.text = text;

        if (!images.isEmpty()) {
            this.images = images;
        }
    }


}
