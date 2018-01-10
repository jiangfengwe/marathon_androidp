package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2017/12/6.
 */

public class RegisterSureBean {

    /**
     * state : false
     * message : 验证码不正确
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
