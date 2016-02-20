package com.greenapex.mightyhomeplanz.entities;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by admin on 20-Feb-16.by ${COMPUTERNAME}
 */
public class JobModel {
    private static final String KEY_ID = "id";
    private static final String KEY_JOBID = "jobID";
    private static final String KEY_JOBDESCRIPTION = "jobDescription";
    private static final String KEY_SAMEADDRESS = "sameAddress";
    private static final String KEY_URGENCYOFWORK = "urgencyOfWork";
    private static final String KEY_JOBCREATIONDATE = "jobCreationDate";
    private static final String KEY_OWNERID = "ownerID";
    private static final String KEY_JOBTITLE = "jobTitle";
    private String id;
    private String jobID;
    private String jobDescription;
    private String sameAddress;
    private String urgencyOfWork;
    private String jobCreationDate;
    private String ownerID;
    private String jobTitle;
    private AddressModel address;
    private JobStatusModel jobStatus;
    private ArrayList<String> images;

    //TODO: Images Model Pending will do after I recieve the response

    public JobModel() {
        this.setImages(new ArrayList<String>());
        setAddress(new AddressModel());
        setJobStatus(new JobStatusModel());
        this.id = "";
        this.jobID = "";
        this.jobDescription = "";
        this.sameAddress = "";
        this.urgencyOfWork = "";
        this.jobCreationDate = "";
        this.ownerID = "";
        this.jobTitle = "";
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

    public String getSameAddress() {
        return sameAddress;
    }

    public void setSameAddress(String sameAddress) {
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

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }




    public JobStatusModel getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatusModel jobStatus) {
        this.jobStatus = jobStatus;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
}
