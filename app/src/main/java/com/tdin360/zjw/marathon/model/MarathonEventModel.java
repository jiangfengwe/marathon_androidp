package com.tdin360.zjw.marathon.model;

/**
 * 赛事列表模型
 * Created by Administrator on 2016/8/24.
 */
public class MarathonEventModel {

    //赛事id
    private int id;
    //赛事名称
    private String name;

//    赛事状态
    private String status;
//    赛事图片
    private String picUrl;
    //竞赛日期
    private String startDate;
    //倒计时时间戳
    private long time;


    public MarathonEventModel(int id, String name, String status, String picUrl, String startDate, long time) {
       this.id=id;
        this.name = name;
        this.status = status;
        this.picUrl = picUrl;
        this.startDate = startDate;
        this.time = time;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
