package com.tdin360.zjw.marathon.model;

/**
 * 个人中心通知消息模型
 * Created by admin on 17/2/16.
 */

public class NoticeMessageModel {

    private int id;
    //消息发送人、单位
    private String forName;
//    发送时间
    private String forTime;
//    通知消息内容
    private String message;

    public NoticeMessageModel(String forName, String forTime, String message) {
        this.forName = forName;
        this.forTime = forTime;
        this.message = message;
    }

    public NoticeMessageModel(int id, String forName, String forTime, String message) {
        this.id = id;
        this.forName = forName;
        this.forTime = forTime;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getForName() {
        return forName;
    }

    public void setForName(String forName) {
        this.forName = forName;
    }

    public String getForTime() {
        return forTime;
    }

    public void setForTime(String forTime) {
        this.forTime = forTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
