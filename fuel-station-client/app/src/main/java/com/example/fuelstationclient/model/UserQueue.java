package com.example.fuelstationclient.model;

import com.example.fuelstationclient.util.DateUtil;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class UserQueue implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("userId")
    private String userId;

    @SerializedName("timeArrived")
    private String timeArrived;

    @SerializedName("timeLeft")
    private String timeLeft;

    public UserQueue(String userId) {
        Random rand = new Random();
        this.id = "ID-" + rand.nextInt(9999999);
        this.userId = userId;
        this.timeArrived = DateUtil.getCurrentTimeStamp();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimeArrived() {
        return timeArrived;
    }

    public void setTimeArrived(String timeArrived) {
        this.timeArrived = timeArrived;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }
}
