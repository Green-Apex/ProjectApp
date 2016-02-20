package com.greenapex.Request.models;

import com.google.gson.Gson;

/**
 * Created by admin on 21-Feb-16.by ${COMPUTERNAME}
 */
public class JobDOCModel {
    private String docID;
    private String docTitle;
    private String uploadedUserID;
    private String datetimestamp;
    private String docPath;

    public JobDOCModel() {
        setDocID("");
        setDocTitle("");
        setUploadedUserID("");
        setUploadedUserID("");
        setDatetimestamp("");
        setDocPath("");
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }



    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getUploadedUserID() {
        return uploadedUserID;
    }

    public void setUploadedUserID(String uploadedUserID) {
        this.uploadedUserID = uploadedUserID;
    }

    public String getDatetimestamp() {
        return datetimestamp;
    }

    public void setDatetimestamp(String datetimestamp) {
        this.datetimestamp = datetimestamp;
    }

    public String getDocPath() {
        return docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }
}
