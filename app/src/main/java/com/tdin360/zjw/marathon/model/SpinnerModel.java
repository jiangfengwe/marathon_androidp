package com.tdin360.zjw.marathon.model;

/**
 * 报名界面部分下拉列表模型类
 * Created by admin on 17/2/14.
 */

public class SpinnerModel {


    private String key;
    private String value;


    public SpinnerModel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return key;
    }
}
