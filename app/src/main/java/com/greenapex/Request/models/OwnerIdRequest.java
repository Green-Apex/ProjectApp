package com.greenapex.Request.models;

import com.google.gson.Gson;

/**
 * Created by Arpit Thakkar on 17/2/16.
 */
public class OwnerIdRequest {

    private String userID;


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
