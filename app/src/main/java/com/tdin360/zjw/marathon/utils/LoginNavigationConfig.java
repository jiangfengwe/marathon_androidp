package com.tdin360.zjw.marathon.utils;

/**
 * 登录跳转导航配置
 * Created by admin on 17/3/29.
 */

public class LoginNavigationConfig {

    private static NavType navType;

    private static LoginNavigationConfig config;

  public static LoginNavigationConfig instance(){

      if(config==null){

          config = new LoginNavigationConfig();
      }

      return config;

  }

    public NavType getNavType() {
        return navType;
    }

    public void setNavType(NavType navType) {
        this.navType = navType;
    }
}
