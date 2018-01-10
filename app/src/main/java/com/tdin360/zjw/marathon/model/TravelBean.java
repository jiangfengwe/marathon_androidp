package com.tdin360.zjw.marathon.model;

import java.util.List;

/**
 * Created by jeffery on 2017/12/13.
 */

public class TravelBean {


    /**
     * state : true
     * message : 请求成功
     * model : {"pageIndex":1,"totalPages":1,"BJTravelListModel":[{"PictureCount":0,"StartPlace":"贵阳","EndPlace":"遵义","Day":1,"Scoring":0,"RouteIntroduce":null,"PlaceIntroduce":null,"Security":null,"PreferentialPolicies":null,"FeeDescription":null,"OperationMethod":null,"OrtherMethod":null,"Phone1":null,"Phone2":null,"Price":0.01,"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0000039.png","IsUsing":false,"UsingDate":null,"EventId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":2,"CustomProperties":{}},{"PictureCount":0,"StartPlace":"贵阳","EndPlace":"安顺","Day":2,"Scoring":0,"RouteIntroduce":null,"PlaceIntroduce":null,"Security":null,"PreferentialPolicies":null,"FeeDescription":null,"OperationMethod":null,"OrtherMethod":null,"Phone1":null,"Phone2":null,"Price":0.01,"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0000038.png","IsUsing":false,"UsingDate":null,"EventId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":1,"CustomProperties":{}}],"Id":0,"CustomProperties":{}}
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
         * totalPages : 1
         * BJTravelListModel : [{"PictureCount":0,"StartPlace":"贵阳","EndPlace":"遵义","Day":1,"Scoring":0,"RouteIntroduce":null,"PlaceIntroduce":null,"Security":null,"PreferentialPolicies":null,"FeeDescription":null,"OperationMethod":null,"OrtherMethod":null,"Phone1":null,"Phone2":null,"Price":0.01,"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0000039.png","IsUsing":false,"UsingDate":null,"EventId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":2,"CustomProperties":{}},{"PictureCount":0,"StartPlace":"贵阳","EndPlace":"安顺","Day":2,"Scoring":0,"RouteIntroduce":null,"PlaceIntroduce":null,"Security":null,"PreferentialPolicies":null,"FeeDescription":null,"OperationMethod":null,"OrtherMethod":null,"Phone1":null,"Phone2":null,"Price":0.01,"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0000038.png","IsUsing":false,"UsingDate":null,"EventId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":1,"CustomProperties":{}}]
         * Id : 0
         * CustomProperties : {}
         */

        private int pageIndex;
        private int totalPages;
        private int Id;
        private CustomPropertiesBean CustomProperties;
        private List<BJTravelListModelBean> BJTravelListModel;

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

        public List<BJTravelListModelBean> getBJTravelListModel() {
            return BJTravelListModel;
        }

        public void setBJTravelListModel(List<BJTravelListModelBean> BJTravelListModel) {
            this.BJTravelListModel = BJTravelListModel;
        }

        public static class CustomPropertiesBean {
        }

        public static class BJTravelListModelBean {
            /**
             * PictureCount : 0
             * StartPlace : 贵阳
             * EndPlace : 遵义
             * Day : 1
             * Scoring : 0
             * RouteIntroduce : null
             * PlaceIntroduce : null
             * Security : null
             * PreferentialPolicies : null
             * FeeDescription : null
             * OperationMethod : null
             * OrtherMethod : null
             * Phone1 : null
             * Phone2 : null
             * Price : 0.01
             * Picture : 0
             * PictureUrl : http://www.baijar.com/content/images/thumbs/0000039.png
             * IsUsing : false
             * UsingDate : null
             * EventId : 0
             * CreateTime : /Date(-62135596800000)/
             * Note : null
             * Id : 2
             * CustomProperties : {}
             */

            private int PictureCount;
            private String StartPlace;
            private String EndPlace;
            private int Day;
            private int Scoring;
            private Object RouteIntroduce;
            private Object PlaceIntroduce;
            private Object Security;
            private Object PreferentialPolicies;
            private Object FeeDescription;
            private Object OperationMethod;
            private Object OrtherMethod;
            private Object Phone1;
            private Object Phone2;
            private double Price;
            private int Picture;
            private String PictureUrl;
            private boolean IsUsing;
            private Object UsingDate;
            private int EventId;
            private String CreateTime;
            private Object Note;
            private int Id;
            private CustomPropertiesBeanX CustomProperties;

            public int getPictureCount() {
                return PictureCount;
            }

            public void setPictureCount(int PictureCount) {
                this.PictureCount = PictureCount;
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

            public int getDay() {
                return Day;
            }

            public void setDay(int Day) {
                this.Day = Day;
            }

            public int getScoring() {
                return Scoring;
            }

            public void setScoring(int Scoring) {
                this.Scoring = Scoring;
            }

            public Object getRouteIntroduce() {
                return RouteIntroduce;
            }

            public void setRouteIntroduce(Object RouteIntroduce) {
                this.RouteIntroduce = RouteIntroduce;
            }

            public Object getPlaceIntroduce() {
                return PlaceIntroduce;
            }

            public void setPlaceIntroduce(Object PlaceIntroduce) {
                this.PlaceIntroduce = PlaceIntroduce;
            }

            public Object getSecurity() {
                return Security;
            }

            public void setSecurity(Object Security) {
                this.Security = Security;
            }

            public Object getPreferentialPolicies() {
                return PreferentialPolicies;
            }

            public void setPreferentialPolicies(Object PreferentialPolicies) {
                this.PreferentialPolicies = PreferentialPolicies;
            }

            public Object getFeeDescription() {
                return FeeDescription;
            }

            public void setFeeDescription(Object FeeDescription) {
                this.FeeDescription = FeeDescription;
            }

            public Object getOperationMethod() {
                return OperationMethod;
            }

            public void setOperationMethod(Object OperationMethod) {
                this.OperationMethod = OperationMethod;
            }

            public Object getOrtherMethod() {
                return OrtherMethod;
            }

            public void setOrtherMethod(Object OrtherMethod) {
                this.OrtherMethod = OrtherMethod;
            }

            public Object getPhone1() {
                return Phone1;
            }

            public void setPhone1(Object Phone1) {
                this.Phone1 = Phone1;
            }

            public Object getPhone2() {
                return Phone2;
            }

            public void setPhone2(Object Phone2) {
                this.Phone2 = Phone2;
            }

            public double getPrice() {
                return Price;
            }

            public void setPrice(double Price) {
                this.Price = Price;
            }

            public int getPicture() {
                return Picture;
            }

            public void setPicture(int Picture) {
                this.Picture = Picture;
            }

            public String getPictureUrl() {
                return PictureUrl;
            }

            public void setPictureUrl(String PictureUrl) {
                this.PictureUrl = PictureUrl;
            }

            public boolean isIsUsing() {
                return IsUsing;
            }

            public void setIsUsing(boolean IsUsing) {
                this.IsUsing = IsUsing;
            }

            public Object getUsingDate() {
                return UsingDate;
            }

            public void setUsingDate(Object UsingDate) {
                this.UsingDate = UsingDate;
            }

            public int getEventId() {
                return EventId;
            }

            public void setEventId(int EventId) {
                this.EventId = EventId;
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
