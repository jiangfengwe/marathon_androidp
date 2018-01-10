package com.tdin360.zjw.marathon.model;

import java.util.List;

/**
 * Created by jeffery on 2017/12/28.
 */

public class HotelListBean {

    /**
     * state : true
     * message : 请求成功
     * model : {"pageIndex":1,"totalPages":1,"BJHotelStyleListModel":[{"Name":"舒适型","Introduce":null,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":1,"CustomProperties":{}}],"BJHotelListModel":[{"Name":"花溪酒店","HotelStyle":"舒适型","EvaluationCount":0,"Scoring":5,"Description":null,"Phone1":null,"Phone2":null,"Address":null,"LowestPrice":200,"IsUsing":false,"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0001045.png","EventId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":2,"CustomProperties":{}},{"Name":"国际酒店","HotelStyle":"舒适型","EvaluationCount":0,"Scoring":4.7,"Description":null,"Phone1":null,"Phone2":null,"Address":null,"LowestPrice":200,"IsUsing":false,"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0001044.jpeg","EventId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":1,"CustomProperties":{}}],"Id":0,"CustomProperties":{}}
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
         * BJHotelStyleListModel : [{"Name":"舒适型","Introduce":null,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":1,"CustomProperties":{}}]
         * BJHotelListModel : [{"Name":"花溪酒店","HotelStyle":"舒适型","EvaluationCount":0,"Scoring":5,"Description":null,"Phone1":null,"Phone2":null,"Address":null,"LowestPrice":200,"IsUsing":false,"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0001045.png","EventId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":2,"CustomProperties":{}},{"Name":"国际酒店","HotelStyle":"舒适型","EvaluationCount":0,"Scoring":4.7,"Description":null,"Phone1":null,"Phone2":null,"Address":null,"LowestPrice":200,"IsUsing":false,"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0001044.jpeg","EventId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":1,"CustomProperties":{}}]
         * Id : 0
         * CustomProperties : {}
         */

        private int pageIndex;
        private int totalPages;
        private int Id;
        private CustomPropertiesBean CustomProperties;
        private List<BJHotelStyleListModelBean> BJHotelStyleListModel;
        private List<BJHotelListModelBean> BJHotelListModel;

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

        public void setId(int id) {
            Id = id;
        }

        public CustomPropertiesBean getCustomProperties() {
            return CustomProperties;
        }

        public void setCustomProperties(CustomPropertiesBean customProperties) {
            CustomProperties = customProperties;
        }

        public List<BJHotelStyleListModelBean> getBJHotelStyleListModel() {
            return BJHotelStyleListModel;
        }

        public void setBJHotelStyleListModel(List<BJHotelStyleListModelBean> BJHotelStyleListModel) {
            this.BJHotelStyleListModel = BJHotelStyleListModel;
        }

        public List<BJHotelListModelBean> getBJHotelListModel() {
            return BJHotelListModel;
        }

        public void setBJHotelListModel(List<BJHotelListModelBean> BJHotelListModel) {
            this.BJHotelListModel = BJHotelListModel;
        }

        public static class CustomPropertiesBean {}
        public static class BJHotelStyleListModelBean {

            /**
             * Name : 舒适型
             * Introduce : null
             * CreateTime : /Date(-62135596800000)/
             * Note : null
             * Id : 1
             * CustomProperties : {}
             */

            private String Name;
            private Object Introduce;
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

            public Object getIntroduce() {
                return Introduce;
            }

            public void setIntroduce(Object Introduce) {
                this.Introduce = Introduce;
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
        public static class BJHotelListModelBean {

            /**
             * Name : 花溪酒店
             * HotelStyle : 舒适型
             * EvaluationCount : 0
             * Scoring : 5
             * Description : null
             * Phone1 : null
             * Phone2 : null
             * Address : null
             * LowestPrice : 200
             * IsUsing : false
             * Picture : 0
             * PictureUrl : http://www.baijar.com/content/images/thumbs/0001045.png
             * EventId : 0
             * CreateTime : /Date(-62135596800000)/
             * Note : null
             * Id : 2
             * CustomProperties : {}
             */

            private String Name;
            private String HotelStyle;
            private int EvaluationCount;
            private String Scoring;
            private Object Description;
            private Object Phone1;
            private Object Phone2;

            private Object Address;
            private int LowestPrice;
            private boolean IsUsing;
            private int Picture;
            private String PictureUrl;
            private int EventId;
            private String CreateTime;
            private Object Note;
            private int Id;
            private CustomPropertiesBean CustomProperties;

            public String getScoring() {
                return Scoring;
            }

            public void setScoring(String scoring) {
                Scoring = scoring;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getHotelStyle() {
                return HotelStyle;
            }

            public void setHotelStyle(String HotelStyle) {
                this.HotelStyle = HotelStyle;
            }

            public int getEvaluationCount() {
                return EvaluationCount;
            }

            public void setEvaluationCount(int EvaluationCount) {
                this.EvaluationCount = EvaluationCount;
            }

           /* public int getScoring() {
                return Scoring;
            }

            public void setScoring(int Scoring) {
                this.Scoring = Scoring;
            }*/

            public Object getDescription() {
                return Description;
            }

            public void setDescription(Object Description) {
                this.Description = Description;
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

            public Object getAddress() {
                return Address;
            }

            public void setAddress(Object Address) {
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
    }
}
