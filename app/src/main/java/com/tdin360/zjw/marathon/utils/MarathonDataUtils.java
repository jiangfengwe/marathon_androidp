package com.tdin360.zjw.marathon.utils;

/**
 * 公用赛事数据单例模型
 * Created by admin on 17/2/13.
 */

public class MarathonDataUtils {

    private static MarathonDataUtils dataUtils;
    private String eventId;
    private String eventName;

    private MarathonDataUtils (){}


    public static MarathonDataUtils init(){

        if(dataUtils==null){

            dataUtils = new MarathonDataUtils();
        }

        return dataUtils;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
