package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2017/12/14.
 */

public class HotelOrderBean {

    /**
     * state : true
     * message : 订单生成成功
     * orderSecretMessage : 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
     */

    private boolean state;
    private String message;
    private String orderSecretMessage;

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

    public String getOrderSecretMessage() {
        return orderSecretMessage;
    }

    public void setOrderSecretMessage(String orderSecretMessage) {
        this.orderSecretMessage = orderSecretMessage;
    }
}
