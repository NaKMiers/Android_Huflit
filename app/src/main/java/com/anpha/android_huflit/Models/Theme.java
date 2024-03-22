package com.anpha.android_huflit.Models;

public class Theme {
    private int themeId;
    private String title;
    private String backgrouncColor;
    private String textColor;

    public Theme(int themeId, String title, String backgrouncColor, String textColor) {
        this.themeId = themeId;
        this.title = title;
        this.backgrouncColor = backgrouncColor;
        this.textColor = textColor;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackgrouncColor() {
        return backgrouncColor;
    }

    public void setBackgrouncColor(String backgrouncColor) {
        this.backgrouncColor = backgrouncColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }
}
