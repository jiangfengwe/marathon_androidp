package com.tdin360.zjw.marathon;

import com.tdin360.zjw.marathon.model.HHDetail;
import com.tdin360.zjw.marathon.model.HotelDetailBean;
import com.tdin360.zjw.marathon.model.HotelOrderInfoBean;
import com.tdin360.zjw.marathon.model.OrderHotelBean;
import com.tdin360.zjw.marathon.model.OrderTravelBean;
import com.tdin360.zjw.marathon.model.OtherLoginUserInfoBean;
import com.tdin360.zjw.marathon.model.TravelDetailBean;
import com.tdin360.zjw.marathon.model.TravelOrderInfoBean;
import com.tdin360.zjw.marathon.model.TravelPictureBean;

import java.util.List;

/**
 * Created by jeffery on 2017/12/13.
 */

public class SingleClass {
    private String eventId;
    private String hotelId;
    private String travelId;
    private String orderHotel;

    public String getOrderHotel() {
        return orderHotel;
    }

    public void setOrderHotel(String orderHotel) {
        this.orderHotel = orderHotel;
    }

    public String getTravelId() {
        return travelId;
    }

    public void setTravelId(String travelId) {
        this.travelId = travelId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    private static SingleClass singleClass=new SingleClass();
    public static SingleClass getInstance(){
        return singleClass;
    }
    public void clearAll() {
        singleClass = new SingleClass();
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    //酒店图集
    private List<HHDetail.ModelBean.BJHotelPictureListModelBean> bjHotelPictureListModel;
    private HHDetail.ModelBean.BJHotelRoomListModelBean bjHotelRoomListModelBean;
    //旅游图集
    private List<TravelPictureBean.ModelBean.BJTravelPictureListModelBean> bjTravelPictureListModel;
    //酒店提交订单
    private HotelOrderInfoBean.ModelBean.BJHotelOrderModelBean bjHotelOrderModel;
    private List<HotelOrderInfoBean.ModelBean.BJHotelStayInCustomerListModelBean> bjHotelStayInCustomerListModel;
    //酒店日期
    private List<HotelDetailBean.ModelBean.ApiHotelMonthDateList> apiHotelMonthDateList;
    private List<HHDetail.ModelBean.ApiHotelMonthDateListBean> apiHotelMonthDateList1;

    public List<HHDetail.ModelBean.ApiHotelMonthDateListBean> getApiHotelMonthDateList1() {
        return apiHotelMonthDateList1;
    }

    public void setApiHotelMonthDateList1(List<HHDetail.ModelBean.ApiHotelMonthDateListBean> apiHotelMonthDateList1) {
        this.apiHotelMonthDateList1 = apiHotelMonthDateList1;
    }

    //旅游日期
    private List<TravelDetailBean.ModelBean.ApiTravelMonthDateListBean> apiTravelMonthDateList;

    //旅游提交订单
    private TravelOrderInfoBean.ModelBean.BJTravelOrderModelBean bjTravelOrderModel;
    private List<TravelOrderInfoBean.ModelBean.BJTravelStayInCustomerListModelBean> bjTravelStayInCustomerListModel;
    //我的订单，酒店订单详情
    private OrderHotelBean.ModelBean.BJHotelOrderListModelBean bjHotelOrderListModelBean;
    //我的订单，旅游订单详情
    private OrderTravelBean.ModelBean.BJTravelOrderListModelBean bjTravelOrderListModelBean;

    private OtherLoginUserInfoBean.UserBean user;

    public List<HotelDetailBean.ModelBean.ApiHotelMonthDateList> getApiHotelMonthDateList() {
        return apiHotelMonthDateList;
    }

    public void setApiHotelMonthDateList(List<HotelDetailBean.ModelBean.ApiHotelMonthDateList> apiHotelMonthDateList) {
        this.apiHotelMonthDateList = apiHotelMonthDateList;
    }

    public OtherLoginUserInfoBean.UserBean getUser() {
        return user;
    }

    public void setUser(OtherLoginUserInfoBean.UserBean user) {
        this.user = user;
    }

    public List<TravelPictureBean.ModelBean.BJTravelPictureListModelBean> getBjTravelPictureListModel() {
        return bjTravelPictureListModel;
    }

    public void setBjTravelPictureListModel(List<TravelPictureBean.ModelBean.BJTravelPictureListModelBean> bjTravelPictureListModel) {
        this.bjTravelPictureListModel = bjTravelPictureListModel;
    }

    public List<TravelDetailBean.ModelBean.ApiTravelMonthDateListBean> getApiTravelMonthDateList() {
        return apiTravelMonthDateList;
    }

    public void setApiTravelMonthDateList(List<TravelDetailBean.ModelBean.ApiTravelMonthDateListBean> apiTravelMonthDateList) {
        this.apiTravelMonthDateList = apiTravelMonthDateList;
    }

    public OrderHotelBean.ModelBean.BJHotelOrderListModelBean getBjHotelOrderListModelBean() {
        return bjHotelOrderListModelBean;
    }

    public void setBjHotelOrderListModelBean(OrderHotelBean.ModelBean.BJHotelOrderListModelBean bjHotelOrderListModelBean) {
        this.bjHotelOrderListModelBean = bjHotelOrderListModelBean;
    }

    public OrderTravelBean.ModelBean.BJTravelOrderListModelBean getBjTravelOrderListModelBean() {
        return bjTravelOrderListModelBean;
    }

    public void setBjTravelOrderListModelBean(OrderTravelBean.ModelBean.BJTravelOrderListModelBean bjTravelOrderListModelBean) {
        this.bjTravelOrderListModelBean = bjTravelOrderListModelBean;
    }

    public TravelOrderInfoBean.ModelBean.BJTravelOrderModelBean getBjTravelOrderModel() {
        return bjTravelOrderModel;
    }

    public void setBjTravelOrderModel(TravelOrderInfoBean.ModelBean.BJTravelOrderModelBean bjTravelOrderModel) {
        this.bjTravelOrderModel = bjTravelOrderModel;
    }

    public List<TravelOrderInfoBean.ModelBean.BJTravelStayInCustomerListModelBean> getBjTravelStayInCustomerListModel() {
        return bjTravelStayInCustomerListModel;
    }

    public void setBjTravelStayInCustomerListModel(List<TravelOrderInfoBean.ModelBean.BJTravelStayInCustomerListModelBean> bjTravelStayInCustomerListModel) {
        this.bjTravelStayInCustomerListModel = bjTravelStayInCustomerListModel;
    }

    public HotelOrderInfoBean.ModelBean.BJHotelOrderModelBean getBjHotelOrderModel() {
        return bjHotelOrderModel;
    }

    public void setBjHotelOrderModel(HotelOrderInfoBean.ModelBean.BJHotelOrderModelBean bjHotelOrderModel) {
        this.bjHotelOrderModel = bjHotelOrderModel;
    }

    public List<HotelOrderInfoBean.ModelBean.BJHotelStayInCustomerListModelBean> getBjHotelStayInCustomerListModel() {
        return bjHotelStayInCustomerListModel;
    }

    public void setBjHotelStayInCustomerListModel(List<HotelOrderInfoBean.ModelBean.BJHotelStayInCustomerListModelBean> bjHotelStayInCustomerListModel) {
        this.bjHotelStayInCustomerListModel = bjHotelStayInCustomerListModel;
    }

  /*  public List<TravelDetailBean.ModelBean.BJTravelPictureListModelBean> getBjTravelPictureListModel() {
        return bjTravelPictureListModel;
    }

    public void setBjTravelPictureListModel(List<TravelDetailBean.ModelBean.BJTravelPictureListModelBean> bjTravelPictureListModel) {
        this.bjTravelPictureListModel = bjTravelPictureListModel;
    }*/

    public HHDetail.ModelBean.BJHotelRoomListModelBean getBjHotelRoomListModelBean() {
        return bjHotelRoomListModelBean;
    }

    public void setBjHotelRoomListModelBean(HHDetail.ModelBean.BJHotelRoomListModelBean bjHotelRoomListModelBean) {
        this.bjHotelRoomListModelBean = bjHotelRoomListModelBean;
    }

    public List<HHDetail.ModelBean.BJHotelPictureListModelBean> getBjHotelPictureListModel() {
        return bjHotelPictureListModel;
    }

    public void setBjHotelPictureListModel(List<HHDetail.ModelBean.BJHotelPictureListModelBean> bjHotelPictureListModel) {
        this.bjHotelPictureListModel = bjHotelPictureListModel;
    }
}
