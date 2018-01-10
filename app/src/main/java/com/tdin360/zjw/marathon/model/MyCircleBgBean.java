package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2017/12/21.
 */

public class MyCircleBgBean {

    /**
     * state : true
     * message : 修改成功
     * PictureUrl : http://www.baijar.com/content/images/thumbs/0002078.jpeg
     */

    private boolean state;
    private String message;
    private String PictureUrl;

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

    public String getPictureUrl() {
        return PictureUrl;
    }

    public void setPictureUrl(String PictureUrl) {
        this.PictureUrl = PictureUrl;
    }
}
