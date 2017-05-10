package com.tdin360.zjw.marathon.utils.db.service;

import com.tdin360.zjw.marathon.model.CarouselModel;
import java.util.List;

/**
 * 赛事详情信息操作类
 * Created by admin on 17/3/22.
 */

public interface EventDetailService {

    void addEventDetail(CarouselModel model);
    List<CarouselModel> getAllEventDetail(String eventId,String type);
    void deleteAll(String eventId);
}
