/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenapex.response.models;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * @author mitulnakum
 */
public class CommonResponse {

    public static final String key_clientCode = "clientCode";
    public static final String key_message = "message";
    public static final String key_data = "data";

    private boolean result;
    private String message;
    private JsonElement data;

    /**
     * @return the data
     */
    public JsonElement getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(JsonElement data) {
        this.data = data;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the result
     */
    public boolean isResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
