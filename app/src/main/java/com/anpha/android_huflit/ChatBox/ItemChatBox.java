package com.anpha.android_huflit.ChatBox;

public class ItemChatBox {
    private String _id;
    private String userId;
    private String type;
    private String itemText;

    public ItemChatBox(String _id, String userId, String type, String itemText) {
        this._id = _id;
        this.userId = userId;
        this.type = type;
        this.itemText = itemText;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    @Override
    public String toString() {
        return "ItemChatBox{" +
                "_id='" + _id + '\'' +
                ", userId='" + userId + '\'' +
                ", type='" + type + '\'' +
                ", itemText='" + itemText + '\'' +
                '}';
    }
}
