package com.anpha.android_huflit.Models;

public class Theme {
    int themeId;
    String background;
    String textColor;
    String title;

    public Theme(int themeId, String background, String textColor, String title) {
        this.themeId = themeId;
        this.background = background;
        this.textColor = textColor;
        this.title = title;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
