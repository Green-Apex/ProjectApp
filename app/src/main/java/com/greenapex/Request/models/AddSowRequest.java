package com.greenapex.Request.models;

import com.google.gson.Gson;
import com.greenapex.mightyhomeplanz.entities.MileStoneModel;

import java.util.ArrayList;

/**
 * Created by admin on 21-Feb-16.by ${COMPUTERNAME}
 */
public class AddSowRequest {
    private ArrayList<MileStoneModel> milestones;
    private JobDOCModel jobDOC;
    private String jobId;
    private String role;
    private String userID;
    private String totalJobCosting;

    public AddSowRequest() {
        this.setMilestones(new ArrayList<MileStoneModel>());
        this.setJobDOC(new JobDOCModel());
        this.jobId = "";
        this.role = "";
        this.userID = "";
        this.setTotalJobCosting("");
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }


    public ArrayList<MileStoneModel> getMilestones() {
        return milestones;
    }

    public void setMilestones(ArrayList<MileStoneModel> milestones) {
        this.milestones = milestones;
    }

    public JobDOCModel getJobDOC() {
        return jobDOC;
    }

    public void setJobDOC(JobDOCModel jobDOC) {
        this.jobDOC = jobDOC;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTotalJobCosting() {
        return totalJobCosting;
    }

    public void setTotalJobCosting(String totalJobCosting) {
        this.totalJobCosting = totalJobCosting;
    }
}
