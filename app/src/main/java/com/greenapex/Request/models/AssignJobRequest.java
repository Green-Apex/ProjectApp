package com.greenapex.Request.models;

import com.google.gson.Gson;

/**
 * Created by admin on 20-Feb-16.by ${COMPUTERNAME}
 */
public class AssignJobRequest {
    private String role;
    private String mmID;
    private String jobID;
    private String pmID;

    public AssignJobRequest() {
        this.setRole("");
        this.setMmID("");
        this.setJobID("");
        this.setPmID("");
    }
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMmID() {
        return mmID;
    }

    public void setMmID(String mmID) {
        this.mmID = mmID;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getPmID() {
        return pmID;
    }

    public void setPmID(String pmID) {
        this.pmID = pmID;
    }
}
