package com.tdin360.zjw.marathon.model;

import java.io.Serializable;

/**
 * 成绩列表模型
 * Created by admin on 17/3/21.
 * @author zhangzhijun
 */

public class MarkModel implements Serializable{


    private String name;
    private String number;
    private String competitorRank;
    private String competitorAmount;
    private String gradeJudge;
    private String eventName;
    private String eventOrganizer;
    private String pictureUrl;
    private boolean competitorSex;
    private String competitorType;
    private String countryOrRegion;
    private String kM5Qiang;
    private String kM5Jing;
    private String kM10Qiang;
    private String kM10Jing;
    private String kM137Qiang;
    private String kM137Jing;
    private String kM18Qiang;
    private String kM18Jing;
    private String kM20Qiang;
    private String kM20Jing;
    private String kM210975Qiang;
    private String kM210975Jing;
    private String kM215Qiang;
    private String kM215Jing;
    private String kM25Qiang;
    private String kM25Jing;
    private String kM29Qiang;
    private String kM29Jing;
    private String kM35Qiang;
    private String kM35Jing;
    private String kM376Qiang;
    private String kM376Jing;
    private String kM40Qiang;
    private String kM40Jing;
    private String kM42195Qiang;
    private String kM42195Jing;
    private String qiang;
    private String jing;

    public MarkModel(String name, String number, String competitorRank, String competitorAmount, String gradeJudge,String eventName,String eventOrganizer,String pictureUrl,boolean competitorSex, String competitorType, String countryOrRegion, String kM5Qiang, String kM5Jing, String kM10Qiang, String kM10Jing, String kM137Qiang, String kM137Jing, String kM18Qiang, String kM18Jing, String kM20Qiang, String kM20Jing, String kM210975Qiang, String kM210975Jing, String kM215Qiang, String kM215Jing, String kM25Qiang, String kM25Jing, String kM29Qiang, String kM29Jing, String kM35Qiang, String kM35Jing, String kM376Qiang, String kM376Jing, String kM40Qiang, String kM40Jing, String kM42195Qiang, String kM42195Jing,String qiang,String jing) {
        this.name = name;
        this.number = number;
        this.competitorRank = competitorRank;
        this.competitorAmount = competitorAmount;
        this.gradeJudge = gradeJudge;
        this.eventName = eventName;
        this.eventOrganizer=eventOrganizer;
        this.pictureUrl=pictureUrl;
        this.competitorSex = competitorSex;
        this.competitorType = competitorType;
        this.countryOrRegion = countryOrRegion;
        this.kM5Qiang = kM5Qiang;
        this.kM5Jing = kM5Jing;
        this.kM10Qiang = kM10Qiang;
        this.kM10Jing = kM10Jing;
        this.kM137Qiang = kM137Qiang;
        this.kM137Jing = kM137Jing;
        this.kM18Qiang = kM18Qiang;
        this.kM18Jing = kM18Jing;
        this.kM20Qiang = kM20Qiang;
        this.kM20Jing = kM20Jing;
        this.kM210975Qiang = kM210975Qiang;
        this.kM210975Jing = kM210975Jing;
        this.kM215Qiang = kM215Qiang;
        this.kM215Jing = kM215Jing;
        this.kM25Qiang = kM25Qiang;
        this.kM25Jing = kM25Jing;
        this.kM29Qiang = kM29Qiang;
        this.kM29Jing = kM29Jing;
        this.kM35Qiang = kM35Qiang;
        this.kM35Jing = kM35Jing;
        this.kM376Qiang = kM376Qiang;
        this.kM376Jing = kM376Jing;
        this.kM40Qiang = kM40Qiang;
        this.kM40Jing = kM40Jing;
        this.kM42195Qiang = kM42195Qiang;
        this.kM42195Jing = kM42195Jing;
        this.qiang=qiang;
        this.jing=jing;

    }


    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getEventOrganizer() {
        return eventOrganizer;
    }

    public String getCompetitorRank() {
        return competitorRank;
    }

    public String getCompetitorAmount() {
        return competitorAmount;
    }

    public String getGradeJudge() {
        return gradeJudge;
    }

    public String getEventName() {
        return eventName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public boolean getCompetitorSex() {
        return competitorSex;
    }

    public String getCompetitorType() {
        return competitorType;
    }

    public String getCountryOrRegion() {
        return countryOrRegion;
    }

    public String getkM5Qiang() {
        return kM5Qiang;
    }

    public String getkM5Jing() {
        return kM5Jing;
    }

    public String getkM10Qiang() {
        return kM10Qiang;
    }

    public String getkM10Jing() {
        return kM10Jing;
    }

    public String getkM137Jing() {
        return kM137Jing;
    }

    public String getkM137Qiang() {
        return kM137Qiang;
    }

    public String getkM18Qiang() {
        return kM18Qiang;
    }

    public String getkM18Jing() {
        return kM18Jing;
    }

    public String getkM20Qiang() {
        return kM20Qiang;
    }

    public String getkM20Jing() {
        return kM20Jing;
    }

    public String getkM210975Jing() {
        return kM210975Jing;
    }

    public String getkM210975Qiang() {
        return kM210975Qiang;
    }

    public String getkM215Qiang() {
        return kM215Qiang;
    }

    public String getkM215Jing() {
        return kM215Jing;
    }

    public String getkM42195Jing() {
        return kM42195Jing;
    }

    public String getkM42195Qiang() {
        return kM42195Qiang;
    }

    public String getkM40Jing() {
        return kM40Jing;
    }

    public String getkM40Qiang() {
        return kM40Qiang;
    }

    public String getkM376Jing() {
        return kM376Jing;
    }

    public String getkM376Qiang() {
        return kM376Qiang;
    }

    public String getkM35Jing() {
        return kM35Jing;
    }

    public String getkM35Qiang() {
        return kM35Qiang;
    }

    public String getkM29Jing() {
        return kM29Jing;
    }

    public String getkM29Qiang() {
        return kM29Qiang;
    }

    public String getkM25Jing() {
        return kM25Jing;
    }

    public String getkM25Qiang() {
        return kM25Qiang;
    }

    public String getQiang() {
        return qiang;
    }

    public void setQiang(String qiang) {
        this.qiang = qiang;
    }

    public String getJing() {
        return jing;
    }

    public void setJing(String jing) {
        this.jing = jing;
    }
}
