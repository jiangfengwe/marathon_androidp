package com.tdin360.zjw.marathon.model;

import java.io.Serializable;
import java.io.StringReader;

/**
 * 报名信息模型
 * Created by Administrator on 2016/7/7.
 */
public class SignUpInfoModel implements Serializable {

    private String imageUrl;//赛事图片
    private String eventId;//赛事id
    private String eventName;//赛事名称
    private String name;//姓名
    private String phone;//电话
    private String email;//邮箱
    private String birthday;//出生日期
    private String idNumber;//证件号码
    private String certificateType;//证件类型
    private boolean gender;//性别
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
    private String orderNum;//订单号
    private String price;//支付金额


    public SignUpInfoModel(String imageUrl,String eventId,String eventName,String name, String phone,String email ,String birthday, String idNumber, String certificateType, boolean gender, String nationality, String province, String city, String county, String attendProject, String clothingSize, String address, String postcode, String urgencyLinkman, String urgencyLinkmanPhone, String attendNumber,boolean isPayed, String createTime,String orderNum,String price) {
        this.imageUrl = imageUrl;
        this.eventId=eventId;
        this.eventName=eventName;
        this.name = name;
        this.phone = phone;
        this.email=email;
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
        this.isPayed = isPayed;
        this.attendNumber = attendNumber;
        this.createTime = createTime;
        this.orderNum=orderNum;
        this.price = price;
    }

    public String getImageUrl(){


        return imageUrl;
    }
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
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

    public String getEmail() {
        return email;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public String getUrgencyLinkmanPhone() {
        return urgencyLinkmanPhone;
    }

    public void setUrgencyLinkmanPhone(String urgencyLinkmanPhone) {
        this.urgencyLinkmanPhone = urgencyLinkmanPhone;
    }

    public String getUrgencyLinkman() {
        return urgencyLinkman;
    }

    public void setUrgencyLinkman(String urgencyLinkman) {
        this.urgencyLinkman = urgencyLinkman;
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

    public String getOrderNum() {
        return orderNum;
    }

    public String getPrice() {
        return price;
    }
}
