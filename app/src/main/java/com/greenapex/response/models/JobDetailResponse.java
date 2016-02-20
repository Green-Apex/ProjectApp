package com.greenapex.response.models;

import com.google.gson.Gson;
import com.greenapex.mightyhomeplanz.entities.AddressModel;
import com.greenapex.mightyhomeplanz.entities.JobStatusModel;

import java.util.ArrayList;

/**
 * Created by admin on 20-Feb-16.by ${COMPUTERNAME}
 */
public class JobDetailResponse {
    private String id;
    private String jobID;
    private String jobDescription;
    private AddressModel address;
    private boolean sameAddress;
    private String urgencyOfWork;
    private String jobCreationDate;
    private String ownerID;
    private JobStatusModel jobStatus;
    private String jobTitle;
    private ArrayList<String> images;

    public JobDetailResponse() {
        this.id = "";
        this.jobID = "";
        this.jobDescription = "";
        this.setAddress(new AddressModel());
        this.urgencyOfWork = "";
        this.jobCreationDate = "";
        this.ownerID = "";
        this.setJobStatus(new JobStatusModel());
        this.jobTitle = "";
        this.setImages(new ArrayList<String>());
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

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public boolean isSameAddress() {
        return sameAddress;
    }

    public void setSameAddress(boolean sameAddress) {
        this.sameAddress = sameAddress;
    }

    public String getUrgencyOfWork() {
        return urgencyOfWork;
    }

    public void setUrgencyOfWork(String urgencyOfWork) {
        this.urgencyOfWork = urgencyOfWork;
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

    public JobStatusModel getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatusModel jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
}
