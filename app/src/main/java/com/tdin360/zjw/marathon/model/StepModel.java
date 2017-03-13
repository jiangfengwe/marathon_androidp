package com.tdin360.zjw.marathon.model;

import java.util.Date;

/**
 * 记步历史纪录
 * Created by admin on 17/3/12.
 */

public class StepModel {

    private int id;
    private int userId;
    private int totalStep;
    private double totalDistance;
    private double totalKcal;
    private String date;
    private String year;
    private String day;


    public StepModel(int userId, int totalStep, double totalDistance, double totalKcal, String date) {
        this.userId = userId;
        this.totalStep = totalStep;
        this.totalDistance = totalDistance;
        this.totalKcal = totalKcal;
        this.date = date;
    }

    public StepModel(int id, int userId, int totalStep, double totalDistance, double totalKcal, String date) {
        this.id = id;
        this.userId = userId;
        this.totalStep = totalStep;
        this.totalDistance = totalDistance;
        this.totalKcal = totalKcal;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTotalStep(int totalStep) {
        this.totalStep = totalStep;
    }

    public void setTotalKcal(double totalKcal) {
        this.totalKcal = totalKcal;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public int getTotalStep() {
        return totalStep;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public double getTotalKcal() {
        return totalKcal;
    }

    public String getDate() {
        return date;
    }


    public String getYear(){

        String[] params = date.split("-");

        return params[0];
    }
    public String getDay(){

        String[] params = date.split("-");

        return params[1]+"-"+params[2];
    }
    @Override
    public String toString() {
        return "id="+id+", totalStep="+totalStep+",totalDistance="+totalDistance+"totalKcal="+totalKcal+"date="+date;
    }
}
