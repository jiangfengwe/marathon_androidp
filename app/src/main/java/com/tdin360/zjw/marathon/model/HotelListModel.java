package com.tdin360.zjw.marathon.model;

import java.util.List;

/**
 * 酒店列表模型
 * Created by admin on 17/8/10.
 */

public class HotelListModel {


    private int Id;
    private String Name;
    private String Type;
    private String PictureUrl;
    private double LowestPrice;
    private String Phone1;
    private String Phone2;
    private String Address;
    private List<String>PictureUrlList;
    private String Description;

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }

    public String getPictureUrl() {
        return PictureUrl;
    }

    public double getLowestPrice() {
        return LowestPrice;
    }

    public String getPhone1() {
        return Phone1;
    }

    public String getPhone2() {
        return Phone2;
    }

    public String getAddress() {
        return Address;
    }

    public List<String> getPictureUrlList() {
        return PictureUrlList;
    }

    public String getDescription() {
        return Description;
    }
}
