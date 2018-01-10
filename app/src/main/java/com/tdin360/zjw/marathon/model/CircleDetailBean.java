package com.tdin360.zjw.marathon.model;

import java.util.List;

/**
 * Created by jeffery on 2017/12/11.
 */

public class CircleDetailBean {


    /**
     * state : true
     * message : 请求成功
     * model : {"pageIndex":1,"totalPages":2,"DynamicsContent":"","TagsNumber":1,"CustomerId":0,"BJDynamicsPictureListModel":[{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0001108.jpeg","PictureHeight":1485,"PictureWidth":1980,"ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0001108_160.jpeg","DynamicsId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0001107.jpeg","PictureHeight":1485,"PictureWidth":1980,"ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0001107_160.jpeg","DynamicsId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}}],"UserModel":{"Id":507,"HeadImg":"http://www.baijar.com/content/images/thumbs/0001069_64090_120.jpg","Phone":null,"NickName":"","Gender":null,"Unionid":null,"IsBindPhone":false,"CustomerSign":null},"TagUserListModel":[{"Id":810,"HeadImg":"http://www.baijar.com/content/images/thumbs/0001070_120.jpg","Phone":null,"NickName":"","Gender":null,"Unionid":null,"IsBindPhone":false,"CustomerSign":null}],"CommentUserListModel":[],"BJDynamicsCommentListModel":[{"pageIndex":0,"totalPages":0,"CustomerId":810,"NickName":"🤓🤓🤓","HeadImg":"http://www.baijar.com/content/images/thumbs/0001070_120.jpg","CommentContent":"Bhjniyff","CreateTimeStr":"2017-12-09","CommentCount":7,"ReplayCommentList":[{"DynamicsId":0,"CustomerId":0,"NickName":"🤓🤓🤓","HeadImg":null,"ReplayerNickName":null,"CommentId":0,"CommentContent":"📳🈶❌❌⭕️⭕️","CommentCount":0,"CreateTime":"/Date(1512962436330)/","CreateTimeStr":null,"Note":null,"Id":27,"CustomProperties":{}},{"DynamicsId":0,"CustomerId":0,"NickName":"🤓🤓🤓","HeadImg":null,"ReplayerNickName":null,"CommentId":0,"CommentContent":"Gggg","CommentCount":0,"CreateTime":"/Date(1512962459917)/","CreateTimeStr":null,"Note":null,"Id":28,"CustomProperties":{}}],"Id":14,"CustomProperties":{}},{"pageIndex":0,"totalPages":0,"CustomerId":810,"NickName":"🤓🤓🤓","HeadImg":"http://www.baijar.com/content/images/thumbs/0001070_120.jpg","CommentContent":"We'd","CreateTimeStr":"2017-12-09","CommentCount":1,"ReplayCommentList":[{"DynamicsId":0,"CustomerId":0,"NickName":"🤓🤓🤓","HeadImg":null,"ReplayerNickName":null,"CommentId":0,"CommentContent":"Xxvvv♐️♑️","CommentCount":0,"CreateTime":"/Date(1512963307537)/","CreateTimeStr":null,"Note":null,"Id":29,"CustomProperties":{}}],"Id":13,"CustomProperties":{}},{"pageIndex":0,"totalPages":0,"CustomerId":810,"NickName":"🤓🤓🤓","HeadImg":"http://www.baijar.com/content/images/thumbs/0001070_120.jpg","CommentContent":"Ryuijjgf","CreateTimeStr":"2017-12-09","CommentCount":0,"ReplayCommentList":[],"Id":12,"CustomProperties":{}},{"pageIndex":0,"totalPages":0,"CustomerId":810,"NickName":"🤓🤓🤓","HeadImg":"http://www.baijar.com/content/images/thumbs/0001070_120.jpg","CommentContent":"Vhhhbhh","CreateTimeStr":"2017-12-09","CommentCount":0,"ReplayCommentList":[],"Id":11,"CustomProperties":{}},{"pageIndex":0,"totalPages":0,"CustomerId":810,"NickName":"🤓🤓🤓","HeadImg":"http://www.baijar.com/content/images/thumbs/0001070_120.jpg","CommentContent":"Qeeddff😐🐵🐵🐔🐥🐝","CreateTimeStr":"2017-12-09","CommentCount":2,"ReplayCommentList":[{"DynamicsId":0,"CustomerId":0,"NickName":"🤓🤓🤓","HeadImg":null,"ReplayerNickName":null,"CommentId":0,"CommentContent":"Gghhjjjj","CommentCount":0,"CreateTime":"/Date(1512960184547)/","CreateTimeStr":null,"Note":null,"Id":23,"CustomProperties":{}}],"Id":8,"CustomProperties":{}}],"Id":0,"CustomProperties":{}}
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

    public static class ModelBean {
        /**
         * pageIndex : 1
         * totalPages : 2
         * DynamicsContent :
         * TagsNumber : 1
         * CustomerId : 0
         * BJDynamicsPictureListModel : [{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0001108.jpeg","PictureHeight":1485,"PictureWidth":1980,"ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0001108_160.jpeg","DynamicsId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}},{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0001107.jpeg","PictureHeight":1485,"PictureWidth":1980,"ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0001107_160.jpeg","DynamicsId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}}]
         * UserModel : {"Id":507,"HeadImg":"http://www.baijar.com/content/images/thumbs/0001069_64090_120.jpg","Phone":null,"NickName":"","Gender":null,"Unionid":null,"IsBindPhone":false,"CustomerSign":null}
         * TagUserListModel : [{"Id":810,"HeadImg":"http://www.baijar.com/content/images/thumbs/0001070_120.jpg","Phone":null,"NickName":"","Gender":null,"Unionid":null,"IsBindPhone":false,"CustomerSign":null}]
         * CommentUserListModel : []
         * BJDynamicsCommentListModel : [{"pageIndex":0,"totalPages":0,"CustomerId":810,"NickName":"🤓🤓🤓","HeadImg":"http://www.baijar.com/content/images/thumbs/0001070_120.jpg","CommentContent":"Bhjniyff","CreateTimeStr":"2017-12-09","CommentCount":7,"ReplayCommentList":[{"DynamicsId":0,"CustomerId":0,"NickName":"🤓🤓🤓","HeadImg":null,"ReplayerNickName":null,"CommentId":0,"CommentContent":"📳🈶❌❌⭕️⭕️","CommentCount":0,"CreateTime":"/Date(1512962436330)/","CreateTimeStr":null,"Note":null,"Id":27,"CustomProperties":{}},{"DynamicsId":0,"CustomerId":0,"NickName":"🤓🤓🤓","HeadImg":null,"ReplayerNickName":null,"CommentId":0,"CommentContent":"Gggg","CommentCount":0,"CreateTime":"/Date(1512962459917)/","CreateTimeStr":null,"Note":null,"Id":28,"CustomProperties":{}}],"Id":14,"CustomProperties":{}},{"pageIndex":0,"totalPages":0,"CustomerId":810,"NickName":"🤓🤓🤓","HeadImg":"http://www.baijar.com/content/images/thumbs/0001070_120.jpg","CommentContent":"We'd","CreateTimeStr":"2017-12-09","CommentCount":1,"ReplayCommentList":[{"DynamicsId":0,"CustomerId":0,"NickName":"🤓🤓🤓","HeadImg":null,"ReplayerNickName":null,"CommentId":0,"CommentContent":"Xxvvv♐️♑️","CommentCount":0,"CreateTime":"/Date(1512963307537)/","CreateTimeStr":null,"Note":null,"Id":29,"CustomProperties":{}}],"Id":13,"CustomProperties":{}},{"pageIndex":0,"totalPages":0,"CustomerId":810,"NickName":"🤓🤓🤓","HeadImg":"http://www.baijar.com/content/images/thumbs/0001070_120.jpg","CommentContent":"Ryuijjgf","CreateTimeStr":"2017-12-09","CommentCount":0,"ReplayCommentList":[],"Id":12,"CustomProperties":{}},{"pageIndex":0,"totalPages":0,"CustomerId":810,"NickName":"🤓🤓🤓","HeadImg":"http://www.baijar.com/content/images/thumbs/0001070_120.jpg","CommentContent":"Vhhhbhh","CreateTimeStr":"2017-12-09","CommentCount":0,"ReplayCommentList":[],"Id":11,"CustomProperties":{}},{"pageIndex":0,"totalPages":0,"CustomerId":810,"NickName":"🤓🤓🤓","HeadImg":"http://www.baijar.com/content/images/thumbs/0001070_120.jpg","CommentContent":"Qeeddff😐🐵🐵🐔🐥🐝","CreateTimeStr":"2017-12-09","CommentCount":2,"ReplayCommentList":[{"DynamicsId":0,"CustomerId":0,"NickName":"🤓🤓🤓","HeadImg":null,"ReplayerNickName":null,"CommentId":0,"CommentContent":"Gghhjjjj","CommentCount":0,"CreateTime":"/Date(1512960184547)/","CreateTimeStr":null,"Note":null,"Id":23,"CustomProperties":{}}],"Id":8,"CustomProperties":{}}]
         * Id : 0
         * CustomProperties : {}
         */

        private int pageIndex;
        private int totalPages;
        private String DynamicsContent;
        private int TagsNumber;
        private int CustomerId;
        private UserModelBean UserModel;
        private int Id;
        private CustomPropertiesBean CustomProperties;
        private List<BJDynamicsPictureListModelBean> BJDynamicsPictureListModel;
        private List<TagUserListModelBean> TagUserListModel;
        private List<?> CommentUserListModel;
        private List<BJDynamicsCommentListModelBean> BJDynamicsCommentListModel;

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

        public String getDynamicsContent() {
            return DynamicsContent;
        }

        public void setDynamicsContent(String DynamicsContent) {
            this.DynamicsContent = DynamicsContent;
        }

        public int getTagsNumber() {
            return TagsNumber;
        }

        public void setTagsNumber(int TagsNumber) {
            this.TagsNumber = TagsNumber;
        }

        public int getCustomerId() {
            return CustomerId;
        }

        public void setCustomerId(int CustomerId) {
            this.CustomerId = CustomerId;
        }

        public UserModelBean getUserModel() {
            return UserModel;
        }

        public void setUserModel(UserModelBean UserModel) {
            this.UserModel = UserModel;
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

        public List<BJDynamicsPictureListModelBean> getBJDynamicsPictureListModel() {
            return BJDynamicsPictureListModel;
        }

        public void setBJDynamicsPictureListModel(List<BJDynamicsPictureListModelBean> BJDynamicsPictureListModel) {
            this.BJDynamicsPictureListModel = BJDynamicsPictureListModel;
        }

        public List<TagUserListModelBean> getTagUserListModel() {
            return TagUserListModel;
        }

        public void setTagUserListModel(List<TagUserListModelBean> TagUserListModel) {
            this.TagUserListModel = TagUserListModel;
        }

        public List<?> getCommentUserListModel() {
            return CommentUserListModel;
        }

        public void setCommentUserListModel(List<?> CommentUserListModel) {
            this.CommentUserListModel = CommentUserListModel;
        }

        public List<BJDynamicsCommentListModelBean> getBJDynamicsCommentListModel() {
            return BJDynamicsCommentListModel;
        }

        public void setBJDynamicsCommentListModel(List<BJDynamicsCommentListModelBean> BJDynamicsCommentListModel) {
            this.BJDynamicsCommentListModel = BJDynamicsCommentListModel;
        }

        public static class UserModelBean {


            /**
             * Id : 507
             * HeadImg : http://www.baijar.com/content/images/thumbs/0001069_64090_120.jpg
             * Phone : null
             * NickName :
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

        public static class BJDynamicsPictureListModelBean {
            /**
             * Picture : 0
             * PictureUrl : http://www.baijar.com/content/images/thumbs/0001108.jpeg
             * PictureHeight : 1485
             * PictureWidth : 1980
             * ThumbPictureUrl : http://www.baijar.com/content/images/thumbs/0001108_160.jpeg
             * DynamicsId : 0
             * CreateTime : /Date(-62135596800000)/
             * Note : null
             * Id : 0
             * CustomProperties : {}
             */

            private int Picture;
            private String PictureUrl;
            private int PictureHeight;
            private int PictureWidth;
            private String ThumbPictureUrl;
            private int DynamicsId;
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

            public String getThumbPictureUrl() {
                return ThumbPictureUrl;
            }

            public void setThumbPictureUrl(String ThumbPictureUrl) {
                this.ThumbPictureUrl = ThumbPictureUrl;
            }

            public int getDynamicsId() {
                return DynamicsId;
            }

            public void setDynamicsId(int DynamicsId) {
                this.DynamicsId = DynamicsId;
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

        public static class TagUserListModelBean {
            /**
             * Id : 810
             * HeadImg : http://www.baijar.com/content/images/thumbs/0001070_120.jpg
             * Phone : null
             * NickName :
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

        public static class BJDynamicsCommentListModelBean {
            /**
             * pageIndex : 0
             * totalPages : 0
             * CustomerId : 810
             * NickName : 🤓🤓🤓
             * HeadImg : http://www.baijar.com/content/images/thumbs/0001070_120.jpg
             * CommentContent : Bhjniyff
             * CreateTimeStr : 2017-12-09
             * CommentCount : 7
             * ReplayCommentList : [{"DynamicsId":0,"CustomerId":0,"NickName":"🤓🤓🤓","HeadImg":null,"ReplayerNickName":null,"CommentId":0,"CommentContent":"📳🈶❌❌⭕️⭕️","CommentCount":0,"CreateTime":"/Date(1512962436330)/","CreateTimeStr":null,"Note":null,"Id":27,"CustomProperties":{}},{"DynamicsId":0,"CustomerId":0,"NickName":"🤓🤓🤓","HeadImg":null,"ReplayerNickName":null,"CommentId":0,"CommentContent":"Gggg","CommentCount":0,"CreateTime":"/Date(1512962459917)/","CreateTimeStr":null,"Note":null,"Id":28,"CustomProperties":{}}]
             * Id : 14
             * CustomProperties : {}
             */

            private int pageIndex;
            private int totalPages;
            private int CustomerId;
            private String NickName;
            private String HeadImg;
            private String CommentContent;
            private String CreateTimeStr;
            private int CommentCount;
            private int Id;
            private CustomPropertiesBeanXX CustomProperties;
            private List<ReplayCommentListBean> ReplayCommentList;

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

            public int getCustomerId() {
                return CustomerId;
            }

            public void setCustomerId(int CustomerId) {
                this.CustomerId = CustomerId;
            }

            public String getNickName() {
                return NickName;
            }

            public void setNickName(String NickName) {
                this.NickName = NickName;
            }

            public String getHeadImg() {
                return HeadImg;
            }

            public void setHeadImg(String HeadImg) {
                this.HeadImg = HeadImg;
            }

            public String getCommentContent() {
                return CommentContent;
            }

            public void setCommentContent(String CommentContent) {
                this.CommentContent = CommentContent;
            }

            public String getCreateTimeStr() {
                return CreateTimeStr;
            }

            public void setCreateTimeStr(String CreateTimeStr) {
                this.CreateTimeStr = CreateTimeStr;
            }

            public int getCommentCount() {
                return CommentCount;
            }

            public void setCommentCount(int CommentCount) {
                this.CommentCount = CommentCount;
            }

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public CustomPropertiesBeanXX getCustomProperties() {
                return CustomProperties;
            }

            public void setCustomProperties(CustomPropertiesBeanXX CustomProperties) {
                this.CustomProperties = CustomProperties;
            }

            public List<ReplayCommentListBean> getReplayCommentList() {
                return ReplayCommentList;
            }

            public void setReplayCommentList(List<ReplayCommentListBean> ReplayCommentList) {
                this.ReplayCommentList = ReplayCommentList;
            }

            public static class CustomPropertiesBeanXX {
            }

            public static class ReplayCommentListBean {
                /**
                 * DynamicsId : 0
                 * CustomerId : 0
                 * NickName : 🤓🤓🤓
                 * HeadImg : null
                 * ReplayerNickName : null
                 * CommentId : 0
                 * CommentContent : 📳🈶❌❌⭕️⭕️
                 * CommentCount : 0
                 * CreateTime : /Date(1512962436330)/
                 * CreateTimeStr : null
                 * Note : null
                 * Id : 27
                 * CustomProperties : {}
                 */

                private int DynamicsId;
                private int CustomerId;
                private String NickName;
                private Object HeadImg;
                private Object ReplayerNickName;
                private int CommentId;
                private String CommentContent;
                private int CommentCount;
                private String CreateTime;
                private Object CreateTimeStr;
                private Object Note;
                private int Id;
                private CustomPropertiesBeanXXX CustomProperties;

                public int getDynamicsId() {
                    return DynamicsId;
                }

                public void setDynamicsId(int DynamicsId) {
                    this.DynamicsId = DynamicsId;
                }

                public int getCustomerId() {
                    return CustomerId;
                }

                public void setCustomerId(int CustomerId) {
                    this.CustomerId = CustomerId;
                }

                public String getNickName() {
                    return NickName;
                }

                public void setNickName(String NickName) {
                    this.NickName = NickName;
                }

                public Object getHeadImg() {
                    return HeadImg;
                }

                public void setHeadImg(Object HeadImg) {
                    this.HeadImg = HeadImg;
                }

                public Object getReplayerNickName() {
                    return ReplayerNickName;
                }

                public void setReplayerNickName(Object ReplayerNickName) {
                    this.ReplayerNickName = ReplayerNickName;
                }

                public int getCommentId() {
                    return CommentId;
                }

                public void setCommentId(int CommentId) {
                    this.CommentId = CommentId;
                }

                public String getCommentContent() {
                    return CommentContent;
                }

                public void setCommentContent(String CommentContent) {
                    this.CommentContent = CommentContent;
                }

                public int getCommentCount() {
                    return CommentCount;
                }

                public void setCommentCount(int CommentCount) {
                    this.CommentCount = CommentCount;
                }

                public String getCreateTime() {
                    return CreateTime;
                }

                public void setCreateTime(String CreateTime) {
                    this.CreateTime = CreateTime;
                }

                public Object getCreateTimeStr() {
                    return CreateTimeStr;
                }

                public void setCreateTimeStr(Object CreateTimeStr) {
                    this.CreateTimeStr = CreateTimeStr;
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

                public CustomPropertiesBeanXXX getCustomProperties() {
                    return CustomProperties;
                }

                public void setCustomProperties(CustomPropertiesBeanXXX CustomProperties) {
                    this.CustomProperties = CustomProperties;
                }

                public static class CustomPropertiesBeanXXX {
                }
            }
        }
    }
}
