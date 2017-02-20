package com.tdin360.zjw.marathon.model;

import android.graphics.Bitmap;

/**
 * Created by admin on 17/2/19.
 */

public class MenuModel {


    private int id;
    private Bitmap icon;
    private String title;

    public MenuModel(int id, Bitmap icon, String title) {
        this.id = id;
        this.icon = icon;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
