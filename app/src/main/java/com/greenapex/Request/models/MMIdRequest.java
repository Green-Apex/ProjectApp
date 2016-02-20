package com.greenapex.Request.models;

import com.google.gson.Gson;

/**
 * Created by Arpit Thakkar on 17/2/16.
 */
public class MMIdRequest {

    private String mmID;


    public String getMmID() {
        return mmID;
    }

    public void setMmID(String mmID) {
        this.mmID = mmID;
    }


    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
