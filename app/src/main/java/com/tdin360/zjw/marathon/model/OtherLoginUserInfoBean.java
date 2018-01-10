package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2017/12/9.
 */

public class OtherLoginUserInfoBean {

    /**
     * user : {"Id":2197,"HeadImg":"","Phone":null,"NickName":"小维","Gender":false,
     * "Unionid":"oPyIvv4euVFaE0JCNlKhQVBn8A78","IsBindPhone":false,"CustomerSign":null}
     */

    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * Id : 2197
         * HeadImg :
         * Phone : null
         * NickName : 小维
         * Gender : false
         * Unionid : oPyIvv4euVFaE0JCNlKhQVBn8A78
         * IsBindPhone : false
         * CustomerSign : null
         */

        private int Id;
        private String HeadImg;
        private String Phone;
        private String NickName;
        private boolean Gender;
        private String Unionid;
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

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String NickName) {
            this.NickName = NickName;
        }

        public boolean isGender() {
            return Gender;
        }

        public void setGender(boolean Gender) {
            this.Gender = Gender;
        }

        public String getUnionid() {
            return Unionid;
        }

        public void setUnionid(String Unionid) {
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
