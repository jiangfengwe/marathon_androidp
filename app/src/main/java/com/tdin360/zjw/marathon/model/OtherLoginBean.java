package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2017/12/9.
 */

public class OtherLoginBean {

    /**
     * state : true
     * message : 登陆成功
     * userSecretMessage : psE5YoOh0ehJXYXemd0iEmQ0i5DeNsYQPbNLoWvIA9p4sp5626cBlVoMluN5OqtC/mhRHUrH2yqFcBfAapgkZL4ejrTx1br0awmCLuwfakxzGd1KWCbx1pQf++BbtQTIqW2bXLDlG//MuywOBNHpxbjVxQFT/4pgyUGX88TSGJsUT24d+x+tCU2YIBuxN/bZn84mvdNw0A33hABtTtq2f7cnveDLZtJddJL8nwpm2+AM7mEuEHEUrRIpzf7pjy15I2xASb8OadyC5uzGyS6slA==
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
