package com.anpha.android_huflit.Models;

import java.io.Serializable;

public class Theme implements Serializable {
    private  int image_color;
    private String txtcolor;

    //tạo Contructor có tham số
    public Theme(int image_color, String txtcolor) {
        this.image_color = image_color;
        this.txtcolor = txtcolor;
    }

    //tạo thuộc tính get, set
    public int getImage_color() {
        return image_color;
    }

    public void setImage_color(int image_color) {
        this.image_color = image_color;
    }

    public String getTxtcolor() {
        return txtcolor;
    }

    public void setTxtcolor(String txtcolor) {
        this.txtcolor = txtcolor;
    }

    public Theme() {

    }
}
