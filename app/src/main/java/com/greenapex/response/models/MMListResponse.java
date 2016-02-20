package com.greenapex.response.models;

import com.google.gson.Gson;

/**
 * Created by admin on 20-Feb-16.by ${COMPUTERNAME}
 */
public class MMListResponse {
    private String id;
    private String email;
    private String fname;
    private String lname;
    private String contactNo;
    private String pwd;
    private String role;
    private String authToken;
    private String emailVerified;
    private String registrationTime;
    private String mmID;

    public MMListResponse() {
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
        this.setMmID("");
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getMmID() {
        return mmID;
    }

    public void setMmID(String mmID) {
        this.mmID = mmID;
    }
}
