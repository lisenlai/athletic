package com.laidage.ican.DataBase;

import org.litepal.crud.DataSupport;

import java.util.Date;

public class History extends DataSupport{
    private String type;
    private float distance;
    private float speed;
    private String startTime;
    private String allTime;
    private int  kilocalorie;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public float getDistance() {
        return distance;
    }
    public void setDistance(float distance) {
        this.distance = distance;
    }
    public float getSpeed() {
        return speed;
    }
    public void setSpeed(float speed) {
        this.speed = speed;
    }
     public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getAllTime() {
        return allTime;
    }
    public void setAllTime(String allTime) {
        this.allTime=allTime;
    }
    public int getKilocalorie() {
        return  kilocalorie;
    }
    public void setKilocalorie(int kilocalorie) {
        this.kilocalorie=kilocalorie;
    }

}
