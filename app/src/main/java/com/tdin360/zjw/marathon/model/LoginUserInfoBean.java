package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2017/12/6.
 */

public class LoginUserInfoBean {
    public static final String ID="id";
    public static final String HeadImg="headimg";
    public static final String Phone="phone";
    public static final String NickName="nickname";
    public static final String Gender="gender";
    public static final String Unionid="unionid";
    public static final String IsBindPhone="isbindphone";
    public static final String CustomerSign="customersign";
    public static final String Login="login";

    /**
     * user : {"Id":507,"HeadImg":"","Phone":null,"NickName":"用户507","Gender":false,"Unionid":null,"IsBindPhone":false,"CustomerSign":null}
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
         * Id : 507
         * HeadImg :
         * Phone : null
         * NickName : 用户507
         * Gender : false
         * Unionid : null
         * IsBindPhone : false
         * CustomerSign : null
         * CustomerAlias:vdOIByT0ygFEq3w
         */

        private String Id;
        private String HeadImg;
        private String Phone;
        private String NickName;
        private boolean Gender;
        private String Unionid;
        private boolean IsBindPhone;
        private String CustomerSign;
        private String CustomerAlias;
        private String login;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getCustomerAlias() {
            return CustomerAlias;
        }

        public void setCustomerAlias(String customerAlias) {
            CustomerAlias = customerAlias;
        }

        public UserBean(String Id, String HeadImg, String NickName, boolean Gender, String Unionid, boolean IsBindPhone, String CustomerSign, String Phone,String login) {
            this.Id=Id;
            this.HeadImg = HeadImg;
            this.NickName = NickName;
            this.Gender = Gender;
            this.Unionid=Unionid;
            this.IsBindPhone = IsBindPhone;
            this.CustomerSign = CustomerSign;
            this.Phone = Phone;
            this.login=login;

        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
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
