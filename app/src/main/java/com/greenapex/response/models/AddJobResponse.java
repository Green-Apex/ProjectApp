package com.greenapex.response.models;

import com.google.gson.Gson;
import com.greenapex.Request.models.JobAddressRequest;

import java.util.ArrayList;

/**
 * Created by Arpit Thakkar on 17/2/16.
 */
public class AddJobResponse {


    private String jobTitle;
    private String jobDescription;
    private ArrayList<String> images;
    private String urgencyOfWork;
    private JobAddressResponse address;

    /*
    other fields
     */
    private String id;
    private String jobID;
    private boolean sameAddress;
    private String jobCreationDate;
    private String ownerID;
    private JobStatusResponse jobStatus;


    public AddJobResponse() {
        this.jobTitle = "";
        this.jobDescription = "";
        this.urgencyOfWork = "";
        this.images = new ArrayList<>();

    }

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

    public JobAddressResponse getAddress() {
        return address;
    }

    public void setAddress(JobAddressResponse address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public boolean isSameAddress() {
        return sameAddress;
    }

    public void setSameAddress(boolean sameAddress) {
        this.sameAddress = sameAddress;
    }

    public String getJobCreationDate() {
        return jobCreationDate;
    }

    public void setJobCreationDate(String jobCreationDate) {
        this.jobCreationDate = jobCreationDate;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public JobStatusResponse getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatusResponse jobStatus) {
        this.jobStatus = jobStatus;
    }


    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
