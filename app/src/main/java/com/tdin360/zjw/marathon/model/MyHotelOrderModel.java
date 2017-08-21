package com.tdin360.zjw.marathon.model;

/**
 * Created by admin on 17/8/12.
 */

public class MyHotelOrderModel {


    private String Id;
    private String HotelName;
    private String OrderNo;
    private boolean IsPay;
    private String PictureUrl;
    private String Status;
    private String Money;


    public String getId() {
        return Id;
    }

    public String getHotelName() {
        return HotelName;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public boolean isPay() {
        return IsPay;
    }

    public String getPictureUrl() {
        return PictureUrl;
    }

    public String getStatus() {
        return Status;
    }

    public String getMoney() {
        return Money;
    }
}
