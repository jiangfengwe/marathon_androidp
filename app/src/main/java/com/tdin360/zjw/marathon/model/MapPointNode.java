package com.tdin360.zjw.marathon.model;

/**
 *
 * 参赛路线经过点(经纬度)
 * Created by admin on 17/3/2.
 */

public class MapPointNode {

    /**
     * 经度
     */
    private double Longitude;

/**
 *  纬度
 */
    private double Latitude;


    public MapPointNode(double longitude, double latitude) {
        Longitude = longitude;
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public double getLatitude() {
        return Latitude;
    }
}
