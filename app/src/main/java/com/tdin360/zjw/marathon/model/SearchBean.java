package com.tdin360.zjw.marathon.model;

import java.util.List;

/**
 * Created by jeffery on 2017/12/20.
 */

public class SearchBean {

    /**
     * state : true
     * message : 请求成功
     * model : {"pageIndex":1,"totalPages":2,"BJEventSystemListModel":[{"Name":"贵阳国际马拉松","Type":null,"Organizer":null,"Phone":null,"ApplyStartTime":null,"ApplyStartTimeStr":"暂定","ApplyEndTime":null,"ApplyEndTimeStr":"暂定","Place":null,"TotalPerson":null,"OurCharger":null,"IsRegister":false,"PersonConfigurationId":0,"PersonConfigurationStr":null,"EditionId":0,"EditionStr":null,"ProcessId":0,"ProcessStr":null,"StoreId":0,"StoreStr":null,"CustomerId":0,"CustomereStr":null,"Domain":null,"StartUsingTime":null,"StartUsingTimeStr":null,"EndUsingTime":null,"EndUsingTimeStr":null,"EventStartTime":null,"EventStartTimeStr":"暂定","EventAppCoverPicture":null,"EventAppCoverPictureUrl":"暂未设定封面图片","CreateTime":"/Date(-62135596800000)/","Note":null,"Statue":null,"IsObsolete":false,"Id":27,"CustomProperties":{}},{"Name":"3667贵阳马拉松","Type":null,"Organizer":null,"Phone":null,"ApplyStartTime":null,"ApplyStartTimeStr":"暂定","ApplyEndTime":null,"ApplyEndTimeStr":"暂定","Place":null,"TotalPerson":null,"OurCharger":null,"IsRegister":true,"PersonConfigurationId":0,"PersonConfigurationStr":null,"EditionId":0,"EditionStr":null,"ProcessId":0,"ProcessStr":null,"StoreId":0,"StoreStr":null,"CustomerId":0,"CustomereStr":null,"Domain":null,"StartUsingTime":null,"StartUsingTimeStr":null,"EndUsingTime":null,"EndUsingTimeStr":null,"EventStartTime":null,"EventStartTimeStr":"06-12 00:00:00","EventAppCoverPicture":null,"EventAppCoverPictureUrl":"http://www.baijar.com/content/images/thumbs/0002056.jpg","CreateTime":"/Date(-62135596800000)/","Note":null,"Statue":null,"IsObsolete":false,"Id":26,"CustomProperties":{}},{"Name":"129贵阳马拉松","Type":null,"Organizer":null,"Phone":null,"ApplyStartTime":null,"ApplyStartTimeStr":"暂定","ApplyEndTime":null,"ApplyEndTimeStr":"暂定","Place":null,"TotalPerson":null,"OurCharger":null,"IsRegister":false,"PersonConfigurationId":0,"PersonConfigurationStr":null,"EditionId":0,"EditionStr":null,"ProcessId":0,"ProcessStr":null,"StoreId":0,"StoreStr":null,"CustomerId":0,"CustomereStr":null,"Domain":null,"StartUsingTime":null,"StartUsingTimeStr":null,"EndUsingTime":null,"EndUsingTimeStr":null,"EventStartTime":null,"EventStartTimeStr":"12-28 00:00:00","EventAppCoverPicture":null,"EventAppCoverPictureUrl":"http://www.baijar.com/content/images/thumbs/0002055.jpg","CreateTime":"/Date(-62135596800000)/","Note":null,"Statue":null,"IsObsolete":false,"Id":25,"CustomProperties":{}}],"Id":0,"CustomProperties":{}}
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
         * BJEventSystemListModel : [{"Name":"贵阳国际马拉松","Type":null,"Organizer":null,"Phone":null,"ApplyStartTime":null,"ApplyStartTimeStr":"暂定","ApplyEndTime":null,"ApplyEndTimeStr":"暂定","Place":null,"TotalPerson":null,"OurCharger":null,"IsRegister":false,"PersonConfigurationId":0,"PersonConfigurationStr":null,"EditionId":0,"EditionStr":null,"ProcessId":0,"ProcessStr":null,"StoreId":0,"StoreStr":null,"CustomerId":0,"CustomereStr":null,"Domain":null,"StartUsingTime":null,"StartUsingTimeStr":null,"EndUsingTime":null,"EndUsingTimeStr":null,"EventStartTime":null,"EventStartTimeStr":"暂定","EventAppCoverPicture":null,"EventAppCoverPictureUrl":"暂未设定封面图片","CreateTime":"/Date(-62135596800000)/","Note":null,"Statue":null,"IsObsolete":false,"Id":27,"CustomProperties":{}},{"Name":"3667贵阳马拉松","Type":null,"Organizer":null,"Phone":null,"ApplyStartTime":null,"ApplyStartTimeStr":"暂定","ApplyEndTime":null,"ApplyEndTimeStr":"暂定","Place":null,"TotalPerson":null,"OurCharger":null,"IsRegister":true,"PersonConfigurationId":0,"PersonConfigurationStr":null,"EditionId":0,"EditionStr":null,"ProcessId":0,"ProcessStr":null,"StoreId":0,"StoreStr":null,"CustomerId":0,"CustomereStr":null,"Domain":null,"StartUsingTime":null,"StartUsingTimeStr":null,"EndUsingTime":null,"EndUsingTimeStr":null,"EventStartTime":null,"EventStartTimeStr":"06-12 00:00:00","EventAppCoverPicture":null,"EventAppCoverPictureUrl":"http://www.baijar.com/content/images/thumbs/0002056.jpg","CreateTime":"/Date(-62135596800000)/","Note":null,"Statue":null,"IsObsolete":false,"Id":26,"CustomProperties":{}},{"Name":"129贵阳马拉松","Type":null,"Organizer":null,"Phone":null,"ApplyStartTime":null,"ApplyStartTimeStr":"暂定","ApplyEndTime":null,"ApplyEndTimeStr":"暂定","Place":null,"TotalPerson":null,"OurCharger":null,"IsRegister":false,"PersonConfigurationId":0,"PersonConfigurationStr":null,"EditionId":0,"EditionStr":null,"ProcessId":0,"ProcessStr":null,"StoreId":0,"StoreStr":null,"CustomerId":0,"CustomereStr":null,"Domain":null,"StartUsingTime":null,"StartUsingTimeStr":null,"EndUsingTime":null,"EndUsingTimeStr":null,"EventStartTime":null,"EventStartTimeStr":"12-28 00:00:00","EventAppCoverPicture":null,"EventAppCoverPictureUrl":"http://www.baijar.com/content/images/thumbs/0002055.jpg","CreateTime":"/Date(-62135596800000)/","Note":null,"Statue":null,"IsObsolete":false,"Id":25,"CustomProperties":{}}]
         * Id : 0
         * CustomProperties : {}
         */

        private int pageIndex;
        private int totalPages;
        private int Id;
        private CustomPropertiesBean CustomProperties;
        private List<BJEventSystemListModelBean> BJEventSystemListModel;

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

        public List<BJEventSystemListModelBean> getBJEventSystemListModel() {
            return BJEventSystemListModel;
        }

        public void setBJEventSystemListModel(List<BJEventSystemListModelBean> BJEventSystemListModel) {
            this.BJEventSystemListModel = BJEventSystemListModel;
        }

        public static class CustomPropertiesBean {
        }

        public static class BJEventSystemListModelBean {
            /**
             * Name : 贵阳国际马拉松
             * Type : null
             * Organizer : null
             * Phone : null
             * ApplyStartTime : null
             * ApplyStartTimeStr : 暂定
             * ApplyEndTime : null
             * ApplyEndTimeStr : 暂定
             * Place : null
             * TotalPerson : null
             * OurCharger : null
             * IsRegister : false
             * PersonConfigurationId : 0
             * PersonConfigurationStr : null
             * EditionId : 0
             * EditionStr : null
             * ProcessId : 0
             * ProcessStr : null
             * StoreId : 0
             * StoreStr : null
             * CustomerId : 0
             * CustomereStr : null
             * Domain : null
             * StartUsingTime : null
             * StartUsingTimeStr : null
             * EndUsingTime : null
             * EndUsingTimeStr : null
             * EventStartTime : null
             * EventStartTimeStr : 暂定
             * EventAppCoverPicture : null
             * EventAppCoverPictureUrl : 暂未设定封面图片
             * CreateTime : /Date(-62135596800000)/
             * Note : null
             * Statue : null
             * IsObsolete : false
             * Id : 27
             * CustomProperties : {}
             */

            private String Name;
            private Object Type;
            private Object Organizer;
            private Object Phone;
            private Object ApplyStartTime;
            private String ApplyStartTimeStr;
            private Object ApplyEndTime;
            private String ApplyEndTimeStr;
            private Object Place;
            private Object TotalPerson;
            private Object OurCharger;
            private boolean IsRegister;
            private int PersonConfigurationId;
            private Object PersonConfigurationStr;
            private int EditionId;
            private Object EditionStr;
            private int ProcessId;
            private Object ProcessStr;
            private int StoreId;
            private Object StoreStr;
            private int CustomerId;
            private Object CustomereStr;
            private Object Domain;
            private Object StartUsingTime;
            private Object StartUsingTimeStr;
            private Object EndUsingTime;
            private Object EndUsingTimeStr;
            private Object EventStartTime;
            private String EventStartTimeStr;
            private Object EventAppCoverPicture;
            private String EventAppCoverPictureUrl;
            private String CreateTime;
            private Object Note;
            private Object Statue;
            private boolean IsObsolete;
            private int Id;
            private String Url;
            private CustomPropertiesBeanX CustomProperties;

            public String getUrl() {
                return Url;
            }

            public void setUrl(String url) {
                Url = url;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public Object getType() {
                return Type;
            }

            public void setType(Object Type) {
                this.Type = Type;
            }

            public Object getOrganizer() {
                return Organizer;
            }

            public void setOrganizer(Object Organizer) {
                this.Organizer = Organizer;
            }

            public Object getPhone() {
                return Phone;
            }

            public void setPhone(Object Phone) {
                this.Phone = Phone;
            }

            public Object getApplyStartTime() {
                return ApplyStartTime;
            }

            public void setApplyStartTime(Object ApplyStartTime) {
                this.ApplyStartTime = ApplyStartTime;
            }

            public String getApplyStartTimeStr() {
                return ApplyStartTimeStr;
            }

            public void setApplyStartTimeStr(String ApplyStartTimeStr) {
                this.ApplyStartTimeStr = ApplyStartTimeStr;
            }

            public Object getApplyEndTime() {
                return ApplyEndTime;
            }

            public void setApplyEndTime(Object ApplyEndTime) {
                this.ApplyEndTime = ApplyEndTime;
            }

            public String getApplyEndTimeStr() {
                return ApplyEndTimeStr;
            }

            public void setApplyEndTimeStr(String ApplyEndTimeStr) {
                this.ApplyEndTimeStr = ApplyEndTimeStr;
            }

            public Object getPlace() {
                return Place;
            }

            public void setPlace(Object Place) {
                this.Place = Place;
            }

            public Object getTotalPerson() {
                return TotalPerson;
            }

            public void setTotalPerson(Object TotalPerson) {
                this.TotalPerson = TotalPerson;
            }

            public Object getOurCharger() {
                return OurCharger;
            }

            public void setOurCharger(Object OurCharger) {
                this.OurCharger = OurCharger;
            }

            public boolean isIsRegister() {
                return IsRegister;
            }

            public void setIsRegister(boolean IsRegister) {
                this.IsRegister = IsRegister;
            }

            public int getPersonConfigurationId() {
                return PersonConfigurationId;
            }

            public void setPersonConfigurationId(int PersonConfigurationId) {
                this.PersonConfigurationId = PersonConfigurationId;
            }

            public Object getPersonConfigurationStr() {
                return PersonConfigurationStr;
            }

            public void setPersonConfigurationStr(Object PersonConfigurationStr) {
                this.PersonConfigurationStr = PersonConfigurationStr;
            }

            public int getEditionId() {
                return EditionId;
            }

            public void setEditionId(int EditionId) {
                this.EditionId = EditionId;
            }

            public Object getEditionStr() {
                return EditionStr;
            }

            public void setEditionStr(Object EditionStr) {
                this.EditionStr = EditionStr;
            }

            public int getProcessId() {
                return ProcessId;
            }

            public void setProcessId(int ProcessId) {
                this.ProcessId = ProcessId;
            }

            public Object getProcessStr() {
                return ProcessStr;
            }

            public void setProcessStr(Object ProcessStr) {
                this.ProcessStr = ProcessStr;
            }

            public int getStoreId() {
                return StoreId;
            }

            public void setStoreId(int StoreId) {
                this.StoreId = StoreId;
            }

            public Object getStoreStr() {
                return StoreStr;
            }

            public void setStoreStr(Object StoreStr) {
                this.StoreStr = StoreStr;
            }

            public int getCustomerId() {
                return CustomerId;
            }

            public void setCustomerId(int CustomerId) {
                this.CustomerId = CustomerId;
            }

            public Object getCustomereStr() {
                return CustomereStr;
            }

            public void setCustomereStr(Object CustomereStr) {
                this.CustomereStr = CustomereStr;
            }

            public Object getDomain() {
                return Domain;
            }

            public void setDomain(Object Domain) {
                this.Domain = Domain;
            }

            public Object getStartUsingTime() {
                return StartUsingTime;
            }

            public void setStartUsingTime(Object StartUsingTime) {
                this.StartUsingTime = StartUsingTime;
            }

            public Object getStartUsingTimeStr() {
                return StartUsingTimeStr;
            }

            public void setStartUsingTimeStr(Object StartUsingTimeStr) {
                this.StartUsingTimeStr = StartUsingTimeStr;
            }

            public Object getEndUsingTime() {
                return EndUsingTime;
            }

            public void setEndUsingTime(Object EndUsingTime) {
                this.EndUsingTime = EndUsingTime;
            }

            public Object getEndUsingTimeStr() {
                return EndUsingTimeStr;
            }

            public void setEndUsingTimeStr(Object EndUsingTimeStr) {
                this.EndUsingTimeStr = EndUsingTimeStr;
            }

            public Object getEventStartTime() {
                return EventStartTime;
            }

            public void setEventStartTime(Object EventStartTime) {
                this.EventStartTime = EventStartTime;
            }

            public String getEventStartTimeStr() {
                return EventStartTimeStr;
            }

            public void setEventStartTimeStr(String EventStartTimeStr) {
                this.EventStartTimeStr = EventStartTimeStr;
            }

            public Object getEventAppCoverPicture() {
                return EventAppCoverPicture;
            }

            public void setEventAppCoverPicture(Object EventAppCoverPicture) {
                this.EventAppCoverPicture = EventAppCoverPicture;
            }

            public String getEventAppCoverPictureUrl() {
                return EventAppCoverPictureUrl;
            }

            public void setEventAppCoverPictureUrl(String EventAppCoverPictureUrl) {
                this.EventAppCoverPictureUrl = EventAppCoverPictureUrl;
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

            public Object getStatue() {
                return Statue;
            }

            public void setStatue(Object Statue) {
                this.Statue = Statue;
            }

            public boolean isIsObsolete() {
                return IsObsolete;
            }

            public void setIsObsolete(boolean IsObsolete) {
                this.IsObsolete = IsObsolete;
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
