package com.tdin360.zjw.marathon.model;

import java.util.List;

/**
 * Created by jeffery on 2017/12/12.
 */

public class CircleDetailAllCommentBean {


    /**
     * state : true
     * message : 请求成功
     * CommentModel : {"pageIndex":1,"totalPages":1,"CustomerId":507,"NickName":"","HeadImg":"http://www.baijar.com/content/images/thumbs/0001069_64090_120.jpg","CommentContent":"jhfyteqwurqiiq","CreateTimeStr":"2017-12-12","CommentCount":0,"ReplayCommentList":[{"DynamicsId":0,"CustomerId":507,"NickName":"","HeadImg":"http://www.baijar.com/content/images/thumbs/0001069_64090_120.jpg","ReplayerNickName":null,"CommentId":0,"CommentContent":"kkkkkkkk","CommentCount":0,"CreateTime":"/Date(1513047749217)/","CreateTimeStr":"2017-12-12","Note":null,"Id":37,"CustomProperties":{}}],"Id":35,"CustomProperties":{}}
     */

    private boolean state;
    private String message;
    private CommentModelBean CommentModel;

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

    public CommentModelBean getCommentModel() {
        return CommentModel;
    }

    public void setCommentModel(CommentModelBean CommentModel) {
        this.CommentModel = CommentModel;
    }

    public static class CommentModelBean {
        /**
         * pageIndex : 1
         * totalPages : 1
         * CustomerId : 507
         * NickName :
         * HeadImg : http://www.baijar.com/content/images/thumbs/0001069_64090_120.jpg
         * CommentContent : jhfyteqwurqiiq
         * CreateTimeStr : 2017-12-12
         * CommentCount : 0
         * ReplayCommentList : [{"DynamicsId":0,"CustomerId":507,"NickName":"","HeadImg":"http://www.baijar.com/content/images/thumbs/0001069_64090_120.jpg","ReplayerNickName":null,"CommentId":0,"CommentContent":"kkkkkkkk","CommentCount":0,"CreateTime":"/Date(1513047749217)/","CreateTimeStr":"2017-12-12","Note":null,"Id":37,"CustomProperties":{}}]
         * Id : 35
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
        private CustomPropertiesBean CustomProperties;
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

        public CustomPropertiesBean getCustomProperties() {
            return CustomProperties;
        }

        public void setCustomProperties(CustomPropertiesBean CustomProperties) {
            this.CustomProperties = CustomProperties;
        }

        public List<ReplayCommentListBean> getReplayCommentList() {
            return ReplayCommentList;
        }

        public void setReplayCommentList(List<ReplayCommentListBean> ReplayCommentList) {
            this.ReplayCommentList = ReplayCommentList;
        }

        public static class CustomPropertiesBean {
        }

        public static class ReplayCommentListBean {
            /**
             * DynamicsId : 0
             * CustomerId : 507
             * NickName :
             * HeadImg : http://www.baijar.com/content/images/thumbs/0001069_64090_120.jpg
             * ReplayerNickName : null
             * CommentId : 0
             * CommentContent : kkkkkkkk
             * CommentCount : 0
             * CreateTime : /Date(1513047749217)/
             * CreateTimeStr : 2017-12-12
             * Note : null
             * Id : 37
             * CustomProperties : {}
             */

            private int DynamicsId;
            private int CustomerId;
            private String NickName;
            private String HeadImg;
            private String ReplayerNickName;
            private int CommentId;
            private String CommentContent;
            private int CommentCount;
            private String CreateTime;
            private String CreateTimeStr;
            private Object Note;
            private int Id;
            private CustomPropertiesBeanX CustomProperties;

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

            public String getHeadImg() {
                return HeadImg;
            }

            public void setHeadImg(String HeadImg) {
                this.HeadImg = HeadImg;
            }

            public String getReplayerNickName() {
                return ReplayerNickName;
            }

            public void setReplayerNickName(String ReplayerNickName) {
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

            public String getCreateTimeStr() {
                return CreateTimeStr;
            }

            public void setCreateTimeStr(String CreateTimeStr) {
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
