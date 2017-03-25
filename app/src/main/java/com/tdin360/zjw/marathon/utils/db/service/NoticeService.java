package com.tdin360.zjw.marathon.utils.db.service;

import com.tdin360.zjw.marathon.model.NoticeMessageModel;

import java.util.List;

/**
 * 通知消息数据库操作接口
 * Created by admin on 17/2/16.
 */

public interface NoticeService {


    void addNotice(NoticeMessageModel model);
    boolean deleteNotice(int id);
    boolean deleteAllNotice();
    List<NoticeMessageModel> getAllNotice();
}
