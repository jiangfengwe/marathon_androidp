package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2018/1/17.
 */

public class WebViewPayBean {

    /**
     * appId : wx56d6dadff22bbf5e
     * nonceStr : tcyVkPMvHisAXII1
     * packageValue : Sign%3DWXPay
     * partnerId : 1406500002
     * prepayId : wx201801171401504a09c776ba0240706032
     * sign : 392DA87AC364353809CF163F551B3E33
     * timeStamp : 1516168910
     */

    private String appId;
    private String nonceStr;
    private String packageValue;
    private int partnerId;
    private String prepayId;
    private String sign;
    private int timeStamp;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }
}
