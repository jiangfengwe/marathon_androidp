package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2017/12/16.
 */

public class TravelCommentBean {

    /**
     * state : false
     * message : 该订单不存在
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
