package com.greenapex.response.models;

import com.google.gson.Gson;

/**
 * Created by Arpit Thakkar on 16/2/16.
 */
public class AddressResponse {

    private String streetAddress;
    private String city;
    private String state;
    private String zip;

    public AddressResponse() {
        this.streetAddress = "";
        this.city = "";
        this.state = "";
        this.zip = "";
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
