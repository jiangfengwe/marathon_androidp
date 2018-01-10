package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2017/12/16.
 */

public class HotelCommentBean {

    /**
     * state : false
     * message : 该订单暂未使用，不允许评价
     */

    private boolean state;
    private String message;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
