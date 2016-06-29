package com.greenapex.response.models;

import com.google.gson.Gson;

/**
 * Created by admin on 12-Mar-16.by ${COMPUTERNAME}
 */
public class ChatResponse {
    private String id;

    private String userID;

    private String jobID;

    private String role;

    private String datetimestamp;

    private String activityType;
    private String desc;
    private String userName;
    private String profilePic;

    public ChatResponse() {
        this.id = "";
        this.userID = "";
        this.jobID = "";
        this.role = "";
        this.datetimestamp = "";
        this.activityType = "";
        this.setDesc("");
        this.setUserName("");
        this.setProfilePic("");
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDatetimestamp() {
        return datetimestamp;
    }

    public void setDatetimestamp(String datetimestamp) {
        this.datetimestamp = datetimestamp;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}