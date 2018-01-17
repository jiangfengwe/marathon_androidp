package com.tdin360.zjw.marathon.utils.db.service;

import com.tdin360.zjw.marathon.model.CarouselModel;
import com.tdin360.zjw.marathon.model.CirclePriseTableModel;

import java.util.List;

/**
 * 赛事详情信息操作类
 * Created by admin on 17/3/22.
 */

public interface CircleNoticeDetailService {

    void addCircleNotice(CirclePriseTableModel model);
    List<CirclePriseTableModel> getAllCircleNotice();
    void deleteAll(String eventId);
}
