package com.tdin360.zjw.marathon.model;

import java.util.List;

/**
 * Created by jeffery on 2017/12/14.
 */

public class TravelOrderInfoBean {

    /**
     * model : {"BJTravelOrderModel":{"PayMethod":null,"Type":null,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0004683_320.jpeg","StartPlace":"贵阳","EndPlace":"遵义","Phone":"17621145702","StartDate":"0001-01-01T00:00:00","StartDatestr":"2018-02-22 00:00","EndDate":"0001-01-01T00:00:00","EndDatestr":"2018-02-22 00:00","TotalMoney":0.01,"TravelNumber":1,"OrderTime":"0001-01-01T00:00:00","OrderTimeStr":"2018-01-26","OrderNo":"201801261656511462689","IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsApplyRefund":false,"ApplyRefundTime":null,"ApplyRefundTimeStr":null,"IsRefund":false,"Status":"1","CustomerId":0,"TravelId":0,"CreateTime":"0001-01-01T00:00:00","Note":null,"Id":1079,"CustomProperties":{}},"BJTravelStayInCustomerListModel":[{"Name":"","IDNumber":"","OrderId":0,"CreateTime":"0001-01-01T00:00:00","Note":null,"Id":0,"CustomProperties":{}}],"Id":0,"CustomProperties":{}}
     */

    private ModelBean model;

    public ModelBean getModel() {
        return model;
    }

    public void setModel(ModelBean model) {
        this.model = model;
    }

    public static class ModelBean{

        /**
         * BJTravelOrderModel : {"PayMethod":null,"Type":null,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0004683_320.jpeg","StartPlace":"贵阳","EndPlace":"遵义","Phone":"17621145702","StartDate":"0001-01-01T00:00:00","StartDatestr":"2018-02-22 00:00","EndDate":"0001-01-01T00:00:00","EndDatestr":"2018-02-22 00:00","TotalMoney":0.01,"TravelNumber":1,"OrderTime":"0001-01-01T00:00:00","OrderTimeStr":"2018-01-26","OrderNo":"201801261656511462689","IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsApplyRefund":false,"ApplyRefundTime":null,"ApplyRefundTimeStr":null,"IsRefund":false,"Status":"1","CustomerId":0,"TravelId":0,"CreateTime":"0001-01-01T00:00:00","Note":null,"Id":1079,"CustomProperties":{}}
         * BJTravelStayInCustomerListModel : [{"Name":"","IDNumber":"","OrderId":0,"CreateTime":"0001-01-01T00:00:00","Note":null,"Id":0,"CustomProperties":{}}]
         * Id : 0
         * CustomProperties : {}
         */

        private BJTravelOrderModelBean BJTravelOrderModel;
        private int Id;
        private CustomPropertiesBeanX CustomProperties;
        private List<BJTravelStayInCustomerListModelBean> BJTravelStayInCustomerListModel;

        public BJTravelOrderModelBean getBJTravelOrderModel() {
            return BJTravelOrderModel;
        }

        public void setBJTravelOrderModel(BJTravelOrderModelBean BJTravelOrderModel) {
            this.BJTravelOrderModel = BJTravelOrderModel;
        }

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public CustomPropertiesBeanX getCustomProperties() {
            return CustomProperties;
        }

        public void setCustomProperties(CustomPropertiesBeanX customProperties) {
            CustomProperties = customProperties;
        }

        public List<BJTravelStayInCustomerListModelBean> getBJTravelStayInCustomerListModel() {
            return BJTravelStayInCustomerListModel;
        }

        public void setBJTravelStayInCustomerListModel(List<BJTravelStayInCustomerListModelBean> BJTravelStayInCustomerListModel) {
            this.BJTravelStayInCustomerListModel = BJTravelStayInCustomerListModel;
        }

        public static class BJTravelOrderModelBean{

            /**
             * PayMethod : null
             * Type : null
             * PictureUrl : http://www.baijar.com/content/images/thumbs/0004683_320.jpeg
             * StartPlace : 贵阳
             * EndPlace : 遵义
             * Phone : 17621145702
             * StartDate : 0001-01-01T00:00:00
             * StartDatestr : 2018-02-22 00:00
             * EndDate : 0001-01-01T00:00:00
             * EndDatestr : 2018-02-22 00:00
             * TotalMoney : 0.01
             * TravelNumber : 1
             * OrderTime : 0001-01-01T00:00:00
             * OrderTimeStr : 2018-01-26
             * OrderNo : 201801261656511462689
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
             * CreateTime : 0001-01-01T00:00:00
             * Note : null
             * Id : 1079
             * CustomProperties : {}
             */

            private Object PayMethod;
            private Object Type;
            private String PictureUrl;
            private String StartPlace;
            private String EndPlace;
            private String Phone;
            private String StartDate;
            private String StartDatestr;
            private String EndDate;
            private String EndDatestr;
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
            private CustomPropertiesBean CustomProperties;

            public Object getPayMethod() {
                return PayMethod;
            }

            public void setPayMethod(Object PayMethod) {
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

            public String getEndDatestr() {
                return EndDatestr;
            }

            public void setEndDatestr(String EndDatestr) {
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

            public CustomPropertiesBean getCustomProperties() {
                return CustomProperties;
            }

            public void setCustomProperties(CustomPropertiesBean CustomProperties) {
                this.CustomProperties = CustomProperties;
            }

            public static class CustomPropertiesBean {
            }
        }
        public static class CustomPropertiesBeanX{}
        public static class BJTravelStayInCustomerListModelBean{

            /**
             * Name :
             * IDNumber :
             * OrderId : 0
             * CreateTime : 0001-01-01T00:00:00
             * Note : null
             * Id : 0
             * CustomProperties : {}
             */

            private String Name;
            private String IDNumber;
            private int OrderId;
            private String CreateTime;
            private Object Note;
            private int Id;
            private CustomPropertiesBean CustomProperties;

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getIDNumber() {
                return IDNumber;
            }

            public void setIDNumber(String IDNumber) {
                this.IDNumber = IDNumber;
            }

            public int getOrderId() {
                return OrderId;
            }

            public void setOrderId(int OrderId) {
                this.OrderId = OrderId;
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

            public CustomPropertiesBean getCustomProperties() {
                return CustomProperties;
            }

            public void setCustomProperties(CustomPropertiesBean CustomProperties) {
                this.CustomProperties = CustomProperties;
            }

            public static class CustomPropertiesBean {
            }
        }
    }
}
