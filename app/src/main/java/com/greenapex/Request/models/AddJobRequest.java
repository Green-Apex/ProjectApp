package com.greenapex.Request.models;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Arpit Thakkar on 17/2/16.
 */
public class AddJobRequest {

    private String jobTitle;
    private String jobDescription;
    private ArrayList<String> images;
    private String urgencyOfWork;
    private JobAddressRequest address;
    private String ownerID = "";

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getUrgencyOfWork() {
        return urgencyOfWork;
    }

    public void setUrgencyOfWork(String urgencyOfWork) {
        this.urgencyOfWork = urgencyOfWork;
    }

    public JobAddressRequest getAddress() {
        return address;
    }

    public void setAddress(JobAddressRequest address) {
        this.address = address;
    }


    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }
}
