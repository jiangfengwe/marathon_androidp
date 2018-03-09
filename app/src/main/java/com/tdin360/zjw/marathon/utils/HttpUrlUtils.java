package com.tdin360.zjw.marathon.utils;

/**
 * http请求接口管理类
 * Created by Administrator on 2016/7/2.
 */
public class HttpUrlUtils {
    //private static final String BASE="http://www.baijar.com/EventAppApi/";
    //动态分享看看有没有EventAppApi。测试一下
    private static final String BASE="http://www.tdin360.com/EventAppApiV2F1/";
    public static final String URL="http://www.tdin360.com";
    public static final String appKey="BJYDAppV-2";
    //验证手机号
    public static final String REGISTER_ONE=BASE+"EventInfo/RegisterStep1";


    //用户注册接口
    public static final String MARATHON_REGISTER= BASE+"RegisterStep1";
    //验证验证码,提交注册信息
    public static final String VALIDATE_CODE=BASE+"RegisterStep2";
    //登录
    public static final String MARATHON_LOGIN=BASE+"Login";
    //第三方登录
    public static final String MARATHON_OTHERLOGIN=BASE+"OtherLogin";
    //第三方绑定手机号验证码
    public static final String OTHER_PHONE_CODE=BASE+"BindPhoneStep1";
    //第三方绑定手机号
    public static final String OTHER_PHONE=BASE+"BindPhoneStep2";
    //找回密码的验证码
    public static final String FIND_PASSWORD_CODE=BASE+"ForgetCustomerPasswordStep1";
    //找回密码的提交
    public static final String FIND_PASSWORD=BASE+"ForgetCustomerPasswordStep2";
    //更新用户基本信息
    public static final String USER_INFO_SEX=BASE+"UploadCustomerBaseMessage";
    //更新用户基本信息
    public static final String CHANGE_HEAD_PIC=BASE+"UploadCustomerAvatar";
    //更新用户昵称
    public static final String CHANGE_NICKNAME=BASE+"UploadCustomerNickName";
    //更新用户签名
    public static final String CHANGE_SIGN=BASE+"UploadCustomerSign";
    //更新用户登录密码
    public static final String CHANGE_PSW=BASE+"UploadCustomerPasswrod";
    //更新用户手机号码:原手机验证码
    public static final String CHANGE_OLD_PHONE_CODE =BASE+"UploadCustomerPhoneStep1";
    //更新用户手机号码
    public static final String CHANGE_PHONE=BASE+"UploadCustomerPhoneStep2";
    //更新用户手机号码:原手机验证码
    public static final String CHANGE_NEW_PHONE_CODE =BASE+"UploadCustomerPhoneStep3";
    //更新用户手机号码
    public static final String CHANGE_NEW_PHONE=BASE+"UploadCustomerPhoneStep4";
    //信息反馈
    public static final String FEEDBACK=BASE+"CreateCustomerFeedback";
    //用户个人中心动态
    public static final String MY_CIRCLE=BASE+"CustomerDynamic";
    //用户个人中心删除动态
    public static final String MY_CIRCLE_DELETE=BASE+"DeleteCustomerDynamic";
    //用户个人中心动态背景图片
    public static final String MY_CIRCLE_PICTURE=BASE+"UploadCustomerDynamicPicture";


    //佰家圈首页(动态首页)
    public static final String CIRCLE=BASE+"BJDynamicHome";
    //动态详情
    public static final String CIRCLE_DETAIL=BASE+"BJDynamicDetail";
    //动态详情评论
    public static final String CIRCLE_DETAIL_COMMENT=BASE+"CommentDynamic";
    //动态详情评论回复
    public static final String CIRCLE_DETAIL_COMMENT_BACK=BASE+"MoreComment";
    //动态点赞
    public static final String CIRCLE_PRAISE=BASE+"TagDynamic";
    //动态点赞列表
    public static final String CIRCLE_PRAISE_LIST=BASE+"BJDynamicTags";
    //动态分享页面
    public static final String CIRCLE_SHARE=BASE+"ShareDynamic";
    //佰家圈发布动态
    public static final String PUBLISH_CIRCLE=BASE+"CreateDynamic";



    //酒店列表
    public static final String HOTEL=BASE+"HotelList";
    //酒店详情
    public static final String HOTEL_DETAIL=BASE+"HotelDetail";
    //生成入住信息、生成入住订单
    public static final String HOTEL_DETAIL_ORDER=BASE+"HotelStayMessage";
    //酒店订单详情页面
    public static final String HOTEL_ORDER_WEBVIEW=BASE+"AppHotelDetail";
    //生成旅游订单
    public static final String TRAVEL_DETAIL_ORDER=BASE+"TravelStayMessage";
    //酒店更多评论
    public static final String HOTEL_MORE_COMMENT=BASE+"HotelEvaluationList";
    //旅游路线列表
    public static final String TRAVEL=BASE+"TravelList";
    //旅游路线详情
    public static final String TRAVEL_DETAIL =BASE+"TravelDetial";
    //旅游路线图片列表
    public static final String TRAVEL_DETAIL_PICTURE =BASE+"TravelPictureList";
    //旅游路线详情中嵌套的信息页面
    public static final String TRAVEL_DETAIL_INFO=BASE+"TravelDetailMessageView";
    //旅游订单详情页面
    public static final String TRAVEL_DETAIL_WEBVIEW=BASE+"AppTravelDetail";
    //酒店更多评论
    public static final String TRAVEL_MORE_COMMENT=BASE+"TravelEvaluationList";
    //支付方法
    public static final String PAY=BASE+"Pay";
    //确认订单是否支付
    public static final String PAY_SURE=BASE+"SureOrder";
    //个人中心旅游订单
    public static final String TRAVEL_ORDER=BASE+"TravelOrderList";
    //个人中心旅游订单详情
    public static final String TRAVEL_ORDER_DETAIL=BASE+"TravelOrderDetail";
    //个人中心取消旅游订单
    public static final String TRAVEL_ORDER_CANCEL=BASE+"CancelTravelOrder";
    //个人中心酒店订单评价
    public static final String Travel_ORDER_COMMENT=BASE+"EvaluateHotelOrder";
    //个人中心酒店订单
    public static final String HOTEL_ORDER=BASE+"HotelOrderList";
    //个人中心酒店订单
    public static final String HOTEL_ORDER_DETAIL=BASE+"HotelOrderDetail";
    //个人中心取消酒店订单
    public static final String HOTEL_ORDER_CANCEL=BASE+"CancelHotelOrder";
    //个人中心酒店订单评价
    public static final String HOTEL_ORDER_COMMENT=BASE+"EvaluateHotelOrder";
    //个人中心酒店订单退款方法
    public static final String HOTEL_ORDER_BACK_MONEY=BASE+"Refund";


    //赛事列表
    public static final String EVENT=BASE+"BJEventList";
    //检查更新接口
    public static final String UPDATE_URL=BASE+"AppUpdate";
    //App下载页面
    public static final String DOWNLOAD_URL=BASE+"AppDownload";
    //系统通知消息详细页面
    public static final String NOTICE_DETAIL_URL=BASE+"SystemNotifation";
    //系统通知消息详细页面
    public static final String ZXING_URL=BASE+"TransitReturn";







   /* //设置密码
    public static final String SET_PASSWORD=BASE+"EventInfo/RegisterStep3";

    //赛事列表接口
    public static final String MARATHON_HOME=BASE+"EventInfo/index";
    //酒店列表
    public static final String HOTEL_LIST=BASE+"eventinfo/GetHotels";
    //酒店详情
    //public static final String HOTEL_DETAIL=BASE+"eventinfo/GetHotelDetail";
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
    //public static final String PAY=BASE+"eventInfo/Pay";
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
    //public static final String UPDATE_URL=BASE+"eventinfo/AppUpdate";

    //免责声明
    public static final String ANNOUNCE=BASE+"EventInfo/Disclaimer?eventId=";*/
}
