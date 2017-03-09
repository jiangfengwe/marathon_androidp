package com.tdin360.zjw.marathon.model;

/**
 * 登录信息模型
 * Created by admin on 17/2/16.
 */

public class LoginModel {

    public static final String nameKEY="name";
    public static final String passKey="password";
    public static final String imageKey="image";

    private String name;
    private String password;
    private String imageUrl;



    public LoginModel(String name, String password,String imageUrl) {
        this.name = name;
        this.password = password;
        this.imageUrl = imageUrl;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
