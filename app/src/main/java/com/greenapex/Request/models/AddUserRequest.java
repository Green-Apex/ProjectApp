package com.greenapex.Request.models;

import com.google.gson.Gson;
import com.greenapex.mightyhomeplanz.entities.AddressModel;

import java.util.ArrayList;

/**
 * Created by admin on 07-Mar-16.by ${COMPUTERNAME}
 */
public class AddUserRequest {
    private String id;
    private String email;
    private String fname;
    private String lname;
    private String contactNo;
    private String pwd;
    private AddressModel address;
    private String role;
    private ArrayList<String> deviceID;
    private String authToken;
    private String emailVerified;
    private String registrationTime;
    private String updationTime;
    private ArrayList<String> comPref;
    private String userID;

    public AddUserRequest() {
        this.setId("");
        this.setEmail("");
        this.setFname("");
        this.setLname("");
        this.setContactNo("");
        this.setPwd("");
        this.setRole("");
        this.setAuthToken("");
        this.setEmailVerified("");
        this.setRegistrationTime("");
        this.setUserID("");
        this.setAddress(new AddressModel());
        this.setDeviceID(new ArrayList<String>());
        this.setComPref(new ArrayList<String>());

    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ArrayList<String> getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(ArrayList<String> deviceID) {
        this.deviceID = deviceID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getUpdationTime() {
        return updationTime;
    }

    public void setUpdationTime(String updationTime) {
        this.updationTime = updationTime;
    }

    public ArrayList<String> getComPref() {
        return comPref;
    }

    public void setComPref(ArrayList<String> comPref) {
        this.comPref = comPref;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
