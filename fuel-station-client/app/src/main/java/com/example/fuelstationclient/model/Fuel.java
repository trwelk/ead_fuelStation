package com.example.fuelstationclient.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class Fuel implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("fuelType")
    private String fuelType;
    @SerializedName("available")
    private boolean available;
    @SerializedName("arrivalTime")
    private String arrivalTime;
    @SerializedName("finishTime")
    private String finishTime;

    @SerializedName("usersInQueue")
    private List<UserQueue> usersInQueue;

    public List<UserQueue> getUsersInQueue() {
        return usersInQueue;
    }

    public void setUsersInQueue(List<UserQueue> usersInQueue) {
        this.usersInQueue = usersInQueue;
    }

    public Fuel( String fuelType) {
        Random rand = new Random();
        this.id = "FUEL-" + rand.nextInt(9999999);
        this.fuelType = fuelType;
        this.available = true;
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

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }
}
