package com.tdin360.zjw.marathon.model;

/**
 * 意见反馈
 * Created by admin on 17/3/22.
 */

public class FeedBackModel {

    private String time;
    private String content;
    private String fromTime;
    private String fromContent;



    public FeedBackModel(String time, String content, String fromTime, String fromContent) {

        this.time = time;
        this.content = content;
        this.fromTime = fromTime;
        this.fromContent = fromContent;
    }


    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public String getFromTime() {
        return fromTime;
    }

    public String getFromContent() {
        return fromContent;
    }
}
