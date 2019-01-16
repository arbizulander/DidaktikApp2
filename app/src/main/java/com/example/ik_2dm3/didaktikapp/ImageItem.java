package com.example.ik_2dm3.didaktikapp;

import android.graphics.Bitmap;

public class ImageItem {
    private Bitmap image;
    private String title;

    ImageItem(){

    }

    public ImageItem(Bitmap image, String title) {
        super();
        this.image = image;
        this.title = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}