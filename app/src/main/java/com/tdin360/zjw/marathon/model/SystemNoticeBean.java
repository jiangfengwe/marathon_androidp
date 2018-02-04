package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2018/1/31.
 */

public class SystemNoticeBean {

    /**
     * messageIntroduce : 测试消息
     * messageType : systemnotification
     * messageId : 19
     *  private String time;
     private String notice;
     */

    private String messageIntroduce;
    private String messageType;
    private int messageId;
    private String time;
    private String notice;

    public SystemNoticeBean(String messageIntroduce, String messageType, int messageId, String time, String notice) {
        this.messageIntroduce = messageIntroduce;
        this.messageType = messageType;
        this.messageId = messageId;
        this.time = time;
        this.notice = notice;
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
