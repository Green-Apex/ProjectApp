package com.greenapex.mightyhomeplanz.entities;

import com.google.gson.Gson;

/**
 * Created by admin on 20-Feb-16.by ${COMPUTERNAME}
 */
public class AddressModel {
    public static final String KEY_STREETADDRESS = "streetAddress";
    public static final String KEY_CITY = "city";
    public static final String KEY_STATE = "state";


    private String streetAddress;
    private String city;
    private String state;

    public AddressModel() {
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
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

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }
}
