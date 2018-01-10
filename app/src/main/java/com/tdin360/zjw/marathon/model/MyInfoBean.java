package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2017/12/7.
 */

public class MyInfoBean {

    /**
     * model : {"Id":507,"HeadImg":"","Phone":null,"NickName":"用户507","Gender":false,"Unionid":null,"IsBindPhone":false,"CustomerSign":null}
     */

    private ModelBean model;

    public ModelBean getModel() {
        return model;
    }

    public void setModel(ModelBean model) {
        this.model = model;
    }

    public static class ModelBean {
        /**
         * Id : 507
         * HeadImg :
         * Phone : null
         * NickName : 用户507
         * Gender : false
         * Unionid : null
         * IsBindPhone : false
         * CustomerSign : null
         */

        private int Id;
        private String HeadImg;
        private String Phone;
        private String NickName;
        private boolean Gender;
        private int Unionid;
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

        public int getUnionid() {
            return Unionid;
        }

        public void setUnionid(int Unionid) {
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
