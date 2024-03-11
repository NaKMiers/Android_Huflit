package com.anpha.android_huflit.Message;

public class Box {
    private String boxId;
    private String boxName;


    public Box() {
    }

    public Box(String boxId, String boxName) {
        this.boxId = boxId;
        this.boxName = boxName;
    }

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }
}
