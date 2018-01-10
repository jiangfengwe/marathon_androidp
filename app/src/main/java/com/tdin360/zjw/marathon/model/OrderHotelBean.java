package com.tdin360.zjw.marathon.model;

import java.util.List;

/**
 * Created by jeffery on 2017/12/15.
 */

public class OrderHotelBean {

    /**
     * state : true
     * message : 请求成功
     * model : {"pageIndex":1,"totalPages":2,"BJHotelOrderListModel":[{"PayMethod":null,"Type":null,"HotelName":"国际酒店","HotelRoomName":"豪华单人间","HotelPictureUrl":null,"Phone":null,"EnterDate":"/Date(-62135596800000)/","EnterDateStr":null,"LeaveDate":"/Date(-62135596800000)/","LeaveDateStr":null,"TotalMoney":0.01,"RoomNumber":1,"OrderTime":"/Date(-62135596800000)/","OrderTimeStr":"2017-12-14 17:20","OrderNo":null,"IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsRefund":false,"Status":"1","CustomerId":0,"HotelRoomId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":1012,"CustomProperties":{}},{"PayMethod":null,"Type":null,"HotelName":"国际酒店","HotelRoomName":"普通单人间","HotelPictureUrl":null,"Phone":null,"EnterDate":"/Date(-62135596800000)/","EnterDateStr":null,"LeaveDate":"/Date(-62135596800000)/","LeaveDateStr":null,"TotalMoney":0.01,"RoomNumber":1,"OrderTime":"/Date(-62135596800000)/","OrderTimeStr":"2017-12-14 17:16","OrderNo":null,"IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsRefund":false,"Status":"1","CustomerId":0,"HotelRoomId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":1011,"CustomProperties":{}},{"PayMethod":null,"Type":null,"HotelName":"国际酒店","HotelRoomName":"豪华单人间","HotelPictureUrl":null,"Phone":null,"EnterDate":"/Date(-62135596800000)/","EnterDateStr":null,"LeaveDate":"/Date(-62135596800000)/","LeaveDateStr":null,"TotalMoney":0.01,"RoomNumber":1,"OrderTime":"/Date(-62135596800000)/","OrderTimeStr":"2017-12-14 15:56","OrderNo":null,"IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsRefund":false,"Status":"1","CustomerId":0,"HotelRoomId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":1010,"CustomProperties":{}},{"PayMethod":null,"Type":null,"HotelName":"国际酒店","HotelRoomName":"豪华单人间","HotelPictureUrl":null,"Phone":null,"EnterDate":"/Date(-62135596800000)/","EnterDateStr":null,"LeaveDate":"/Date(-62135596800000)/","LeaveDateStr":null,"TotalMoney":0.01,"RoomNumber":1,"OrderTime":"/Date(-62135596800000)/","OrderTimeStr":"2017-12-14 15:25","OrderNo":null,"IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsRefund":false,"Status":"1","CustomerId":0,"HotelRoomId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":1008,"CustomProperties":{}},{"PayMethod":null,"Type":null,"HotelName":"国际酒店","HotelRoomName":"豪华单人间","HotelPictureUrl":null,"Phone":null,"EnterDate":"/Date(-62135596800000)/","EnterDateStr":null,"LeaveDate":"/Date(-62135596800000)/","LeaveDateStr":null,"TotalMoney":0.01,"RoomNumber":1,"OrderTime":"/Date(-62135596800000)/","OrderTimeStr":"2017-12-14 15:20","OrderNo":null,"IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsRefund":false,"Status":"1","CustomerId":0,"HotelRoomId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":1006,"CustomProperties":{}}],"Id":0,"CustomProperties":{}}
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
         * totalPages : 2
         * BJHotelOrderListModel : [{"PayMethod":null,"Type":null,"HotelName":"国际酒店","HotelRoomName":"豪华单人间","HotelPictureUrl":null,"Phone":null,"EnterDate":"/Date(-62135596800000)/","EnterDateStr":null,"LeaveDate":"/Date(-62135596800000)/","LeaveDateStr":null,"TotalMoney":0.01,"RoomNumber":1,"OrderTime":"/Date(-62135596800000)/","OrderTimeStr":"2017-12-14 17:20","OrderNo":null,"IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsRefund":false,"Status":"1","CustomerId":0,"HotelRoomId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":1012,"CustomProperties":{}},{"PayMethod":null,"Type":null,"HotelName":"国际酒店","HotelRoomName":"普通单人间","HotelPictureUrl":null,"Phone":null,"EnterDate":"/Date(-62135596800000)/","EnterDateStr":null,"LeaveDate":"/Date(-62135596800000)/","LeaveDateStr":null,"TotalMoney":0.01,"RoomNumber":1,"OrderTime":"/Date(-62135596800000)/","OrderTimeStr":"2017-12-14 17:16","OrderNo":null,"IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsRefund":false,"Status":"1","CustomerId":0,"HotelRoomId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":1011,"CustomProperties":{}},{"PayMethod":null,"Type":null,"HotelName":"国际酒店","HotelRoomName":"豪华单人间","HotelPictureUrl":null,"Phone":null,"EnterDate":"/Date(-62135596800000)/","EnterDateStr":null,"LeaveDate":"/Date(-62135596800000)/","LeaveDateStr":null,"TotalMoney":0.01,"RoomNumber":1,"OrderTime":"/Date(-62135596800000)/","OrderTimeStr":"2017-12-14 15:56","OrderNo":null,"IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsRefund":false,"Status":"1","CustomerId":0,"HotelRoomId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":1010,"CustomProperties":{}},{"PayMethod":null,"Type":null,"HotelName":"国际酒店","HotelRoomName":"豪华单人间","HotelPictureUrl":null,"Phone":null,"EnterDate":"/Date(-62135596800000)/","EnterDateStr":null,"LeaveDate":"/Date(-62135596800000)/","LeaveDateStr":null,"TotalMoney":0.01,"RoomNumber":1,"OrderTime":"/Date(-62135596800000)/","OrderTimeStr":"2017-12-14 15:25","OrderNo":null,"IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsRefund":false,"Status":"1","CustomerId":0,"HotelRoomId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":1008,"CustomProperties":{}},{"PayMethod":null,"Type":null,"HotelName":"国际酒店","HotelRoomName":"豪华单人间","HotelPictureUrl":null,"Phone":null,"EnterDate":"/Date(-62135596800000)/","EnterDateStr":null,"LeaveDate":"/Date(-62135596800000)/","LeaveDateStr":null,"TotalMoney":0.01,"RoomNumber":1,"OrderTime":"/Date(-62135596800000)/","OrderTimeStr":"2017-12-14 15:20","OrderNo":null,"IsCancel":false,"CancelTime":null,"IsPay":false,"PayTime":null,"IsUsing":false,"UsingTime":null,"IsEvaluate":false,"EvaluateTime":null,"IsRefund":false,"Status":"1","CustomerId":0,"HotelRoomId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":1006,"CustomProperties":{}}]
         * Id : 0
         * CustomProperties : {}
         */

        private int pageIndex;
        private int totalPages;
        private int Id;
        private CustomPropertiesBean CustomProperties;
        private List<BJHotelOrderListModelBean> BJHotelOrderListModel;

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

        public List<BJHotelOrderListModelBean> getBJHotelOrderListModel() {
            return BJHotelOrderListModel;
        }

        public void setBJHotelOrderListModel(List<BJHotelOrderListModelBean> BJHotelOrderListModel) {
            this.BJHotelOrderListModel = BJHotelOrderListModel;
        }

        public static class CustomPropertiesBean {
        }

        public static class BJHotelOrderListModelBean {
            /**
             * PayMethod : null
             * Type : null
             * HotelName : 国际酒店
             * HotelRoomName : 豪华单人间
             * HotelPictureUrl : null
             * Phone : null
             * EnterDate : /Date(-62135596800000)/
             * EnterDateStr : null
             * LeaveDate : /Date(-62135596800000)/
             * LeaveDateStr : null
             * TotalMoney : 0.01
             * RoomNumber : 1
             * OrderTime : /Date(-62135596800000)/
             * OrderTimeStr : 2017-12-14 17:20
             * OrderNo : null
             * IsCancel : false
             * CancelTime : null
             * IsPay : false
             * PayTime : null
             * IsUsing : false
             * UsingTime : null
             * IsEvaluate : false
             * EvaluateTime : null
             * IsRefund : false
             * Status : 1
             * CustomerId : 0
             * HotelRoomId : 0
             * CreateTime : /Date(-62135596800000)/
             * Note : null
             * Id : 1012
             * CustomProperties : {}
             */

            private String PayMethod;
            private Object Type;
            private String HotelName;
            private String HotelRoomName;
            private String HotelPictureUrl;
            private Object Phone;
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
            private boolean IsRefund;
            private String Status;
            private int CustomerId;
            private int HotelRoomId;
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

            public Object getPhone() {
                return Phone;
            }

            public void setPhone(Object Phone) {
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
