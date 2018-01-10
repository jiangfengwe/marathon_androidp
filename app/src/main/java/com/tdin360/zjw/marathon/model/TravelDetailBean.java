package com.tdin360.zjw.marathon.model;

import java.util.List;

/**
 * Created by jeffery on 2017/12/13.
 */

public class TravelDetailBean {

    /**
     * state : true
     * message : 请求成功
     * model : {"pageIndex":0,"totalPages":0,"EvaluationCount":1,"BJTravelModel":{"PictureCount":2,"StartPlace":"贵阳","EndPlace":"安顺","Day":2,"Scoring":0,"RouteIntroduce":"贵阳-安顺  2017-12-12","PlaceIntroduce":"安顺地区是著名的旅游圣地","Security":"请旅游客人注意自己人身安全","PreferentialPolicies":"阿达大大多所大所大所多所多","FeeDescription":"是的地方沙发斯蒂芬沙发斯蒂芬","OperationMethod":"直接使用","OrtherMethod":"sss","Phone1":"15761630070","Phone2":"15761630070","Price":0.01,"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0000038.png","IsUsing":false,"UsingDate":null,"EventId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":1,"CustomProperties":{}},"BJTravelPictureListModel":[],"ApiTravelMonthDateList":[{"Month":"12","ApiTravelDayDateList":[{"Day":"08"},{"Day":"09"},{"Day":"13"},{"Day":"14"},{"Day":"15"},{"Day":"16"},{"Day":"17"}]},{"Month":"11","ApiTravelDayDateList":[{"Day":"13"},{"Day":"14"}]},{"Month":"10","ApiTravelDayDateList":[{"Day":"15"}]}],"BJTravelEvaluateListModel":[{"BJTravelEvaluatePictureListModel":[{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002106.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002106_160.jpg","PictureHeight":552,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002105.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002105_160.jpg","PictureHeight":552,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002104.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002104_160.jpg","PictureHeight":550,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002103.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002103_160.jpg","PictureHeight":552,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002102.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002102_160.jpg","PictureHeight":550,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002101.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002101_160.jpg","PictureHeight":552,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}}],"EvaluationUserModel":{"Id":4099,"HeadImg":"http://www.baijar.com/content/images/thumbs/0002055_120.jpg","Phone":null,"NickName":"用户4099","Gender":null,"Unionid":null,"IsBindPhone":false,"CustomerSign":null},"EvaluateContent":"Gggg好好过自己想要一个不知道该国经济学家在你心里想什么就买吧、在这里等你们哟！安顺市委宣传部的小手表演服人\u2026\u2026这里没有","ReplayContent":null,"EvaluateTime":"/Date(-62135596800000)/","EvaluateTimeStr":"2017-12-26","Scoring":0,"CustomerId":0,"TravelId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}}],"Id":0,"CustomProperties":{}}
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
         * pageIndex : 0
         * totalPages : 0
         * EvaluationCount : 1
         * BJTravelModel : {"PictureCount":2,"StartPlace":"贵阳","EndPlace":"安顺","Day":2,"Scoring":0,"RouteIntroduce":"贵阳-安顺  2017-12-12","PlaceIntroduce":"安顺地区是著名的旅游圣地","Security":"请旅游客人注意自己人身安全","PreferentialPolicies":"阿达大大多所大所大所多所多","FeeDescription":"是的地方沙发斯蒂芬沙发斯蒂芬","OperationMethod":"直接使用","OrtherMethod":"sss","Phone1":"15761630070","Phone2":"15761630070","Price":0.01,"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0000038.png","IsUsing":false,"UsingDate":null,"EventId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":1,"CustomProperties":{}}
         * BJTravelPictureListModel : []
         * ApiTravelMonthDateList : [{"Month":"12","ApiTravelDayDateList":[{"Day":"08"},{"Day":"09"},{"Day":"13"},{"Day":"14"},{"Day":"15"},{"Day":"16"},{"Day":"17"}]},{"Month":"11","ApiTravelDayDateList":[{"Day":"13"},{"Day":"14"}]},{"Month":"10","ApiTravelDayDateList":[{"Day":"15"}]}]
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
        private List<?> BJTravelPictureListModel;
        private List<ApiTravelMonthDateListBean> ApiTravelMonthDateList;
        private List<BJTravelEvaluateListModelBean> BJTravelEvaluateListModel;

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

        public List<ApiTravelMonthDateListBean> getApiTravelMonthDateList() {
            return ApiTravelMonthDateList;
        }

        public void setApiTravelMonthDateList(List<ApiTravelMonthDateListBean> apiTravelMonthDateList) {
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
             * PictureCount : 2
             * StartPlace : 贵阳
             * EndPlace : 安顺
             * Day : 2
             * Scoring : 0
             * RouteIntroduce : 贵阳-安顺  2017-12-12
             * PlaceIntroduce : 安顺地区是著名的旅游圣地
             * Security : 请旅游客人注意自己人身安全
             * PreferentialPolicies : 阿达大大多所大所大所多所多
             * FeeDescription : 是的地方沙发斯蒂芬沙发斯蒂芬
             * OperationMethod : 直接使用
             * OrtherMethod : sss
             * Phone1 : 15761630070
             * Phone2 : 15761630070
             * Price : 0.01
             * Picture : 0
             * PictureUrl : http://www.baijar.com/content/images/thumbs/0000038.png
             * IsUsing : false
             * UsingDate : null
             * EventId : 0
             * CreateTime : /Date(-62135596800000)/
             * Note : null
             * Id : 1
             * CustomProperties : {}
             */

            private int PictureCount;
            private String StartPlace;
            private String EndPlace;
            private int Day;
            private int Scoring;
            private String RouteIntroduce;
            private String PlaceIntroduce;
            private String Security;
            private String PreferentialPolicies;
            private String FeeDescription;
            private String OperationMethod;
            private String OrtherMethod;
            private String Phone1;
            private String Phone2;
            private double Price;
            private int Picture;
            private String PictureUrl;
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

            public String getRouteIntroduce() {
                return RouteIntroduce;
            }

            public void setRouteIntroduce(String RouteIntroduce) {
                this.RouteIntroduce = RouteIntroduce;
            }

            public String getPlaceIntroduce() {
                return PlaceIntroduce;
            }

            public void setPlaceIntroduce(String PlaceIntroduce) {
                this.PlaceIntroduce = PlaceIntroduce;
            }

            public String getSecurity() {
                return Security;
            }

            public void setSecurity(String Security) {
                this.Security = Security;
            }

            public String getPreferentialPolicies() {
                return PreferentialPolicies;
            }

            public void setPreferentialPolicies(String PreferentialPolicies) {
                this.PreferentialPolicies = PreferentialPolicies;
            }

            public String getFeeDescription() {
                return FeeDescription;
            }

            public void setFeeDescription(String FeeDescription) {
                this.FeeDescription = FeeDescription;
            }

            public String getOperationMethod() {
                return OperationMethod;
            }

            public void setOperationMethod(String OperationMethod) {
                this.OperationMethod = OperationMethod;
            }

            public String getOrtherMethod() {
                return OrtherMethod;
            }

            public void setOrtherMethod(String OrtherMethod) {
                this.OrtherMethod = OrtherMethod;
            }

            public String getPhone1() {
                return Phone1;
            }

            public void setPhone1(String Phone1) {
                this.Phone1 = Phone1;
            }

            public String getPhone2() {
                return Phone2;
            }

            public void setPhone2(String Phone2) {
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
        public static class ApiTravelMonthDateListBean{

            /**
             * Month : 12
             * ApiTravelDayDateList : [{"Day":"08"},{"Day":"09"},{"Day":"13"},{"Day":"14"},{"Day":"15"},{"Day":"16"},{"Day":"17"}]
             */

            private String Month;
            private List<ApiTravelDayDateListBean> ApiTravelDayDateList;

            public String getMonth() {
                return Month;
            }

            public void setMonth(String Month) {
                this.Month = Month;
            }

            public List<ApiTravelDayDateListBean> getApiTravelDayDateList() {
                return ApiTravelDayDateList;
            }

            public void setApiTravelDayDateList(List<ApiTravelDayDateListBean> ApiTravelDayDateList) {
                this.ApiTravelDayDateList = ApiTravelDayDateList;
            }

            public static class ApiTravelDayDateListBean {
                /**
                 * Day : 08
                 */

                private String Day;

                public String getDay() {
                    return Day;
                }

                public void setDay(String Day) {
                    this.Day = Day;
                }
            }
        }
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
