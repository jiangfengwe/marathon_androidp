package com.tdin360.zjw.marathon.model;

import java.util.List;

/**
 * Created by jeffery on 2017/12/27.
 */

public class TravelMoreCommentBean {

    /**
     * state : true
     * message : 请求成功
     * model : {"pageIndex":1,"totalPages":1,"EvaluationCount":0,"BJTravelModel":{"PictureCount":0,"StartPlace":null,"EndPlace":null,"Day":0,"Scoring":0,"RouteIntroduce":null,"PlaceIntroduce":null,"Security":null,"PreferentialPolicies":null,"FeeDescription":null,"OperationMethod":null,"OrtherMethod":null,"Phone1":null,"Phone2":null,"Price":0,"Picture":0,"PictureUrl":null,"IsUsing":false,"UsingDate":null,"EventId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},"BJTravelPictureListModel":[],"ApiTravelMonthDateList":[],"BJTravelEvaluateListModel":[{"BJTravelEvaluatePictureListModel":[{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002106.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002106_160.jpg","PictureHeight":552,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002105.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002105_160.jpg","PictureHeight":552,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002104.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002104_160.jpg","PictureHeight":550,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002103.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002103_160.jpg","PictureHeight":552,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002102.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002102_160.jpg","PictureHeight":550,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002101.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002101_160.jpg","PictureHeight":552,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}}],"EvaluationUserModel":{"Id":4099,"HeadImg":"http://www.baijar.com/content/images/thumbs/0002055_120.jpg","Phone":null,"NickName":"用户4099","Gender":null,"Unionid":null,"IsBindPhone":false,"CustomerSign":null},"EvaluateContent":"Gggg好好过自己想要一个不知道该国经济学家在你心里想什么就买吧、在这里等你们哟！安顺市委宣传部的小手表演服人\u2026\u2026这里没有","ReplayContent":null,"EvaluateTime":"/Date(-62135596800000)/","EvaluateTimeStr":"2017-12-26","Scoring":0,"CustomerId":0,"TravelId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}}],"Id":0,"CustomProperties":{}}
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
         * EvaluationCount : 0
         * BJTravelModel : {"PictureCount":0,"StartPlace":null,"EndPlace":null,"Day":0,"Scoring":0,"RouteIntroduce":null,"PlaceIntroduce":null,"Security":null,"PreferentialPolicies":null,"FeeDescription":null,"OperationMethod":null,"OrtherMethod":null,"Phone1":null,"Phone2":null,"Price":0,"Picture":0,"PictureUrl":null,"IsUsing":false,"UsingDate":null,"EventId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}}
         * BJTravelPictureListModel : []
         * ApiTravelMonthDateList : []
         * BJTravelEvaluateListModel : [{"BJTravelEvaluatePictureListModel":[{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002106.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002106_160.jpg","PictureHeight":552,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002105.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002105_160.jpg","PictureHeight":552,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002104.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002104_160.jpg","PictureHeight":550,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002103.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002103_160.jpg","PictureHeight":552,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002102.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002102_160.jpg","PictureHeight":550,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002101.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002101_160.jpg","PictureHeight":552,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}}],"EvaluationUserModel":{"Id":4099,"HeadImg":"http://www.baijar.com/content/images/thumbs/0002055_120.jpg","Phone":null,"NickName":"用户4099","Gender":null,"Unionid":null,"IsBindPhone":false,"CustomerSign":null},"EvaluateContent":"Gggg好好过自己想要一个不知道该国经济学家在你心里想什么就买吧、在这里等你们哟！安顺市委宣传部的小手表演服人\u2026\u2026这里没有","ReplayContent":null,"EvaluateTime":"/Date(-62135596800000)/","EvaluateTimeStr":"2017-12-26","Scoring":0,"CustomerId":0,"TravelId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}}]
         * Id : 0
         * CustomProperties : {}
         */

        private int pageIndex;
        private int totalPages;
        private int EvaluationCount;
        private BJTravelModelBean BJTravelModel;
        private int Id;
        private CustomPropertiesBeanX CustomProperties;
        private java.util.List<?> BJTravelPictureListModel;
        private java.util.List<?> ApiTravelMonthDateList;
        private java.util.List<BJTravelEvaluateListModelBean> BJTravelEvaluateListModel;

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

        public int getEvaluationCount() {
            return EvaluationCount;
        }

        public void setEvaluationCount(int evaluationCount) {
            EvaluationCount = evaluationCount;
        }

        public BJTravelModelBean getBJTravelModel() {
            return BJTravelModel;
        }

        public void setBJTravelModel(BJTravelModelBean BJTravelModel) {
            this.BJTravelModel = BJTravelModel;
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

        public List<?> getBJTravelPictureListModel() {
            return BJTravelPictureListModel;
        }

        public void setBJTravelPictureListModel(List<?> BJTravelPictureListModel) {
            this.BJTravelPictureListModel = BJTravelPictureListModel;
        }

        public List<?> getApiTravelMonthDateList() {
            return ApiTravelMonthDateList;
        }

        public void setApiTravelMonthDateList(List<?> apiTravelMonthDateList) {
            ApiTravelMonthDateList = apiTravelMonthDateList;
        }

        public List<BJTravelEvaluateListModelBean> getBJTravelEvaluateListModel() {
            return BJTravelEvaluateListModel;
        }

        public void setBJTravelEvaluateListModel(List<BJTravelEvaluateListModelBean> BJTravelEvaluateListModel) {
            this.BJTravelEvaluateListModel = BJTravelEvaluateListModel;
        }

        public static class BJTravelModelBean{

            /**
             * PictureCount : 0
             * StartPlace : null
             * EndPlace : null
             * Day : 0
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
             * Price : 0
             * Picture : 0
             * PictureUrl : null
             * IsUsing : false
             * UsingDate : null
             * EventId : 0
             * CreateTime : /Date(-62135596800000)/
             * Note : null
             * Id : 0
             * CustomProperties : {}
             */

            private int PictureCount;
            private Object StartPlace;
            private Object EndPlace;
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
            private int Price;
            private int Picture;
            private Object PictureUrl;
            private boolean IsUsing;
            private Object UsingDate;
            private int EventId;
            private String CreateTime;
            private Object Note;
            private int Id;
            private CustomPropertiesBean CustomProperties;

            public int getPictureCount() {
                return PictureCount;
            }

            public void setPictureCount(int PictureCount) {
                this.PictureCount = PictureCount;
            }

            public Object getStartPlace() {
                return StartPlace;
            }

            public void setStartPlace(Object StartPlace) {
                this.StartPlace = StartPlace;
            }

            public Object getEndPlace() {
                return EndPlace;
            }

            public void setEndPlace(Object EndPlace) {
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

            public int getPrice() {
                return Price;
            }

            public void setPrice(int Price) {
                this.Price = Price;
            }

            public int getPicture() {
                return Picture;
            }

            public void setPicture(int Picture) {
                this.Picture = Picture;
            }

            public Object getPictureUrl() {
                return PictureUrl;
            }

            public void setPictureUrl(Object PictureUrl) {
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
        public static class BJTravelEvaluateListModelBean{

            /**
             * BJTravelEvaluatePictureListModel : [{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002106.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002106_160.jpg","PictureHeight":552,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002105.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002105_160.jpg","PictureHeight":552,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002104.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002104_160.jpg","PictureHeight":550,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002103.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002103_160.jpg","PictureHeight":552,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002102.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002102_160.jpg","PictureHeight":550,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002101.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002101_160.jpg","PictureHeight":552,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}}]
             * EvaluationUserModel : {"Id":4099,"HeadImg":"http://www.baijar.com/content/images/thumbs/0002055_120.jpg","Phone":null,"NickName":"用户4099","Gender":null,"Unionid":null,"IsBindPhone":false,"CustomerSign":null}
             * EvaluateContent : Gggg好好过自己想要一个不知道该国经济学家在你心里想什么就买吧、在这里等你们哟！安顺市委宣传部的小手表演服人……这里没有
             * ReplayContent : null
             * EvaluateTime : /Date(-62135596800000)/
             * EvaluateTimeStr : 2017-12-26
             * Scoring : 0
             * CustomerId : 0
             * TravelId : 0
             * CreateTime : /Date(-62135596800000)/
             * Note : null
             * Id : 0
             * CustomProperties : {}
             */

            private EvaluationUserModelBean EvaluationUserModel;
            private String EvaluateContent;
            private Object ReplayContent;
            private String EvaluateTime;
            private String EvaluateTimeStr;
            private int Scoring;
            private int CustomerId;
            private int TravelId;
            private String CreateTime;
            private Object Note;
            private int Id;
            private CustomPropertiesBean CustomProperties;
            private List<BJTravelEvaluatePictureListModelBean> BJTravelEvaluatePictureListModel;

            public EvaluationUserModelBean getEvaluationUserModel() {
                return EvaluationUserModel;
            }

            public void setEvaluationUserModel(EvaluationUserModelBean EvaluationUserModel) {
                this.EvaluationUserModel = EvaluationUserModel;
            }

            public String getEvaluateContent() {
                return EvaluateContent;
            }

            public void setEvaluateContent(String EvaluateContent) {
                this.EvaluateContent = EvaluateContent;
            }

            public Object getReplayContent() {
                return ReplayContent;
            }

            public void setReplayContent(Object ReplayContent) {
                this.ReplayContent = ReplayContent;
            }

            public String getEvaluateTime() {
                return EvaluateTime;
            }

            public void setEvaluateTime(String EvaluateTime) {
                this.EvaluateTime = EvaluateTime;
            }

            public String getEvaluateTimeStr() {
                return EvaluateTimeStr;
            }

            public void setEvaluateTimeStr(String EvaluateTimeStr) {
                this.EvaluateTimeStr = EvaluateTimeStr;
            }

            public int getScoring() {
                return Scoring;
            }

            public void setScoring(int Scoring) {
                this.Scoring = Scoring;
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

            public List<BJTravelEvaluatePictureListModelBean> getBJTravelEvaluatePictureListModel() {
                return BJTravelEvaluatePictureListModel;
            }

            public void setBJTravelEvaluatePictureListModel(List<BJTravelEvaluatePictureListModelBean> BJTravelEvaluatePictureListModel) {
                this.BJTravelEvaluatePictureListModel = BJTravelEvaluatePictureListModel;
            }

            public static class EvaluationUserModelBean {

                /**
                 * Id : 4099
                 * HeadImg : http://www.baijar.com/content/images/thumbs/0002055_120.jpg
                 * Phone : null
                 * NickName : 用户4099
                 * Gender : null
                 * Unionid : null
                 * IsBindPhone : false
                 * CustomerSign : null
                 */

                private int Id;
                private String HeadImg;
                private Object Phone;
                private String NickName;
                private Object Gender;
                private Object Unionid;
                private boolean IsBindPhone;
                private Object CustomerSign;

                public int getId() {
                    return Id;
                }

                public void setId(int Id) {
                    this.Id = Id;
                }

                public String getHeadImg() {
                    return HeadImg;
                }

                public void setHeadImg(String HeadImg) {
                    this.HeadImg = HeadImg;
                }

                public Object getPhone() {
                    return Phone;
                }

                public void setPhone(Object Phone) {
                    this.Phone = Phone;
                }

                public String getNickName() {
                    return NickName;
                }

                public void setNickName(String NickName) {
                    this.NickName = NickName;
                }

                public Object getGender() {
                    return Gender;
                }

                public void setGender(Object Gender) {
                    this.Gender = Gender;
                }

                public Object getUnionid() {
                    return Unionid;
                }

                public void setUnionid(Object Unionid) {
                    this.Unionid = Unionid;
                }

                public boolean isIsBindPhone() {
                    return IsBindPhone;
                }

                public void setIsBindPhone(boolean IsBindPhone) {
                    this.IsBindPhone = IsBindPhone;
                }

                public Object getCustomerSign() {
                    return CustomerSign;
                }

                public void setCustomerSign(Object CustomerSign) {
                    this.CustomerSign = CustomerSign;
                }
            }

            public static class CustomPropertiesBean {
            }

            public static class BJTravelEvaluatePictureListModelBean {
                /**
                 * Picture : 0
                 * PictureUrl : http://www.baijar.com/content/images/thumbs/0002106.jpg
                 * ThumbPictureUrl : http://www.baijar.com/content/images/thumbs/0002106_160.jpg
                 * PictureHeight : 552
                 * PictureWidth : 828
                 * EvaluateId : 0
                 * CreateTime : /Date(-62135596800000)/
                 * Note : null
                 * Id : 0
                 * CustomProperties : {}
                 */

                private int Picture;
                private String PictureUrl;
                private String ThumbPictureUrl;
                private int PictureHeight;
                private int PictureWidth;
                private int EvaluateId;
                private String CreateTime;
                private Object Note;
                private int Id;
                private CustomPropertiesBeanX CustomProperties;

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

                public String getThumbPictureUrl() {
                    return ThumbPictureUrl;
                }

                public void setThumbPictureUrl(String ThumbPictureUrl) {
                    this.ThumbPictureUrl = ThumbPictureUrl;
                }

                public int getPictureHeight() {
                    return PictureHeight;
                }

                public void setPictureHeight(int PictureHeight) {
                    this.PictureHeight = PictureHeight;
                }

                public int getPictureWidth() {
                    return PictureWidth;
                }

                public void setPictureWidth(int PictureWidth) {
                    this.PictureWidth = PictureWidth;
                }

                public int getEvaluateId() {
                    return EvaluateId;
                }

                public void setEvaluateId(int EvaluateId) {
                    this.EvaluateId = EvaluateId;
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
}
