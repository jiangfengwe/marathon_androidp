package com.tdin360.zjw.marathon.model;

/**
 *
 * 我的物资列表模型
 * Created by admin on 17/3/5.
 */

public class GoodsModel {

    private int id;
    private String eventName;
    private String content;
    private boolean isApply;


    public GoodsModel(int id, String eventName, String content, boolean isApply) {
        this.id = id;
        this.eventName = eventName;
        this.content = content;
        this.isApply = isApply;
    }


    public int getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public String getContent() {
        return content;
    }

    public boolean isApply() {
        return isApply;
    }
}
