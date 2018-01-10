package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2017/12/9.
 */

public class OtherLoginBean {

    /**
     * state : true
     * message : 登陆成功
     * userSecretMessage : V0G9urLbQ0BplFmql9F9GLuZbtDoy5yga9LWGFYBQKUz68IVB8HKvgmaGzhP77NOEqzYeJ3+obzo1cRJSkHM13CqSULXhRZVx28SuHpWSpoQiyVcrTxSNvg69rOOg+kkszeUNAtBDHfK7P8r1xzv8I66H7pM7fJxTYhkg5xOkef8N1A+m6uYJZfQDLbXihQ5RoPl5QrOm85z5gYMG6URPqFz7KYy+uhD68w6Huh0XGQ=
     */

    private boolean state;
    private String message;
    private String userSecretMessage;

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

    public String getUserSecretMessage() {
        return userSecretMessage;
    }

    public void setUserSecretMessage(String userSecretMessage) {
        this.userSecretMessage = userSecretMessage;
    }
}
