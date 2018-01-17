package com.tdin360.zjw.marathon.utils.db.service;


import com.tdin360.zjw.marathon.model.NoticeModel;

import java.util.List;

/**
 * 公告数据库操作接口
 * Created by admin on 17/2/16.
 */

public interface NoticeService {
    void addNotice(NoticeModel model);
    boolean deleteNotice(String eventId);
    boolean deleteAllNotice();
    List<NoticeModel> getAllNotice(String eventId);
}
