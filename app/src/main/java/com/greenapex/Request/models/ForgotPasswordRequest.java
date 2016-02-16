package com.greenapex.Request.models;

import com.google.gson.Gson;

/**
 * Created by rultech on 15/2/16.
 */
public class ForgotPasswordRequest {

    private String email;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
