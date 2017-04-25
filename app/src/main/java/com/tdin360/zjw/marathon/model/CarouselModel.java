package com.tdin360.zjw.marathon.model;

/**
 * 轮播图赞助商实体类
 * Created by Administrator on 2016/7/5.
 */
public class CarouselModel {
    private String title;
    private String eventId;
    private String picUrl;
    private String linkUrl;
    private String type;


    public CarouselModel(String eventId,String title,String picUrl, String linkUrl,String type) {
        this.eventId = eventId;
        this.title=title;
        this.picUrl = picUrl;
        this.linkUrl = linkUrl;
        this.type = type;
    }

    public String getEventId() {
        return eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
