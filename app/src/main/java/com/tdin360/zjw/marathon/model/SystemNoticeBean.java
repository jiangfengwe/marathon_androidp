package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2018/1/31.
 */

public class SystemNoticeBean {

    /**
     * messageIntroduce : 测试消息
     * messageType : systemnotification
     * messageId : 19
     * url
     *  private String time;
     private String notice;
     */

    private String messageIntroduce;
    private String messageType;
    private int messageId;
    private String time;
    private String notice;
    private String title;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SystemNoticeBean(String messageIntroduce, String messageType, int messageId, String time, String notice,String title,String url) {
        this.messageIntroduce = messageIntroduce;
        this.messageType = messageType;
        this.messageId = messageId;
        this.time = time;
        this.notice = notice;
        this.title = title;
        this.url=url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getMessageIntroduce() {
        return messageIntroduce;
    }

    public void setMessageIntroduce(String messageIntroduce) {
        this.messageIntroduce = messageIntroduce;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
