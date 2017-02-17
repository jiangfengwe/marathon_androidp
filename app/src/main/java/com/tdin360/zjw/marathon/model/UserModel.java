package com.tdin360.zjw.marathon.model;

/**
 * 用户信息模型
 * Created by admin on 17/2/16.
 */

public class UserModel  {

    //用户头像
    private String imageUrl;
//    姓名
    private String name;
//    手机号
    private String phone;
//    登录密码
    private String password;
//     邮箱
    private String email;
//    生日
    private String birthday;
//    性别
    private boolean gender;


    public UserModel(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public UserModel(String imageUrl,String phone, String name, String email, String birthday, boolean gender) {
        this.imageUrl = imageUrl;
        this.phone=phone;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


}
