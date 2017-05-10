package com.tdin360.zjw.marathon.utils.db.service;

import com.tdin360.zjw.marathon.model.NewsModel;
import com.tdin360.zjw.marathon.model.NoticeMessageModel;

import java.util.List;

/**
 * 新闻数据库操作接口
 * Created by admin on 17/2/16.
 */

public interface NewsService {


    void addNews(NewsModel model);
    boolean deleteNews(String eventId);
    boolean deleteAllNews();
    List<NewsModel> getAllNews(String eventId);
}
