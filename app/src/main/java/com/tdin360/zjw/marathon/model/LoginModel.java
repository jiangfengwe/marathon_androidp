package com.tdin360.zjw.marathon.model;

/**
 * 登录信息模型
 * Created by admin on 17/2/16.
 */

public class LoginModel {

    private final String nameKEY="name";
    private final String passKey="password";
    private String name;
    private String password;


    public LoginModel(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getNameKEY() {
        return nameKEY;
    }

    public String getPassKey() {
        return passKey;
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
}
