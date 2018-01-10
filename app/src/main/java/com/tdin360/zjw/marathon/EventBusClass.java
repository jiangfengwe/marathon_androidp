package com.tdin360.zjw.marathon;

import com.tdin360.zjw.marathon.model.HotelDetailBean;

import java.util.List;

/**
 * Created by jeffery on 2017/12/7.
 */

public class EventBusClass {
    private String msg;
    private EnumEventBus enumEventBus;



    public EnumEventBus getEnumEventBus() {
        return enumEventBus;
    }

    public void setEnumEventBus(EnumEventBus enumEventBus) {
        this.enumEventBus = enumEventBus;
    }

    public EventBusClass(EnumEventBus enumEventBus) {
        this.enumEventBus = enumEventBus;
    }

    public EventBusClass(String msg , EnumEventBus enumEventBus) {
        this.msg = msg;
        this.enumEventBus = enumEventBus;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
