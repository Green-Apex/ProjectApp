/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenapex.response.models;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * @author Arpit
 */
public class CommonResponse {

    public static final String key_clientCode = "clientCode";
    public static final String key_message = "message";
    public static final String key_data = "data";

    public static final String case_Success = "0";
    public static final String case_Wrong_Credentials = "1";
    public static final String case_Login_Success = "2";
    public static final String case_MandatoryField = "5003";
    public static final String case_notFound = "10";


    //    private boolean result;
    private String message;
    private String clientCode;
    //    private String data;
    private JsonElement data;

    public CommonResponse() {
        this.message = "";
        this.clientCode = "";

    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

//    public boolean isResult() {
//        return result;
//    }
//
//    public void setResult(boolean result) {
//        this.result = result;
//    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
