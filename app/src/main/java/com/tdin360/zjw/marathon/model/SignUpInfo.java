package com.tdin360.zjw.marathon.model;

import java.io.Serializable;
import java.io.StringReader;

/**
 * 报名信息模型
 * Created by Administrator on 2016/7/7.
 */
public class SignUpInfo implements Serializable {

    private String name;//姓名
    private String phone;//电话
    private String birthday;//出生日期
    private String idNumber;//证件号码
    private String certificateType;//证件类型
    private String gender;//性别
    private String nationality;//国家
    private String province;//省/市
    private String city;//城市
    private String county;//区县
    private String attendProject;//参赛项目
    private String clothingSize;//服装尺码
    private String address;//现居地址
    private String postcode;//邮政编码
    private String urgencyLinkman;//紧急联系人姓名
    private String urgencyLinkmanPhone;//紧急联系人电话
    private String createTime;//报名时间
    private boolean isPayed;//是否支付
    private String attendNumber;//参赛号码
    private String applyNature;//报名方式（单人、团体）

    //支付宝配置参数
    private String service;
    private String partner;
    private String _input_charset;
    private String sign_type;
    private String notify_url;
    private String out_trade_no;
    private String subject;
    private String payment_type;
    private String seller_id;
    private String total_fee;
    private String body;
    private String sign;

    //说明
    private String reason;

    public SignUpInfo() {

    }

    public SignUpInfo(String name, String phone, String birthday, String idNumber, String certificateType, String gender, String nationality, String province, String city, String county, String attendProject, String clothingSize, String address, String postcode, String urgencyLinkman, String urgencyLinkmanPhone, String createTime, boolean isPayed, String attendNumber, String applyNature, String service, String partner, String _input_charset, String sign_type, String notify_url, String out_trade_no, String subject, String payment_type, String seller_id, String total_fee, String body, String sign) {
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
        this.idNumber = idNumber;
        this.certificateType = certificateType;
        this.gender = gender;
        this.nationality = nationality;
        this.province = province;
        this.city = city;
        this.county = county;
        this.attendProject = attendProject;
        this.clothingSize = clothingSize;
        this.address = address;
        this.postcode = postcode;
        this.urgencyLinkman = urgencyLinkman;
        this.urgencyLinkmanPhone = urgencyLinkmanPhone;
        this.createTime = createTime;
        this.isPayed = isPayed;
        this.attendNumber = attendNumber;
        this.applyNature = applyNature;
        this.service = service;
        this.partner = partner;
        this._input_charset = _input_charset;
        this.sign_type = sign_type;
        this.notify_url = notify_url;
        this.out_trade_no = out_trade_no;
        this.subject = subject;
        this.payment_type = payment_type;
        this.seller_id = seller_id;
        this.total_fee = total_fee;
        this.body = body;
        this.sign = sign;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAttendProject() {
        return attendProject;
    }

    public void setAttendProject(String attendProject) {
        this.attendProject = attendProject;
    }

    public String getClothingSize() {
        return clothingSize;
    }

    public void setClothingSize(String clothingSize) {
        this.clothingSize = clothingSize;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getUrgencyLinkman() {
        return urgencyLinkman;
    }

    public void setUrgencyLinkman(String urgencyLinkman) {
        this.urgencyLinkman = urgencyLinkman;
    }

    public String getUrgencyLinkmanPhone() {
        return urgencyLinkmanPhone;
    }

    public void setUrgencyLinkmanPhone(String urgencyLinkmanPhone) {
        this.urgencyLinkmanPhone = urgencyLinkmanPhone;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public String getAttendNumber() {
        return attendNumber;
    }

    public void setAttendNumber(String attendNumber) {
        this.attendNumber = attendNumber;
    }

    public String getApplyNature() {
        return applyNature;
    }

    public void setApplyNature(String applyNature) {
        this.applyNature = applyNature;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String get_input_charset() {
        return _input_charset;
    }

    public void set_input_charset(String _input_charset) {
        this._input_charset = _input_charset;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
