package com.tdin360.zjw.marathon.model;

import java.util.List;

/**
 * Created by jeffery on 2017/12/20.
 */

public class MyCircleBean {


    /**
     * state : true
     * message : 请求成功
     * model : {"pageIndex":1,"totalPages":1,"NickName":"123456","Sign":"454564187","PictureUrl":"","BJDynamicListModel":[{"BJDynamicsPictureListModel":[],"UserModel":{"Id":0,"HeadImg":"","Phone":null,"NickName":"","Gender":null,"Unionid":null,"IsBindPhone":false,"CustomerSign":null},"DynamicsContent":"555555","ReleaseTime":"/Date(-62135596800000)/","ReleaseTimeStr":"2017-12-16 12:03","IsDelete":false,"IsChecked":null,"IsRecommend":false,"CommentNumber":0,"TagsNumber":0,"View":0,"Share":0,"CustomerId":0,"Id":9,"CustomProperties":{}},{"BJDynamicsPictureListModel":[],"UserModel":{"Id":0,"HeadImg":"","Phone":null,"NickName":"","Gender":null,"Unionid":null,"IsBindPhone":false,"CustomerSign":null},"DynamicsContent":"555555","ReleaseTime":"/Date(-62135596800000)/","ReleaseTimeStr":"2017-12-16 12:03","IsDelete":false,"IsChecked":null,"IsRecommend":false,"CommentNumber":0,"TagsNumber":0,"View":0,"Share":0,"CustomerId":0,"Id":8,"CustomProperties":{}},{"BJDynamicsPictureListModel":[],"UserModel":{"Id":0,"HeadImg":"","Phone":null,"NickName":"","Gender":null,"Unionid":null,"IsBindPhone":false,"CustomerSign":null},"DynamicsContent":"555555","ReleaseTime":"/Date(-62135596800000)/","ReleaseTimeStr":"2017-12-16 12:03","IsDelete":false,"IsChecked":null,"IsRecommend":false,"CommentNumber":0,"TagsNumber":0,"View":0,"Share":0,"CustomerId":0,"Id":7,"CustomProperties":{}},{"BJDynamicsPictureListModel":[{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002053.jpeg","PictureHeight":1080,"PictureWidth":1920,"ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002053_160.jpeg","DynamicsId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}}],"UserModel":{"Id":0,"HeadImg":"","Phone":null,"NickName":"","Gender":null,"Unionid":null,"IsBindPhone":false,"CustomerSign":null},"DynamicsContent":"反正武汉哈哈镜","ReleaseTime":"/Date(-62135596800000)/","ReleaseTimeStr":"2017-12-16 10:12","IsDelete":false,"IsChecked":null,"IsRecommend":false,"CommentNumber":0,"TagsNumber":0,"View":0,"Share":0,"CustomerId":0,"Id":6,"CustomProperties":{}}],"Id":0,"CustomProperties":{}}
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
         * NickName : 123456
         * Sign : 454564187
         * PictureUrl :
         * BJDynamicListModel : [{"BJDynamicsPictureListModel":[],"UserModel":{"Id":0,"HeadImg":"","Phone":null,"NickName":"","Gender":null,"Unionid":null,"IsBindPhone":false,"CustomerSign":null},"DynamicsContent":"555555","ReleaseTime":"/Date(-62135596800000)/","ReleaseTimeStr":"2017-12-16 12:03","IsDelete":false,"IsChecked":null,"IsRecommend":false,"CommentNumber":0,"TagsNumber":0,"View":0,"Share":0,"CustomerId":0,"Id":9,"CustomProperties":{}},{"BJDynamicsPictureListModel":[],"UserModel":{"Id":0,"HeadImg":"","Phone":null,"NickName":"","Gender":null,"Unionid":null,"IsBindPhone":false,"CustomerSign":null},"DynamicsContent":"555555","ReleaseTime":"/Date(-62135596800000)/","ReleaseTimeStr":"2017-12-16 12:03","IsDelete":false,"IsChecked":null,"IsRecommend":false,"CommentNumber":0,"TagsNumber":0,"View":0,"Share":0,"CustomerId":0,"Id":8,"CustomProperties":{}},{"BJDynamicsPictureListModel":[],"UserModel":{"Id":0,"HeadImg":"","Phone":null,"NickName":"","Gender":null,"Unionid":null,"IsBindPhone":false,"CustomerSign":null},"DynamicsContent":"555555","ReleaseTime":"/Date(-62135596800000)/","ReleaseTimeStr":"2017-12-16 12:03","IsDelete":false,"IsChecked":null,"IsRecommend":false,"CommentNumber":0,"TagsNumber":0,"View":0,"Share":0,"CustomerId":0,"Id":7,"CustomProperties":{}},{"BJDynamicsPictureListModel":[{"Picture":0,"PictureUrl":"http://www.baijar.com/content/images/thumbs/0002053.jpeg","PictureHeight":1080,"PictureWidth":1920,"ThumbPictureUrl":"http://www.baijar.com/content/images/thumbs/0002053_160.jpeg","DynamicsId":0,"CreateTime":"/Date(-62135596800000)/","Note":null,"Id":0,"CustomProperties":{}}],"UserModel":{"Id":0,"HeadImg":"","Phone":null,"NickName":"","Gender":null,"Unionid":null,"IsBindPhone":false,"CustomerSign":null},"DynamicsContent":"反正武汉哈哈镜","ReleaseTime":"/Date(-62135596800000)/","ReleaseTimeStr":"2017-12-16 10:12","IsDelete":false,"IsChecked":null,"IsRecommend":false,"CommentNumber":0,"TagsNumber":0,"View":0,"Share":0,"CustomerId":0,"Id":6,"CustomProperties":{}}]
         * Id : 0
         * CustomProperties : {}
         */

        private int pageIndex;
        private int totalPages;
        private String NickName;
        private String Sign;
        private String PictureUrl;
        private int Id;
        private CustomPropertiesBean CustomProperties;
        private List<BJDynamicListModelBean> BJDynamicListModel;

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

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String NickName) {
            this.NickName = NickName;
        }

        public String getSign() {
            return Sign;
        }

        public void setSign(String Sign) {
            this.Sign = Sign;
        }

        public String getPictureUrl() {
            return PictureUrl;
        }

        public void setPictureUrl(String PictureUrl) {
            this.PictureUrl = PictureUrl;
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

        public List<BJDynamicListModelBean> getBJDynamicListModel() {
            return BJDynamicListModel;
        }

        public void setBJDynamicListModel(List<BJDynamicListModelBean> BJDynamicListModel) {
            this.BJDynamicListModel = BJDynamicListModel;
        }

        public static class CustomPropertiesBean {
        }

        public static class BJDynamicListModelBean {
            /**
             * BJDynamicsPictureListModel : []
             * UserModel : {"Id":0,"HeadImg":"","Phone":null,"NickName":"","Gender":null,"Unionid":null,"IsBindPhone":false,"CustomerSign":null}
             * DynamicsContent : 555555
             * ReleaseTime : /Date(-62135596800000)/
             * ReleaseTimeStr : 2017-12-16 12:03
             * IsDelete : false
             * IsChecked : null
             * IsRecommend : false
             * CommentNumber : 0
             * TagsNumber : 0
             * View : 0
             * Share : 0
             * CustomerId : 0
             * Id : 9
             * CustomProperties : {}
             */

            private UserModelBean UserModel;
            private String DynamicsContent;
            private String ReleaseTime;
            private String ReleaseTimeStr;
            private boolean IsDelete;
            private Object IsChecked;
            private boolean IsRecommend;
            private int CommentNumber;
            private int TagsNumber;
            private int View;
            private int Share;
            private int CustomerId;
            private int Id;
            private CustomPropertiesBeanX CustomProperties;
            private List<BJDynamicsPictureListModel> BJDynamicsPictureListModel;

            public UserModelBean getUserModel() {
                return UserModel;
            }

            public void setUserModel(UserModelBean UserModel) {
                this.UserModel = UserModel;
            }

            public String getDynamicsContent() {
                return DynamicsContent;
            }

            public void setDynamicsContent(String DynamicsContent) {
                this.DynamicsContent = DynamicsContent;
            }

            public String getReleaseTime() {
                return ReleaseTime;
            }

            public void setReleaseTime(String ReleaseTime) {
                this.ReleaseTime = ReleaseTime;
            }

            public String getReleaseTimeStr() {
                return ReleaseTimeStr;
            }

            public void setReleaseTimeStr(String ReleaseTimeStr) {
                this.ReleaseTimeStr = ReleaseTimeStr;
            }

            public boolean isIsDelete() {
                return IsDelete;
            }

            public void setIsDelete(boolean IsDelete) {
                this.IsDelete = IsDelete;
            }

            public Object getIsChecked() {
                return IsChecked;
            }

            public void setIsChecked(Object IsChecked) {
                this.IsChecked = IsChecked;
            }

            public boolean isIsRecommend() {
                return IsRecommend;
            }

            public void setIsRecommend(boolean IsRecommend) {
                this.IsRecommend = IsRecommend;
            }

            public int getCommentNumber() {
                return CommentNumber;
            }

            public void setCommentNumber(int CommentNumber) {
                this.CommentNumber = CommentNumber;
            }

            public int getTagsNumber() {
                return TagsNumber;
            }

            public void setTagsNumber(int TagsNumber) {
                this.TagsNumber = TagsNumber;
            }

            public int getView() {
                return View;
            }

            public void setView(int View) {
                this.View = View;
            }

            public int getShare() {
                return Share;
            }

            public void setShare(int Share) {
                this.Share = Share;
            }

            public int getCustomerId() {
                return CustomerId;
            }

            public void setCustomerId(int CustomerId) {
                this.CustomerId = CustomerId;
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

            public List<BJDynamicsPictureListModel> getBJDynamicsPictureListModel() {
                return BJDynamicsPictureListModel;
            }

            public void setBJDynamicsPictureListModel(List<BJDynamicsPictureListModel> BJDynamicsPictureListModel) {
                this.BJDynamicsPictureListModel = BJDynamicsPictureListModel;
            }

            public static class UserModelBean {
            }

            public static class CustomPropertiesBeanX {
            }
            public static class BJDynamicsPictureListModel {
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
                private CustomPropertiesBeanXX CustomProperties;

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

                public CustomPropertiesBeanXX getCustomProperties() {
                    return CustomProperties;
                }

                public void setCustomProperties(CustomPropertiesBeanXX CustomProperties) {
                    this.CustomProperties = CustomProperties;
                }

                public static class CustomPropertiesBeanXX {
                }
            }

        }
    }
}
