package com.tdin360.zjw.marathon.model;

import java.util.List;

/**
 * Created by jeffery on 2018/1/3.
 * 酒店详情数据
 */

public class AA {
    /**
     * state : true
     * message : 请求成功
     * model : {"pageIndex":0,"totalPages":0,
     * "BJHotelModel":{"Name":"测试","HotelStyle":null,"EvaluationCount":0,"Scoring":5,"Description":"该酒店是最好的酒店","Phone1":"15761630070","Phone2":null,"Address":"花溪","LowestPrice":0,"IsUsing":false,"UsingDate":null,"Picture":0,"PictureUrl":"http://www.tdin360.com/content/images/thumbs/0005818.jpeg","EventId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":4,"CustomProperties":{}},
     * "BJHotelPictureListModel":[],
     * "BJHotelRoomListModel":[{"BJHotelRoomPictureListModel":[],"HotelRoomPictureCount":0,"Name":"普通单人间","Price":0.01,"Area":30,"IsBooking":true,"Picture":0,"PictureUrl":"http://www.tdin360.com/content/images/thumbs/0005819_160.jpeg","Network":"wifi","Bathroom":"有浴室","Window":"有窗","BedType":"大床","LiveInPerson":2,"Breakfast":"有早餐","Instructions":null,"HotelId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":1005,"CustomProperties":{}}],
     * "BJHotelEvaluateListModel":[],"ApiHotelMonthDateList":[{"Month":"02","ApiHotelDayDateList":[{"Day":"21"},{"Day":"22"},{"Day":"23"}]}],"Id":0,"CustomProperties":{}}
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
         * BJHotelModel : {"Name":"测试","HotelStyle":null,"EvaluationCount":0,"Scoring":5,"Description":"该酒店是最好的酒店","Phone1":"15761630070","Phone2":null,"Address":"花溪","LowestPrice":0,"IsUsing":false,"UsingDate":null,"Picture":0,"PictureUrl":"http://www.tdin360.com/content/images/thumbs/0005818.jpeg","EventId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":4,"CustomProperties":{}}
         * BJHotelPictureListModel : []
         * BJHotelRoomListModel : [{"BJHotelRoomPictureListModel":[],"HotelRoomPictureCount":0,"Name":"普通单人间","Price":0.01,"Area":30,"IsBooking":true,"Picture":0,"PictureUrl":"http://www.tdin360.com/content/images/thumbs/0005819_160.jpeg","Network":"wifi","Bathroom":"有浴室","Window":"有窗","BedType":"大床","LiveInPerson":2,"Breakfast":"有早餐","Instructions":null,"HotelId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":1005,"CustomProperties":{}}]
         * BJHotelEvaluateListModel : []
         * ApiHotelMonthDateList : [{"Month":"02","ApiHotelDayDateList":[{"Day":"21"},{"Day":"22"},{"Day":"23"}]}]
         * Id : 0
         * CustomProperties : {}
         */

        private int pageIndex;
        private int totalPages;
        private BJHotelModelBean BJHotelModel;
        private int Id;
        private CustomPropertiesBeanX CustomProperties;
        private List<BJHotelPictureListModelBean> BJHotelPictureListModel;
        private List<BJHotelRoomListModelBean> BJHotelRoomListModel;
        private List<BJHotelEvaluateListModelBean> BJHotelEvaluateListModel;
        private List<ApiHotelMonthDateListBean> ApiHotelMonthDateList;

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

        public BJHotelModelBean getBJHotelModel() {
            return BJHotelModel;
        }

        public void setBJHotelModel(BJHotelModelBean BJHotelModel) {
            this.BJHotelModel = BJHotelModel;
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

        public List<BJHotelPictureListModelBean> getBJHotelPictureListModel() {
            return BJHotelPictureListModel;
        }

        public void setBJHotelPictureListModel(List<BJHotelPictureListModelBean> BJHotelPictureListModel) {
            this.BJHotelPictureListModel = BJHotelPictureListModel;
        }

        public List<BJHotelRoomListModelBean> getBJHotelRoomListModel() {
            return BJHotelRoomListModel;
        }

        public void setBJHotelRoomListModel(List<BJHotelRoomListModelBean> BJHotelRoomListModel) {
            this.BJHotelRoomListModel = BJHotelRoomListModel;
        }

        public List<BJHotelEvaluateListModelBean> getBJHotelEvaluateListModel() {
            return BJHotelEvaluateListModel;
        }

        public void setBJHotelEvaluateListModel(List<BJHotelEvaluateListModelBean> BJHotelEvaluateListModel) {
            this.BJHotelEvaluateListModel = BJHotelEvaluateListModel;
        }

        public List<ApiHotelMonthDateListBean> getApiHotelMonthDateList() {
            return ApiHotelMonthDateList;
        }

        public void setApiHotelMonthDateList(List<ApiHotelMonthDateListBean> apiHotelMonthDateList) {
            ApiHotelMonthDateList = apiHotelMonthDateList;
        }

        public static class BJHotelModelBean{

            /**
             * Name : 测试
             * HotelStyle : null
             * EvaluationCount : 0
             * Scoring : 5
             * Description : 该酒店是最好的酒店
             * Phone1 : 15761630070
             * Phone2 : null
             * Address : 花溪
             * LowestPrice : 0
             * IsUsing : false
             * UsingDate : null
             * Picture : 0
             * PictureUrl : http://www.tdin360.com/content/images/thumbs/0005818.jpeg
             * EventId : 0
             * CreateTime : /Date(-62135596800000)/
             * Note : null
             * Id : 4
             * CustomProperties : {}
             */

            private String Name;
            private Object HotelStyle;
            private int EvaluationCount;
            private int Scoring;
            private String Description;
            private String Phone1;
            private Object Phone2;
            private String Address;
            private int LowestPrice;
            private boolean IsUsing;
            private Object UsingDate;
            private int Picture;
            private String PictureUrl;
            private int EventId;
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

            public Object getHotelStyle() {
                return HotelStyle;
            }

            public void setHotelStyle(Object HotelStyle) {
                this.HotelStyle = HotelStyle;
            }

            public int getEvaluationCount() {
                return EvaluationCount;
            }

            public void setEvaluationCount(int EvaluationCount) {
                this.EvaluationCount = EvaluationCount;
            }

            public int getScoring() {
                return Scoring;
            }

            public void setScoring(int Scoring) {
                this.Scoring = Scoring;
            }

            public String getDescription() {
                return Description;
            }

            public void setDescription(String Description) {
                this.Description = Description;
            }

            public String getPhone1() {
                return Phone1;
            }

            public void setPhone1(String Phone1) {
                this.Phone1 = Phone1;
            }

            public Object getPhone2() {
                return Phone2;
            }

            public void setPhone2(Object Phone2) {
                this.Phone2 = Phone2;
            }

            public String getAddress() {
                return Address;
            }

            public void setAddress(String Address) {
                this.Address = Address;
            }

            public int getLowestPrice() {
                return LowestPrice;
            }

            public void setLowestPrice(int LowestPrice) {
                this.LowestPrice = LowestPrice;
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
        public static class BJHotelRoomListModelBean{
            /**
             * BJHotelRoomPictureListModel : []
             * HotelRoomPictureCount : 0
             * Name : 普通单人间
             * Price : 0.01
             * Area : 30
             * IsBooking : true
             * Picture : 0
             * PictureUrl : http://www.tdin360.com/content/images/thumbs/0005819_160.jpeg
             * Network : wifi
             * Bathroom : 有浴室
             * Window : 有窗
             * BedType : 大床
             * LiveInPerson : 2
             * Breakfast : 有早餐
             * Instructions : null
             * HotelId : 0
             * CreateTime : /Date(-62135596800000)/
             * Note : null
             * Id : 1005
             * CustomProperties : {}
             */

            private int HotelRoomPictureCount;
            private String Name;
            private double Price;
            private int Area;
            private boolean IsBooking;
            private int Picture;
            private String PictureUrl;
            private String Network;
            private String Bathroom;
            private String Window;
            private String BedType;
            private int LiveInPerson;
            private String Breakfast;
            private String Instructions;
            private int HotelId;
            private String CreateTime;
            private Object Note;
            private int Id;
            private CustomPropertiesBean CustomProperties;
            private List<?> BJHotelRoomPictureListModel;

            public int getHotelRoomPictureCount() {
                return HotelRoomPictureCount;
            }

            public void setHotelRoomPictureCount(int HotelRoomPictureCount) {
                this.HotelRoomPictureCount = HotelRoomPictureCount;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public double getPrice() {
                return Price;
            }

            public void setPrice(double Price) {
                this.Price = Price;
            }

            public int getArea() {
                return Area;
            }

            public void setArea(int Area) {
                this.Area = Area;
            }

            public boolean isIsBooking() {
                return IsBooking;
            }

            public void setIsBooking(boolean IsBooking) {
                this.IsBooking = IsBooking;
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

            public String getNetwork() {
                return Network;
            }

            public void setNetwork(String Network) {
                this.Network = Network;
            }

            public String getBathroom() {
                return Bathroom;
            }

            public void setBathroom(String Bathroom) {
                this.Bathroom = Bathroom;
            }

            public String getWindow() {
                return Window;
            }

            public void setWindow(String Window) {
                this.Window = Window;
            }

            public String getBedType() {
                return BedType;
            }

            public void setBedType(String BedType) {
                this.BedType = BedType;
            }

            public int getLiveInPerson() {
                return LiveInPerson;
            }

            public void setLiveInPerson(int LiveInPerson) {
                this.LiveInPerson = LiveInPerson;
            }

            public String getBreakfast() {
                return Breakfast;
            }

            public void setBreakfast(String Breakfast) {
                this.Breakfast = Breakfast;
            }

            public String getInstructions() {
                return Instructions;
            }

            public void setInstructions(String Instructions) {
                this.Instructions = Instructions;
            }

            public int getHotelId() {
                return HotelId;
            }

            public void setHotelId(int HotelId) {
                this.HotelId = HotelId;
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

            public List<?> getBJHotelRoomPictureListModel() {
                return BJHotelRoomPictureListModel;
            }

            public void setBJHotelRoomPictureListModel(List<?> BJHotelRoomPictureListModel) {
                this.BJHotelRoomPictureListModel = BJHotelRoomPictureListModel;
            }

            public static class CustomPropertiesBean {
            }
        }
        public static class ApiHotelMonthDateListBean{

            /**
             * Month : 02
             * ApiHotelDayDateList : [{"Day":"21"},{"Day":"22"},{"Day":"23"}]
             */

            private String Month;
            private List<ApiHotelDayDateListBean> ApiHotelDayDateList;

            public String getMonth() {
                return Month;
            }

            public void setMonth(String Month) {
                this.Month = Month;
            }

            public List<ApiHotelDayDateListBean> getApiHotelDayDateList() {
                return ApiHotelDayDateList;
            }

            public void setApiHotelDayDateList(List<ApiHotelDayDateListBean> ApiHotelDayDateList) {
                this.ApiHotelDayDateList = ApiHotelDayDateList;
            }

            public static class ApiHotelDayDateListBean {
                /**
                 * Day : 21
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
        public static class BJHotelPictureListModelBean{

            /**
             * Picture : 0
             * PictureUrl : http://www.baijar.com/content/images/thumbs/0001052.png
             * ThumbPictureUrl : http://www.baijar.com/content/images/thumbs/0001052_320.png
             * PictureHeight : 926
             * PictureWidth : 1297
             * HotelId : 0
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
            private int HotelId;
            private String CreateTime;
            private Object Note;
            private int Id;
            private HotelDetailBean.ModelBean.BJHotelPictureListModelBean.CustomPropertiesBean CustomProperties;

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

            public int getHotelId() {
                return HotelId;
            }

            public void setHotelId(int HotelId) {
                this.HotelId = HotelId;
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

            public HotelDetailBean.ModelBean.BJHotelPictureListModelBean.CustomPropertiesBean getCustomProperties() {
                return CustomProperties;
            }

            public void setCustomProperties(HotelDetailBean.ModelBean.BJHotelPictureListModelBean.CustomPropertiesBean CustomProperties) {
                this.CustomProperties = CustomProperties;
            }

            public static class CustomPropertiesBean {
            }
        }
        public static class BJHotelEvaluateListModelBean{
            /**
             * BJHotelEvaluatePictureListModel : [{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002100.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002100_160.jpg","PictureHeight":552,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002099.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002099_160.jpg","PictureHeight":552,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002098.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002098_160.jpg","PictureHeight":552,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002097.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002097_160.jpg","PictureHeight":550,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002095.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002095_160.jpg","PictureHeight":1241,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002092.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002092_160.jpg","PictureHeight":550,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002090.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002090_160.jpg","PictureHeight":552,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002089.jpg","ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002089_160.jpg","PictureHeight":552,"PictureWidth":828,"EvaluateId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}}]
             * EvaluationUserModel : {"Id":4099,"HeadImg":"http://www.baijar.com/content/images/thumbs/0002055_120.jpg","Phone":null,"NickName":"用户4099","Gender":null,"Unionid":null,"IsBindPhone":false,"CustomerSign":null}
             * EvaluateContent : Jjhv风 vvv 宝贝这样vvgggbbbcdCCTV 次在外面吃饭都吃过最多也有很多事情是这样
             * ReplayContent : null
             * EvaluateTime : /Date(-62135596800000)/
             * EvaluateTimeStr : 2017-12-26
             * Scoring : 0
             * CustomerId : 0
             * HotelRoomId : 0
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
            private String Scoring;
            private int CustomerId;
            private int HotelRoomId;
            private String CreateTime;
            private Object Note;
            private int Id;
            private CustomPropertiesBean CustomProperties;
            private List<BJHotelEvaluatePictureListModelBean> BJHotelEvaluatePictureListModel;

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

            public String getScoring() {
                return Scoring;
            }

            public void setScoring(String Scoring) {
                this.Scoring = Scoring;
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

            public List<BJHotelEvaluatePictureListModelBean> getBJHotelEvaluatePictureListModel() {
                return BJHotelEvaluatePictureListModel;
            }

            public void setBJHotelEvaluatePictureListModel(List<BJHotelEvaluatePictureListModelBean> BJHotelEvaluatePictureListModel) {
                this.BJHotelEvaluatePictureListModel = BJHotelEvaluatePictureListModel;
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

            public static class BJHotelEvaluatePictureListModelBean {
                /**
                 * Picture : 0
                 * PictureUrl : http://www.baijar.com/content/images/thumbs/0002100.jpg
                 * ThumbPictureUrl : http://www.baijar.com/content/images/thumbs/0002100_160.jpg
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
                private HotelDetailBean.ModelBean.BJHotelEvaluateListModelBean.BJHotelEvaluatePictureListModelBean.CustomPropertiesBeanX CustomProperties;

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

                public HotelDetailBean.ModelBean.BJHotelEvaluateListModelBean.BJHotelEvaluatePictureListModelBean.CustomPropertiesBeanX getCustomProperties() {
                    return CustomProperties;
                }

                public void setCustomProperties(HotelDetailBean.ModelBean.BJHotelEvaluateListModelBean.BJHotelEvaluatePictureListModelBean.CustomPropertiesBeanX CustomProperties) {
                    this.CustomProperties = CustomProperties;
                }

                public static class CustomPropertiesBeanX {
                }
            }
        }
    }
}
