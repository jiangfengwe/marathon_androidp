package com.tdin360.zjw.marathon.utils;

/**
 * http请求接口管理类
 * Created by Administrator on 2016/7/2.
 */
public class HttpUrlUtils {


    private static final String BASE="http://www.baijar.com:1004/";

    //用户注册接口
    public static final String MARATHON_REGISTER= BASE+"EventInfo/Register";
    //用户登录接口
    public static final String MARATHON_LOGIN=BASE+"EventInfo/Login";
    //赛事列表接口
    public static final String MARATHON_HOME=BASE+"EventInfo/index";
    //赛事详情数据接口
    public static final String MARATHON_DETAILS=BASE+"EventInfo/CarouselAndSponsor";
    //赛事报名接口
    public static final String MARATHON_SIGNUP=BASE+"EventInfo/PersonalRegister";
    //赛事简介地址
    public static final String MARATHON_INTRO=BASE+"eventinfo/EventInfoDetail";
    //赛事新闻/公告接口
    public static final String MARATHON_NewsOrNotice=BASE+"EventInfo/NewsAndNotice";
   //赛事新闻公告详情接口
    public static final String EVENT_NEWS_OR_NOTICE_DETAILS=BASE+"EventInfo/EventNewsOrNoticeDetail?id=";
    //发送验证码接口
    public static final String SEND_SMS=BASE+"eventinfo/SendVerification";
    //修改登录密码接口
    public static final String CHANGE_PASSWORD=BASE+"eventInfo/ChangePassword";
    //找回密码接口
    public static final String FIND_PASSWORD=BASE+"eventInfo/PasswordRecoveryConfirmPOST";
   //获取个人资料接口
    public static final String GET_MYINFO=BASE+"eventInfo/Info";
    //更改个人资料接口
    public static final String CHANGE_MYINFO=BASE+"eventInfo/Info";
    //我的报名列表
    public static final String MY_SIGNUP_SEARCH=BASE+"eventinfo/AttendCompetitionList";
    //报名详情
    public static final String MY_SIGN_UP_DETAILS=BASE+"eventinfo/AttendCompetitionDetail?applyId=3233";
    //支付订单接口
    public static final String PAY=BASE+"eventInfo/Pay";

    //参赛路线数据接口
    public static final String PROJECT_MAP=BASE+"eventinfo/map";
    //我的物资数据接口
    public static final String MY_GOODS=BASE+"eventinfo/Goods";
    //我的物资详情
    public static  final String MY_GOODS_DETAIL=BASE+"eventinfo/GoodsDetail";
    // 搜索
    public static final String SEARCH=BASE+"eventinfo/Search";
    //头像上传
    public static final String UPLOAD_IMAGE=BASE+"eventinfo/UploadAvatar";


    //安装包更新下载地址
    public static final String DOWNLOAD_URL="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488963955064&di=9956f46ee621f2b42091ce8d2ebf0d4d&imgtype=0&src=http%3A%2F%2Fimg.qumingxing.com%2Fupload%2Fmastermap%2F20160705%2Feyhm6j29.JPG";
}
