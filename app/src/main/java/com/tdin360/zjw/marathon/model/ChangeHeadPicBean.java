package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2017/12/8.
 */

public class ChangeHeadPicBean {

    /**
     * state : true
     * message : 成功
     * user : {"Id":0,"HeadImg":"http://www.baijar.com/content/images/thumbs/0001069_120.jpg","Phone":null,"NickName":"","Gender":null,"Unionid":null,"IsBindPhone":false,"CustomerSign":null}
     */

    private boolean state;
    private String message;
    private UserBean user;

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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * Id : 0
         * HeadImg : http://www.baijar.com/content/images/thumbs/0001069_120.jpg
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
}
