package com.greenapex.Request.models;

import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by Arpit Thakkar on 17/2/16.
 */
public class JobAddressRequest {


    private String addressID;
    private String streetAddress;
    private long pincode;
    private String city;
    private String state;
    private String landMark;
    private boolean sameAddress;


    public String getAddressID() {
        return addressID;
    }

    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public long getPincode() {
        return pincode;
    }

    public void setPincode(long pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }


    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public boolean isSameAddress() {
        return sameAddress;
    }

    public void setSameAddress(boolean sameAddress) {
        this.sameAddress = sameAddress;
    }
}

