package com.tdin360.zjw.marathon.utils.db.service;

import com.tdin360.zjw.marathon.model.UserModel;


/**
 * Created by admin on 17/3/22.
 */

public interface MyInfoService {


    void addInfo(UserModel model);

    UserModel getUserModel(String tel);

    void delete();
}
