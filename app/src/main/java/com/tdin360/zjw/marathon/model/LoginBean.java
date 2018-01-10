package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2017/12/6.
 */

public class LoginBean {


    /**
     * state : true
     * message : 登陆成功
     * userSecretMessage : GuHGZ2m4TjGkjsTUtmy3reFhWf/iD0CGPijmUW/OgjrvG9ry/XcXQpptm94EQ4m7TBYGalYKW4D7zcgygBXuD6Ybr9ktaYwbE8f8vbJB6mv8TEQae+0TUj3gjRLgX6kn+5mzzL4vEpvvcsGb04koymsAOim8kyenst5QdmysFDPKGrZMzmuA8EBfiktzRdNDgjYYz2aGowgU5Q0WjhLmM4xk1qe2hXW+AjnW1tEdVHdYuTDRncnRcWwLDb2jrUv5mT6e96ZCeBe34DQt3CH0xfk9BQE1xA+oQ3xpyzzBL2wIN504om7DRvgRS//3clkU
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
