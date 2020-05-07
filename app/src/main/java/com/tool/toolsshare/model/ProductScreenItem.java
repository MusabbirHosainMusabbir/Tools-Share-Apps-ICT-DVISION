package com.tool.toolsshare.model;

public class ProductScreenItem {

    String Title;
    String ScreenImg;


    public ProductScreenItem(String title, String screenImg) {
        Title = title;
        ScreenImg = screenImg;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getScreenImg() {
        return ScreenImg;
    }

    public void setScreenImg(String screenImg) {
        ScreenImg = screenImg;
    }
}
