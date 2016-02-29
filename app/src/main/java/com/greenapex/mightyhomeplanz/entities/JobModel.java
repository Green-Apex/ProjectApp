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
    private static final String KEY_USERID = "userID";
    private static final String KEY_JOBTITLE = "jobTitle";
    private String id;
    private String jobDescription;
    private boolean sameAddress;
    private String urgencyOfWork;
    private String jobCreationDate;
    private ArrayList<String> userID;
    private String jobID;
    private String jobTitle;
    private AddressModel address;
    private String jobStatus;
    private ArrayList<String> images;

    //TODO: Images Model Pending will do after I recieve the response

    public JobModel() {
        this.setImages(new ArrayList<String>());
        setAddress(new AddressModel());
        setJobStatus("");
        this.id = "";
        this.setJobID("");
        this.jobDescription = "";
        this.urgencyOfWork = "";
        this.jobCreationDate = "";
        this.setUserID(new ArrayList<String>());
        this.jobTitle = "";
        this.setJobID("");
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

    public boolean getSameAddress() {
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


    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }


    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
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

    public ArrayList<String> getUserID() {
        return userID;
    }

    public void setUserID(ArrayList<String> userID) {
        this.userID = userID;
    }
}
