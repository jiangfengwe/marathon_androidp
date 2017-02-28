package com.tdin360.zjw.marathon.utils;

/**
 * http请求接口管理类
 * Created by Administrator on 2016/7/2.
 */
public class HttpUrlUtils {
    //赛事列表接口
    public static final String Marath_ALL_Envent="http://byydtk.oicp.net:26211/EventInfo/index";
    //用户注册接口
    public static final String MARATHON_REGISTER="http://byydtk.oicp.net:26211/EventInfo/Register";
    //用户登录接口
    public static final String MARATHON_LOGIN="http://byydtk.oicp.net:26211/EventInfo/Login";
    //赛事首页（详情）数据接口
    public static final String MARATHON_HOME="http://byydtk.oicp.net:26211/EventInfo/CarouselAndSponsor";
    //赛事报名接口
    public static final String MARATHON_SIGNUP="http://byydtk.oicp.net:26211/EventInfo/PersonalRegister";
    //赛事报名查询接口
    public static final String MARATHON_SignUpInfoSEARCH="http://www.baijar.com:8078/MarathonRegisterMobile/QuerySignUpInfo";
    //赛事新闻/公告接口
    public static final String MARATHON_NewsOrNotice="http://byydtk.oicp.net:26211/EventInfo/NewsAndNotice";
   // 赛事新闻公告详情接口
    public static final String EVENT_NEWS_OR_NOTICE_DETAILS="http://byydtk.oicp.net:26211/EventInfo/EventNewsOrNoticeDetail?id=";
    //发送验证码接口
    public static final String SEND_SMS="http://byydtk.oicp.net:26211/eventinfo/SendVerification";
    //修改登录密码接口
    public static final String CHANGE_PASSWORD="http://byydtk.oicp.net:26211/eventInfo/ChangePassword";
    //找回密码接口
    public static final String FIND_PASSWORD="http://byydtk.oicp.net:26211/eventInfo/PasswordRecoveryConfirmPOST";
   //获取个人资料接口
    public static final String GET_MYINFO="http://byydtk.oicp.net:26211/eventInfo/Info";
    //更改个人资料接口
    public static final String CHANGE_MYINFO="http://byydtk.oicp.net:26211/eventInfo/Info";

    public static final String DOWNLOAD_URL="";
}
