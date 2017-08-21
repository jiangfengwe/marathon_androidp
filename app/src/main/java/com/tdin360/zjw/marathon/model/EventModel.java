package com.tdin360.zjw.marathon.model;

/**
 * 赛事列表模型
 * Created by Administrator on 2016/8/24.
 */
public class EventModel {

    //赛事id
    private int id;
    //赛事名称
    private String name;

//    赛事状态
    private String status;
//    赛事图片
    private String picUrl;
    //比赛时间
    private String startDate;
    //报名时间
    private String signUpStartTime;
    //倒计时时间戳
    private long time;

    private String shardUrl;

    private boolean isRegister;

    private boolean isWebPage;


    public EventModel(int id, String name, String status, String picUrl,String signUpStartTime, String startDate,String shardUrl,boolean isRegister,boolean isWebPage) {
        this.id=id;
        this.name = name;
        this.status = status;
        this.picUrl = picUrl;
        this.signUpStartTime=signUpStartTime;
        this.startDate = startDate;
        this.shardUrl=shardUrl;
        this.isRegister=isRegister;
        this.isWebPage=isWebPage;

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

    public String getSignUpStartTime(){

        return signUpStartTime;
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

    public String getShardUrl() {
        return shardUrl;
    }

    public boolean isRegister() {
        return isRegister;
    }

    public void setRegister(boolean register) {
        isRegister = register;
    }

    public boolean isWebPage() {
        return isWebPage;
    }


    @Override
    public String toString() {
        return "EventModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", startDate='" + startDate + '\'' +
                ", signUpStartTime='" + signUpStartTime + '\'' +
                ", time=" + time +
                ", shardUrl='" + shardUrl + '\'' +
                ", isRegister=" + isRegister +
                ", isWebPage=" + isWebPage +
                '}';
    }
}
