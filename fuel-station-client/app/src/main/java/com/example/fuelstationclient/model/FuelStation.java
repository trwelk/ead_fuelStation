package com.example.fuelstationclient.model;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FuelStation implements Serializable  {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("contactNumber")
    private String contactNumber;

    @SerializedName("fuelStock")
    private List<Fuel> fuelStock;

    @SerializedName("usersInQueue")
    private List<UserQueue> usersInQueue;

    public FuelStation(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public FuelStation(String id, String name, String email, String latitude, String longitude, String contactNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contactNumber = contactNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public List<Fuel> getFuelStock() {
        return fuelStock;
    }

    public void setFuelStock(List<Fuel> fuelStock) {
        this.fuelStock = fuelStock;
    }

    public List<UserQueue> getUsersInQueue() {
        return usersInQueue;
    }

    public void setUsersInQueue(List<UserQueue> usersInQueue) {
        this.usersInQueue = usersInQueue;
    }


}
