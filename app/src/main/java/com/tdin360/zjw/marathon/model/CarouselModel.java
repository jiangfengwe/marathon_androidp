package com.tdin360.zjw.marathon.model;

/**
 * 轮播图实体类
 * Created by Administrator on 2016/7/5.
 */
public class CarouselModel {
    private String eventId;
    private String picUrl;
    private String linkUrl;
    private String type;


    public CarouselModel(String eventId,String picUrl, String linkUrl,String type) {
        this.eventId = eventId;
        this.picUrl = picUrl;
        this.linkUrl = linkUrl;
        this.type = type;
    }

    public String getEventId() {
        return eventId;
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

    public String isType() {
        return type;
    }
}
