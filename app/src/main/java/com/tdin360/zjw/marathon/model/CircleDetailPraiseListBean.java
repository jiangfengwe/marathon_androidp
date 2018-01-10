package com.tdin360.zjw.marathon.model;

import java.util.List;

/**
 * Created by jeffery on 2017/12/11.
 */

public class CircleDetailPraiseListBean {

    /**
     * state : true
     * message : 请求成功
     * model : {"pageIndex":1,"totalPages":2,"TagUserListModel":[{"Id":0,"HeadImg":"http://www.baijar.com/content/images/thumbs/0001069_64090_120.jpg","Phone":null,"NickName":"","Gender":null,"Unionid":null,"IsBindPhone":false,"CustomerSign":null}],"Id":0,"CustomProperties":{}}
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
         * TagUserListModel : [{"Id":0,"HeadImg":"http://www.baijar.com/content/images/thumbs/0001069_64090_120.jpg","Phone":null,"NickName":"","Gender":null,"Unionid":null,"IsBindPhone":false,"CustomerSign":null}]
         * Id : 0
         * CustomProperties : {}
         */

        private int pageIndex;
        private int totalPages;
        private int Id;
        private CustomPropertiesBean CustomProperties;
        private List<TagUserListModelBean> TagUserListModel;

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

        public List<TagUserListModelBean> getTagUserListModel() {
            return TagUserListModel;
        }

        public void setTagUserListModel(List<TagUserListModelBean> TagUserListModel) {
            this.TagUserListModel = TagUserListModel;
        }

        public static class CustomPropertiesBean {
        }

        public static class TagUserListModelBean {
            /**
             * Id : 0
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
            private String CustomerSign;

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

            public String getCustomerSign() {
                return CustomerSign;
            }

            public void setCustomerSign(String CustomerSign) {
                this.CustomerSign = CustomerSign;
            }
        }
    }
}
