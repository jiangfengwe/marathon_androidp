package com.tdin360.zjw.marathon.model;

import java.util.List;

/**
 * Created by jeffery on 2017/12/15.
 */

public class OrderTravelBean {

    /**
     * state : true
     * message : 请求成功
     * model : {"pageIndex":1,"totalPages":4,"BJTravelOrderListModel":[{"PayMethod":null,"Type":null,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0000039.png","StartPlace":"贵阳","EndPlace":"遵义","Phone":null,"StartDate":"/Date(-62135596800000)/","StartDatestr":"2017-12-09 00:00","EndDate":"/Date(-62135596800000)/","EndDatestr":null,"TotalMoney":0.01,"TravelNumber":1,"OrderTime":"/Date(-62135596800000)/","OrderTimeStr":"2017-12-21 15:12","OrderNo":"201712211512307351040","IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsApplyRefund":false,"ApplyRefundTime":null,"ApplyRefundTimeStr":null,"IsRefund":false,"Status":"1","CustomerId":0,"TravelId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":27,"CustomProperties":{}},{"PayMethod":null,"Type":null,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0000038.png","StartPlace":"贵阳","EndPlace":"安顺","Phone":null,"StartDate":"/Date(-62135596800000)/","StartDatestr":"2017-12-08 00:00","EndDate":"/Date(-62135596800000)/","EndDatestr":null,"TotalMoney":0.01,"TravelNumber":1,"OrderTime":"/Date(-62135596800000)/","OrderTimeStr":"2017-12-20 09:44","OrderNo":"201712200944086633139","IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsApplyRefund":false,"ApplyRefundTime":null,"ApplyRefundTimeStr":null,"IsRefund":false,"Status":"1","CustomerId":0,"TravelId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":20,"CustomProperties":{}},{"PayMethod":null,"Type":null,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0000039.png","StartPlace":"贵阳","EndPlace":"遵义","Phone":null,"StartDate":"/Date(-62135596800000)/","StartDatestr":"2017-11-11 00:00","EndDate":"/Date(-62135596800000)/","EndDatestr":null,"TotalMoney":0.01,"TravelNumber":1,"OrderTime":"/Date(-62135596800000)/","OrderTimeStr":"2017-12-19 16:01","OrderNo":"201712191601417056797","IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsApplyRefund":false,"ApplyRefundTime":null,"ApplyRefundTimeStr":null,"IsRefund":false,"Status":"1","CustomerId":0,"TravelId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":19,"CustomProperties":{}},{"PayMethod":null,"Type":null,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0000039.png","StartPlace":"贵阳","EndPlace":"遵义","Phone":null,"StartDate":"/Date(-62135596800000)/","StartDatestr":"2017-10-10 00:00","EndDate":"/Date(-62135596800000)/","EndDatestr":null,"TotalMoney":0.01,"TravelNumber":1,"OrderTime":"/Date(-62135596800000)/","OrderTimeStr":"2017-12-19 15:58","OrderNo":"201712191558384893751","IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsApplyRefund":false,"ApplyRefundTime":null,"ApplyRefundTimeStr":null,"IsRefund":false,"Status":"1","CustomerId":0,"TravelId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":18,"CustomProperties":{}},{"PayMethod":null,"Type":null,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0000038.png","StartPlace":"贵阳","EndPlace":"安顺","Phone":null,"StartDate":"/Date(-62135596800000)/","StartDatestr":"2017-12-12 00:00","EndDate":"/Date(-62135596800000)/","EndDatestr":null,"TotalMoney":0.01,"TravelNumber":1,"OrderTime":"/Date(-62135596800000)/","OrderTimeStr":"2017-12-16 16:02","OrderNo":"201712161602262454721","IsCancel":true,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsApplyRefund":false,"ApplyRefundTime":null,"ApplyRefundTimeStr":null,"IsRefund":false,"Status":"2","CustomerId":0,"TravelId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":17,"CustomProperties":{}}],"Id":0,"CustomProperties":{}}
     */

    private boolean state;
    private String message;
    private ModelBean model;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ModelBean getModel() {
        return model;
    }

    public void setModel(ModelBean model) {
        this.model = model;
    }

    public static class ModelBean{

        /**
         * pageIndex : 1
         * totalPages : 4
         * BJTravelOrderListModel : [{"PayMethod":null,"Type":null,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0000039.png","StartPlace":"贵阳","EndPlace":"遵义","Phone":null,"StartDate":"/Date(-62135596800000)/","StartDatestr":"2017-12-09 00:00","EndDate":"/Date(-62135596800000)/","EndDatestr":null,"TotalMoney":0.01,"TravelNumber":1,"OrderTime":"/Date(-62135596800000)/","OrderTimeStr":"2017-12-21 15:12","OrderNo":"201712211512307351040","IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsApplyRefund":false,"ApplyRefundTime":null,"ApplyRefundTimeStr":null,"IsRefund":false,"Status":"1","CustomerId":0,"TravelId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":27,"CustomProperties":{}},{"PayMethod":null,"Type":null,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0000038.png","StartPlace":"贵阳","EndPlace":"安顺","Phone":null,"StartDate":"/Date(-62135596800000)/","StartDatestr":"2017-12-08 00:00","EndDate":"/Date(-62135596800000)/","EndDatestr":null,"TotalMoney":0.01,"TravelNumber":1,"OrderTime":"/Date(-62135596800000)/","OrderTimeStr":"2017-12-20 09:44","OrderNo":"201712200944086633139","IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsApplyRefund":false,"ApplyRefundTime":null,"ApplyRefundTimeStr":null,"IsRefund":false,"Status":"1","CustomerId":0,"TravelId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":20,"CustomProperties":{}},{"PayMethod":null,"Type":null,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0000039.png","StartPlace":"贵阳","EndPlace":"遵义","Phone":null,"StartDate":"/Date(-62135596800000)/","StartDatestr":"2017-11-11 00:00","EndDate":"/Date(-62135596800000)/","EndDatestr":null,"TotalMoney":0.01,"TravelNumber":1,"OrderTime":"/Date(-62135596800000)/","OrderTimeStr":"2017-12-19 16:01","OrderNo":"201712191601417056797","IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsApplyRefund":false,"ApplyRefundTime":null,"ApplyRefundTimeStr":null,"IsRefund":false,"Status":"1","CustomerId":0,"TravelId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":19,"CustomProperties":{}},{"PayMethod":null,"Type":null,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0000039.png","StartPlace":"贵阳","EndPlace":"遵义","Phone":null,"StartDate":"/Date(-62135596800000)/","StartDatestr":"2017-10-10 00:00","EndDate":"/Date(-62135596800000)/","EndDatestr":null,"TotalMoney":0.01,"TravelNumber":1,"OrderTime":"/Date(-62135596800000)/","OrderTimeStr":"2017-12-19 15:58","OrderNo":"201712191558384893751","IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsApplyRefund":false,"ApplyRefundTime":null,"ApplyRefundTimeStr":null,"IsRefund":false,"Status":"1","CustomerId":0,"TravelId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":18,"CustomProperties":{}},{"PayMethod":null,"Type":null,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0000038.png","StartPlace":"贵阳","EndPlace":"安顺","Phone":null,"StartDate":"/Date(-62135596800000)/","StartDatestr":"2017-12-12 00:00","EndDate":"/Date(-62135596800000)/","EndDatestr":null,"TotalMoney":0.01,"TravelNumber":1,"OrderTime":"/Date(-62135596800000)/","OrderTimeStr":"2017-12-16 16:02","OrderNo":"201712161602262454721","IsCancel":true,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsApplyRefund":false,"ApplyRefundTime":null,"ApplyRefundTimeStr":null,"IsRefund":false,"Status":"2","CustomerId":0,"TravelId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":17,"CustomProperties":{}}]
         * Id : 0
         * CustomProperties : {}
         */

        private int pageIndex;
        private int totalPages;
        private int Id;
        private CustomPropertiesBean CustomProperties;
        private List<BJTravelOrderListModelBean> BJTravelOrderListModel;

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public CustomPropertiesBean getCustomProperties() {
            return CustomProperties;
        }

        public void setCustomProperties(CustomPropertiesBean CustomProperties) {
            this.CustomProperties = CustomProperties;
        }

        public List<BJTravelOrderListModelBean> getBJTravelOrderListModel() {
            return BJTravelOrderListModel;
        }

        public void setBJTravelOrderListModel(List<BJTravelOrderListModelBean> BJTravelOrderListModel) {
            this.BJTravelOrderListModel = BJTravelOrderListModel;
        }

        public static class CustomPropertiesBean {
        }

        public static class BJTravelOrderListModelBean {
            /**
             * PayMethod : null
             * Type : null
             * PictureUrl : http://www.baijar.com/content/images/thumbs/0000039.png
             * StartPlace : 贵阳
             * EndPlace : 遵义
             * Phone : null
             * StartDate : /Date(-62135596800000)/
             * StartDatestr : 2017-12-09 00:00
             * EndDate : /Date(-62135596800000)/
             * EndDatestr : null
             * TotalMoney : 0.01
             * TravelNumber : 1
             * OrderTime : /Date(-62135596800000)/
             * OrderTimeStr : 2017-12-21 15:12
             * OrderNo : 201712211512307351040
             * IsCancel : false
             * CancelTime : null
             * IsPay : false
             * PayTime : null
             * IsUsing : false
             * UsingTime : null
             * IsEvaluate : false
             * EvaluateTime : null
             * IsApplyRefund : false
             * ApplyRefundTime : null
             * ApplyRefundTimeStr : null
             * IsRefund : false
             * Status : 1
             * CustomerId : 0
             * TravelId : 0
             * CreateTime : /Date(-62135596800000)/
             * Note : null
             * Id : 27
             * CustomProperties : {}
             */

            private String PayMethod;
            private Object Type;
            private String PictureUrl;
            private String StartPlace;
            private String EndPlace;
            private String Phone;
            private String StartDate;
            private String StartDatestr;
            private String EndDate;
            private Object EndDatestr;
            private double TotalMoney;
            private int TravelNumber;
            private String OrderTime;
            private String OrderTimeStr;
            private String OrderNo;
            private boolean IsCancel;
            private Object CancelTime;
            private boolean IsPay;
            private Object PayTime;
            private boolean IsUsing;
            private Object UsingTime;
            private boolean IsEvaluate;
            private Object EvaluateTime;
            private boolean IsApplyRefund;
            private Object ApplyRefundTime;
            private Object ApplyRefundTimeStr;
            private boolean IsRefund;
            private String Status;
            private int CustomerId;
            private int TravelId;
            private String CreateTime;
            private Object Note;
            private int Id;
            private CustomPropertiesBeanX CustomProperties;

            public String getPayMethod() {
                return PayMethod;
            }

            public void setPayMethod(String PayMethod) {
                this.PayMethod = PayMethod;
            }

            public Object getType() {
                return Type;
            }

            public void setType(Object Type) {
                this.Type = Type;
            }

            public String getPictureUrl() {
                return PictureUrl;
            }

            public void setPictureUrl(String PictureUrl) {
                this.PictureUrl = PictureUrl;
            }

            public String getStartPlace() {
                return StartPlace;
            }

            public void setStartPlace(String StartPlace) {
                this.StartPlace = StartPlace;
            }

            public String getEndPlace() {
                return EndPlace;
            }

            public void setEndPlace(String EndPlace) {
                this.EndPlace = EndPlace;
            }

            public String getPhone() {
                return Phone;
            }

            public void setPhone(String Phone) {
                this.Phone = Phone;
            }

            public String getStartDate() {
                return StartDate;
            }

            public void setStartDate(String StartDate) {
                this.StartDate = StartDate;
            }

            public String getStartDatestr() {
                return StartDatestr;
            }

            public void setStartDatestr(String StartDatestr) {
                this.StartDatestr = StartDatestr;
            }

            public String getEndDate() {
                return EndDate;
            }

            public void setEndDate(String EndDate) {
                this.EndDate = EndDate;
            }

            public Object getEndDatestr() {
                return EndDatestr;
            }

            public void setEndDatestr(Object EndDatestr) {
                this.EndDatestr = EndDatestr;
            }

            public double getTotalMoney() {
                return TotalMoney;
            }

            public void setTotalMoney(double TotalMoney) {
                this.TotalMoney = TotalMoney;
            }

            public int getTravelNumber() {
                return TravelNumber;
            }

            public void setTravelNumber(int TravelNumber) {
                this.TravelNumber = TravelNumber;
            }

            public String getOrderTime() {
                return OrderTime;
            }

            public void setOrderTime(String OrderTime) {
                this.OrderTime = OrderTime;
            }

            public String getOrderTimeStr() {
                return OrderTimeStr;
            }

            public void setOrderTimeStr(String OrderTimeStr) {
                this.OrderTimeStr = OrderTimeStr;
            }

            public String getOrderNo() {
                return OrderNo;
            }

            public void setOrderNo(String OrderNo) {
                this.OrderNo = OrderNo;
            }

            public boolean isIsCancel() {
                return IsCancel;
            }

            public void setIsCancel(boolean IsCancel) {
                this.IsCancel = IsCancel;
            }

            public Object getCancelTime() {
                return CancelTime;
            }

            public void setCancelTime(Object CancelTime) {
                this.CancelTime = CancelTime;
            }

            public boolean isIsPay() {
                return IsPay;
            }

            public void setIsPay(boolean IsPay) {
                this.IsPay = IsPay;
            }

            public Object getPayTime() {
                return PayTime;
            }

            public void setPayTime(Object PayTime) {
                this.PayTime = PayTime;
            }

            public boolean isIsUsing() {
                return IsUsing;
            }

            public void setIsUsing(boolean IsUsing) {
                this.IsUsing = IsUsing;
            }

            public Object getUsingTime() {
                return UsingTime;
            }

            public void setUsingTime(Object UsingTime) {
                this.UsingTime = UsingTime;
            }

            public boolean isIsEvaluate() {
                return IsEvaluate;
            }

            public void setIsEvaluate(boolean IsEvaluate) {
                this.IsEvaluate = IsEvaluate;
            }

            public Object getEvaluateTime() {
                return EvaluateTime;
            }

            public void setEvaluateTime(Object EvaluateTime) {
                this.EvaluateTime = EvaluateTime;
            }

            public boolean isIsApplyRefund() {
                return IsApplyRefund;
            }

            public void setIsApplyRefund(boolean IsApplyRefund) {
                this.IsApplyRefund = IsApplyRefund;
            }

            public Object getApplyRefundTime() {
                return ApplyRefundTime;
            }

            public void setApplyRefundTime(Object ApplyRefundTime) {
                this.ApplyRefundTime = ApplyRefundTime;
            }

            public Object getApplyRefundTimeStr() {
                return ApplyRefundTimeStr;
            }

            public void setApplyRefundTimeStr(Object ApplyRefundTimeStr) {
                this.ApplyRefundTimeStr = ApplyRefundTimeStr;
            }

            public boolean isIsRefund() {
                return IsRefund;
            }

            public void setIsRefund(boolean IsRefund) {
                this.IsRefund = IsRefund;
            }

            public String getStatus() {
                return Status;
            }

            public void setStatus(String Status) {
                this.Status = Status;
            }

            public int getCustomerId() {
                return CustomerId;
            }

            public void setCustomerId(int CustomerId) {
                this.CustomerId = CustomerId;
            }

            public int getTravelId() {
                return TravelId;
            }

            public void setTravelId(int TravelId) {
                this.TravelId = TravelId;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }

            public Object getNote() {
                return Note;
            }

            public void setNote(Object Note) {
                this.Note = Note;
            }

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public CustomPropertiesBeanX getCustomProperties() {
                return CustomProperties;
            }

            public void setCustomProperties(CustomPropertiesBeanX CustomProperties) {
                this.CustomProperties = CustomProperties;
            }

            public static class CustomPropertiesBeanX {
            }
        }
    }
}
