package com.greenapex.mightyhomeplanz.entities;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by admin on 20-Feb-16.by ${COMPUTERNAME}
 */
public class JobOwnerModel {
//    private static final String KEY_ID = "id";
//    private static final String KEY_JOBID = "jobID";
//    private static final String KEY_JOBDESCRIPTION = "jobDescription";
//    private static final String KEY_SAMEADDRESS = "sameAddress";
//    private static final String KEY_URGENCYOFWORK = "urgencyOfWork";
//    private static final String KEY_JOBCREATIONDATE = "jobCreationDate";
//    private static final String KEY_USERID = "userID";
//    private static final String KEY_JOBTITLE = "jobTitle";

    private String id;
    private String email;
    private String fname;
    private String lname;
    private String contactNo;
    private String userID;
    private String pwd;
    private AddressModel address;
    private String role;
    private String deviceID;
    private boolean emailVerified;
    private String profilePic;
    private String registrationTime;
    private String fbID;
    private String authToken;
    private ArrayList<String> comPref;
    private String updationTime;
    private String platform;


    //TODO: Images Model Pending will do after I recieve the response

    public JobOwnerModel() {
        this.setId("");
        this.setEmail("");
        this.setFname("");
        this.setLname("");
        this.setContactNo("");
        this.setUserID("");
        this.setPwd("");
        setAddress(new AddressModel());
        this.setRole("");
        this.setDeviceID("");
        this.setProfilePic("");
        this.setRegistrationTime("");
        this.setFbID("");
        this.setAuthToken("");
        this.setComPref(new ArrayList<String>());
        this.setUpdationTime("");
        this.setPlatform("");
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

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getFbID() {
        return fbID;
    }

    public void setFbID(String fbID) {
        this.fbID = fbID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public ArrayList<String> getComPref() {
        return comPref;
    }

    public void setComPref(ArrayList<String> comPref) {
        this.comPref = comPref;
    }

    public String getUpdationTime() {
        return updationTime;
    }

    public void setUpdationTime(String updationTime) {
        this.updationTime = updationTime;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
