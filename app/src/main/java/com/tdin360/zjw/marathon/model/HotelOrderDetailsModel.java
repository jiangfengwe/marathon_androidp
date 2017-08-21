package com.tdin360.zjw.marathon.model;

/**
 * Created by admin on 17/8/12.
 */

public class HotelOrderDetailsModel {

    private String Name;
    private String IDNumber;
    private String EnterDates;
    private String LeaveDates;
    private String TotalMoney;
    private String RoomNumber;
    private String CreateTimeS;
    private String Status;
    private String OrderNo;

    private String Title;
    private String Type;
    private String Area;
    private String PictureUrl;


    public void setTitle(String title) {
        Title = title;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setArea(String area) {
        Area = area;
    }

    public void setPictureUrl(String pictureUrl) {
        PictureUrl = pictureUrl;
    }

    public String getName() {
        return Name;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public String getEnterDates() {
        return EnterDates;
    }

    public String getLeaveDates() {
        return LeaveDates;
    }

    public String getTotalMoney() {
        return TotalMoney;
    }

    public String getRoomNumber() {
        return RoomNumber;
    }

    public String getCreateTimeS() {
        return CreateTimeS;
    }

    public String getStatus() {
        return Status;
    }

    public String getTitle() {
        return Title;
    }

    public String getType() {
        return Type;
    }

    public String getArea() {
        return Area;
    }

    public String getPictureUrl() {
        return PictureUrl;
    }

    public String getOrderNo() {
        return OrderNo;
    }
}
