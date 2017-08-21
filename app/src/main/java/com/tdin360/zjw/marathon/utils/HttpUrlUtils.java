package com.tdin360.zjw.marathon.utils;

/**
 * http请求接口管理类
 * Created by Administrator on 2016/7/2.
 */
public class HttpUrlUtils {


    private static final String BASE="http://shop.baijar.com/";
    public static final String appKey="eventkeyfdsfds520tdzh123456";

    //验证手机号
    public static final String REGISTER_ONE=BASE+"EventInfo/RegisterStep1";
    //用户注册接口
    public static final String MARATHON_REGISTER= BASE+"EventInfo/Register";
    //验证验证码
    public static final String VALIDATE_CODE=BASE+"EventInfo/RegisterStep2";
    //设置密码
    public static final String SET_PASSWORD=BASE+"EventInfo/RegisterStep3";
    //登录
    public static final String MARATHON_LOGIN=BASE+"EventInfo/Login";
    //赛事列表接口
    public static final String MARATHON_HOME=BASE+"EventInfo/index";
    //酒店列表
    public static final String HOTEL_LIST=BASE+"eventinfo/GetHotels";
    //酒店详情
    public static final String HOTEL_DETAIL=BASE+"eventinfo/GetHotelDetail";
    //酒店下单
    public static final String HOTEL_BUY=BASE+"eventinfo/ReservationHotel";
    //酒店预订支付
    public static final String HOTEL_PAY= BASE+"eventinfo/HotelPay";
    //我的预订
    public static final String MY_HOTEL_ORDER=BASE+"eventinfo/MyReservation";
    //酒店预订订单详情
    public static final String HOTEL_ORDER_DETAILS=BASE+"eventinfo/ReservationDetail";
    //取消订单
    public static final String CANCEL_HOTEL_ORDER=BASE+"eventinfo/CancleOrder";

    //赛事详情数据接口
    public static final String MARATHON_DETAILS=BASE+"EventInfo/CarouselAndSponsor";
    //赛事报名接口
    public static final String MARATHON_SIGNUP= BASE+"EventInfo/PersonalRegister";
    //赛事简介地址
    public static final String MARATHON_INTRO=BASE+"eventInfo/EventInfoDetail";
    //赛事新闻/公告接口
    public static final String MARATHON_NewsOrNotice=BASE+"EventInfo/NewsAndNotice";
   //赛事新闻公告详情接口
    public static final String EVENT_NEWS_OR_NOTICE_DETAILS=BASE+"EventInfo/EventNewsOrNoticeDetail?id=";
    //发送验证码接口
    public static final String SEND_SMS=BASE+"eventInfo/SendVerification";
    //修改登录密码接口
    public static final String CHANGE_PASSWORD=BASE+"eventInfo/ChangePassword";
    //找回密码接口
    public static final String FIND_PASSWORD=BASE+"eventInfo/PasswordRecoveryConfirmPOST";
   //获取个人资料接口
    public static final String GET_MYINFO=BASE+"eventInfo/Info";
    //更改个人资料接口
    public static final String CHANGE_MYINFO=BASE+"eventInfo/Info";
    //我的报名列表
    public static final String MY_SIGNUP_SEARCH=BASE+"eventInfo/AttendCompetitionList";
    //报名详情接口
    public static final String MY_SIGN_UP_DETAILS=BASE+"eventInfo/AttendCompetitionDetail";
    //成绩查询接口
    public static final String MARK_SEARCH=BASE+"eventInfo/CompetitionGrade";
    //支付订单接口
    public static final String PAY=BASE+"eventInfo/Pay";
    //参赛路线数据接口
    public static final String PROJECT_MAP=BASE+"eventInfo/map";
    //我的物资数据接口
    public static final String MY_GOODS=BASE+"eventInfo/Goods";
    //我的物资详情接口
    public static  final String MY_GOODS_DETAIL=BASE+"eventInfo/GoodsDetail";
    //确认领取物资接口
    public static final String CONFIRM_GOODS=BASE+"eventInfo/SureReceiveGoods";
    //搜索接口
    public static final String SEARCH=BASE+"eventInfo/Search";
    //头像上传接口
    public static final String UPLOAD_IMAGE=BASE+"eventInfo/UploadAvatar";
    //支付验证接口
    public static final String CHECKED_PAY_STATUS=BASE+"eventInfo/ConfirmPay";
    //获取意见反馈列表接口
    public static final String FEED_LIST=BASE+"eventInfo/FeedbackList";
    //提交反馈接口
    public static final String ADDFEED_INFO=BASE+"eventInfo/FeedBackContent";
    //检查更新接口
    public static final String UPDATE_URL=BASE+"eventinfo/AppUpdate";

    //免责声明
    public static final String ANNOUNCE=BASE+"EventInfo/Disclaimer?eventId=";
}
