package com.tdin360.zjw.marathon.model;

/**
 * 轮播图实体类
 * Created by Administrator on 2016/7/5.
 */
public class CarouselItem {
    private String picUrl;
    private String linkUrl;


    public CarouselItem(String picUrl, String linkUrl) {
        this.picUrl = picUrl;
        this.linkUrl = linkUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
