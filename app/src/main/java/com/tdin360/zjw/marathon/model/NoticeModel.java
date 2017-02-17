package com.tdin360.zjw.marathon.model;


import java.io.Serializable;

/**
 * Created by admin on 16/11/22.
 */

public class NoticeModel implements Serializable{

    private int id;
    private String title;
    private String url;
    private String[] split;


    public NoticeModel(int id, String title, String date, String url ) {
        this.id=id;
        this.title = title;
        this.url=url;
        this.split = date.split("-");

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

    public String getDate() {


        return split[0]+"-"+split[1];
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDay() {


        return split[2];
    }



}
