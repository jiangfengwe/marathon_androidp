package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2018/1/24.
 */

public class CircleShareBean {

    /**
     * state : true
     * message : 分享成功
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
