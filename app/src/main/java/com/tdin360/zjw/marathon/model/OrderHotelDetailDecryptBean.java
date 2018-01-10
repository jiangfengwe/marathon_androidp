package com.tdin360.zjw.marathon.model;

import java.util.List;

/**
 * Created by jeffery on 2018/1/2.
 */

public class OrderHotelDetailDecryptBean {

    /**
     * model : {"BJHotelOrderModel":{"PayMethod":"","Type":"hotel","HotelName":"国际酒店","HotelRoomName":"豪华单人间","HotelPictureUrl":"http://www.baijar.com/content/images/thumbs/0001044_320.jpeg","Phone":"17621145702","EnterDate":"0001-01-01T00:00:00","EnterDateStr":"2017-12-28","LeaveDate":"0001-01-01T00:00:00","LeaveDateStr":"2017-12-29","TotalMoney":0.02,"RoomNumber":2,"OrderTime":"0001-01-01T00:00:00","OrderTimeStr":"2017-12-29","OrderNo":"201712291116497441925","IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsApplyRefund":false,"ApplyRefundTime":null,"ApplyRefundTimeStr":null,"IsRefund":false,"Status":"1","CustomerId":0,"HotelRoomId":0,"CreateTime":"0001-01-01T00:00:00","Note":null,"Id":2089,"CustomProperties":{}},"BJHotelStayInCustomerListModel":[{"Name":"ttt","IDNumber":null,"OrderId":0,"CreateTime":"0001-01-01T00:00:00","Note":null,"Id":0,"CustomProperties":{}},{"Name":"rr","IDNumber":null,"OrderId":0,"CreateTime":"0001-01-01T00:00:00","Note":null,"Id":0,"CustomProperties":{}},{"Name":"","IDNumber":null,"OrderId":0,"CreateTime":"0001-01-01T00:00:00","Note":null,"Id":0,"CustomProperties":{}}],"Id":0,"CustomProperties":{}}
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
         * BJHotelOrderModel : {"PayMethod":"","Type":"hotel","HotelName":"国际酒店","HotelRoomName":"豪华单人间","HotelPictureUrl":"http://www.baijar.com/content/images/thumbs/0001044_320.jpeg","Phone":"17621145702","EnterDate":"0001-01-01T00:00:00","EnterDateStr":"2017-12-28","LeaveDate":"0001-01-01T00:00:00","LeaveDateStr":"2017-12-29","TotalMoney":0.02,"RoomNumber":2,"OrderTime":"0001-01-01T00:00:00","OrderTimeStr":"2017-12-29","OrderNo":"201712291116497441925","IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsApplyRefund":false,"ApplyRefundTime":null,"ApplyRefundTimeStr":null,"IsRefund":false,"Status":"1","CustomerId":0,"HotelRoomId":0,"CreateTime":"0001-01-01T00:00:00","Note":null,"Id":2089,"CustomProperties":{}}
         * BJHotelStayInCustomerListModel : [{"Name":"ttt","IDNumber":null,"OrderId":0,"CreateTime":"0001-01-01T00:00:00","Note":null,"Id":0,"CustomProperties":{}},{"Name":"rr","IDNumber":null,"OrderId":0,"CreateTime":"0001-01-01T00:00:00","Note":null,"Id":0,"CustomProperties":{}},{"Name":"","IDNumber":null,"OrderId":0,"CreateTime":"0001-01-01T00:00:00","Note":null,"Id":0,"CustomProperties":{}}]
         * Id : 0
         * CustomProperties : {}
         */

        private BJHotelOrderModelBean BJHotelOrderModel;
        private int Id;
        private CustomPropertiesBeanX CustomProperties;
        private java.util.List<BJHotelStayInCustomerListModelBean> BJHotelStayInCustomerListModel;

        public BJHotelOrderModelBean getBJHotelOrderModel() {
            return BJHotelOrderModel;
        }

        public void setBJHotelOrderModel(BJHotelOrderModelBean BJHotelOrderModel) {
            this.BJHotelOrderModel = BJHotelOrderModel;
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

        public List<BJHotelStayInCustomerListModelBean> getBJHotelStayInCustomerListModel() {
            return BJHotelStayInCustomerListModel;
        }

        public void setBJHotelStayInCustomerListModel(List<BJHotelStayInCustomerListModelBean> BJHotelStayInCustomerListModel) {
            this.BJHotelStayInCustomerListModel = BJHotelStayInCustomerListModel;
        }

        public static class BJHotelOrderModelBean{

            /**
             * PayMethod :
             * Type : hotel
             * HotelName : 国际酒店
             * HotelRoomName : 豪华单人间
             * HotelPictureUrl : http://www.baijar.com/content/images/thumbs/0001044_320.jpeg
             * Phone : 17621145702
             * EnterDate : 0001-01-01T00:00:00
             * EnterDateStr : 2017-12-28
             * LeaveDate : 0001-01-01T00:00:00
             * LeaveDateStr : 2017-12-29
             * TotalMoney : 0.02
             * RoomNumber : 2
             * OrderTime : 0001-01-01T00:00:00
             * OrderTimeStr : 2017-12-29
             * OrderNo : 201712291116497441925
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
             * HotelRoomId : 0
             * CreateTime : 0001-01-01T00:00:00
             * Note : null
             * Id : 2089
             * CustomProperties : {}
             */

            private String PayMethod;
            private String Type;
            private String HotelName;
            private String HotelRoomName;
            private String HotelPictureUrl;
            private String Phone;
            private String EnterDate;
            private String EnterDateStr;
            private String LeaveDate;
            private String LeaveDateStr;
            private double TotalMoney;
            private int RoomNumber;
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
            private int HotelRoomId;
            private String CreateTime;
            private Object Note;
            private int Id;
            private CustomPropertiesBean CustomProperties;

            public String getPayMethod() {
                return PayMethod;
            }

            public void setPayMethod(String PayMethod) {
                this.PayMethod = PayMethod;
            }

            public String getType() {
                return Type;
            }

            public void setType(String Type) {
                this.Type = Type;
            }

            public String getHotelName() {
                return HotelName;
            }

            public void setHotelName(String HotelName) {
                this.HotelName = HotelName;
            }

            public String getHotelRoomName() {
                return HotelRoomName;
            }

            public void setHotelRoomName(String HotelRoomName) {
                this.HotelRoomName = HotelRoomName;
            }

            public String getHotelPictureUrl() {
                return HotelPictureUrl;
            }

            public void setHotelPictureUrl(String HotelPictureUrl) {
                this.HotelPictureUrl = HotelPictureUrl;
            }

            public String getPhone() {
                return Phone;
            }

            public void setPhone(String Phone) {
                this.Phone = Phone;
            }

            public String getEnterDate() {
                return EnterDate;
            }

            public void setEnterDate(String EnterDate) {
                this.EnterDate = EnterDate;
            }

            public String getEnterDateStr() {
                return EnterDateStr;
            }

            public void setEnterDateStr(String EnterDateStr) {
                this.EnterDateStr = EnterDateStr;
            }

            public String getLeaveDate() {
                return LeaveDate;
            }

            public void setLeaveDate(String LeaveDate) {
                this.LeaveDate = LeaveDate;
            }

            public String getLeaveDateStr() {
                return LeaveDateStr;
            }

            public void setLeaveDateStr(String LeaveDateStr) {
                this.LeaveDateStr = LeaveDateStr;
            }

            public double getTotalMoney() {
                return TotalMoney;
            }

            public void setTotalMoney(double TotalMoney) {
                this.TotalMoney = TotalMoney;
            }

            public int getRoomNumber() {
                return RoomNumber;
            }

            public void setRoomNumber(int RoomNumber) {
                this.RoomNumber = RoomNumber;
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

            public int getHotelRoomId() {
                return HotelRoomId;
            }

            public void setHotelRoomId(int HotelRoomId) {
                this.HotelRoomId = HotelRoomId;
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
        public static class BJHotelStayInCustomerListModelBean{

            /**
             * Name : ttt
             * IDNumber : null
             * OrderId : 0
             * CreateTime : 0001-01-01T00:00:00
             * Note : null
             * Id : 0
             * CustomProperties : {}
             */

            private String Name;
            private Object IDNumber;
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

            public Object getIDNumber() {
                return IDNumber;
            }

            public void setIDNumber(Object IDNumber) {
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
