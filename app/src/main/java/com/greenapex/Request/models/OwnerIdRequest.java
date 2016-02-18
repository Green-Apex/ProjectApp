package com.greenapex.Request.models;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Arpit Thakkar on 17/2/16.
 */
public class OwnerIdRequest {

    private String ownerID;


    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }


    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
