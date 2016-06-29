package com.greenapex.mightyhomeplanz.entities;

import com.google.gson.Gson;

/**
 * Created by admin on 20-Feb-16.by ${COMPUTERNAME}
 */
public class MileStoneModel {
    private String title;
    private String description;
    private String amount;
    private MileStoneStatusModel status;
    private String milestoneID = "";

    public MileStoneModel() {
        this.setTitle("");
        this.setDescription("");
        this.setAmount("");
        this.setStatus(new MileStoneStatusModel());
        this.setMilestoneID("");
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public MileStoneStatusModel getStatus() {
        return status;
    }

    public void setStatus(MileStoneStatusModel status) {
        this.status = status;
    }

    public String getMilestoneID() {
        return milestoneID;
    }

    public void setMilestoneID(String milestoneID) {
        this.milestoneID = milestoneID;
    }
}
