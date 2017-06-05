package com.tdin360.zjw.marathon.model;

import java.io.Serializable;

/**
 *
 * 我的物资列表模型
 * Created by admin on 17/3/5.
 */

public class GoodsModel implements Serializable{

    private int id;
    private String name;
    private String gander;
    private String number;
    private String idNumber;
    private String eventName;
    private String content;
    private String size;
    private boolean isApply;
    private String goodsInfo;
    private boolean isReceive;


    public GoodsModel(int id,String name,String gander,String number,String idNumber,String eventName, String content,String size,boolean isApply,String goodsInfo,boolean isReceive) {
        this.id = id;
        this.name=name;
        this.number=number;
        this.idNumber = idNumber;
        this.gander = gander;
        this.eventName = eventName;
        this.content = content;
        this.size=size;
        this.isApply = isApply;
        this.goodsInfo=goodsInfo;
        this.isReceive=isReceive;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGander() {
        return gander;
    }

    public String getNumber() {
        return number;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getSize() {
        return size;
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

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public boolean isReceive(){

        return isReceive;
    }

}
