package com.greenapex.Request.models;

import com.google.gson.Gson;

/**
 * Created by admin on 01-Jun-16.by ${COMPUTERNAME}
 */
public class SignInFbRequest {
    private String platform;

    private String fbID;

    private String lname;

    private String registrationTime;

    private String deviceID;

    private String authToken;

    private String updationTime;

    private String emailVerified;

    private String id;

    private String userID;

    private String pwd;

    private AddressRequest address;

    private String email;

    private String profilePic;

    private String role;

    private String contactNo;

    private String[] comPref;

    private String fname;

    public SignInFbRequest() {
        setAddress(new AddressRequest());
        setAuthToken("");
        setComPref(new String[]{""});
        setContactNo("");
        setDeviceID("");
        setEmail("");
        setEmailVerified("true");
        setFbID("");
        setFname("");
        setId("");
        setLname("");
        setPlatform("");
        setProfilePic("");
        setPwd("");
        setRegistrationTime("");
        setRole("");
        setUpdationTime("");
        setUserID("");
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getFbID() {
        return fbID;
    }

    public void setFbID(String fbID) {
        this.fbID = fbID;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUpdationTime() {
        return updationTime;
    }

    public void setUpdationTime(String updationTime) {
        this.updationTime = updationTime;
    }

    public String getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public AddressRequest getAddress() {
        return address;
    }

    public void setAddress(AddressRequest address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String[] getComPref() {
        return comPref;
    }

    public void setComPref(String[] comPref) {
        this.comPref = comPref;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}