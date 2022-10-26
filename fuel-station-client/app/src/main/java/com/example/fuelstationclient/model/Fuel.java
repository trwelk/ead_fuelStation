package com.example.fuelstationclient.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Fuel implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("fuelType")
    private String fuelType;
    @SerializedName("available")
    private boolean available;
    @SerializedName("arrivalTime")
    private Date arrivalTime;
    @SerializedName("finishTime")
    private Date finishTime;

    public Fuel(String id, String fuelType, boolean available, Date arrivalTime, Date finishTime) {
        this.id = id;
        this.fuelType = fuelType;
        this.available = available;
        this.arrivalTime = arrivalTime;
        this.finishTime = finishTime;
    }

    public Fuel(String id, String fuelType, boolean available) {
        this.id = id;
        this.fuelType = fuelType;
        this.available = available;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}
