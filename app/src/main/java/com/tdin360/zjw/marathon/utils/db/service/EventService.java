package com.tdin360.zjw.marathon.utils.db.service;

import com.tdin360.zjw.marathon.model.EventModel;

import java.util.List;

/**
 * 赛事信息操作类
 * Created by admin on 17/3/22.
 */

public interface EventService {

    void addEvent(EventModel model);
    List<EventModel> getAllEvent();
    void deleteAll();
}
