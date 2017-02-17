package com.tdin360.zjw.marathon.model;

import java.io.Serializable;

/**
 * 新闻和公告实体模型
 * Created by Administrator on 2016/7/5.
 */
public class NewsModel implements Serializable
{
    private int id;
    private String title;
    private String time;
    private String picUrl;
    private String detailUrl;



    public NewsModel(){}

    public NewsModel(int id, String title, String picUrl, String detailUrl, String time) {
        this.id = id;
        this.title = title;
        this.picUrl = picUrl;
        this.detailUrl = detailUrl;
        this.time = time;
    }

    public NewsModel(int id, String title, String detailUrl, String time) {
        this.id = id;
        this.title = title;
        this.detailUrl = detailUrl;
        this.time = time;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl){
        this.detailUrl=detailUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }


}
