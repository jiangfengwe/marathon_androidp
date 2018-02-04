package com.tdin360.zjw.marathon.utils.db.service;

import com.tdin360.zjw.marathon.model.CirclePriseTableModel;
import com.tdin360.zjw.marathon.model.SystemNoticeBean;

import java.util.List;

/**
 * 赛事详情信息操作类
 * Created by admin on 17/3/22.
 */

public interface SystemNoticeDetailService {

    void addSystemNotice(SystemNoticeBean model);
    List<SystemNoticeBean> getAllSystemNotice();
    void deleteAll(String eventId);
}
