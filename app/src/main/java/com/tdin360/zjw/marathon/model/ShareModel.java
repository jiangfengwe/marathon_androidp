package com.tdin360.zjw.marathon.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**分享信息模型
 * Created by admin on 16/12/25.
 */

public class ShareModel implements Serializable{

    private String title;
    private String url;
    private Bitmap bitmap;
    private String describe;

    public ShareModel(String title, String url, String describe, Bitmap bitmap) {
        this.title = title;
        this.url = url;
        this.describe = describe;
        this.bitmap = bitmap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
