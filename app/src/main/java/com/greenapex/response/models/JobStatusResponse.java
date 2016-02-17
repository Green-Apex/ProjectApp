package com.greenapex.response.models;

import com.google.gson.Gson;

/**
 * Created by Arpit Thakkar on 17/2/16.
 */
public class JobStatusResponse {

    private boolean isNew;
    private String njDate;
    private boolean isAssigned;
    private boolean underEstimation;
    private boolean inProgress;
    private boolean completed;
    private boolean rejected;
    private boolean requestedForPayment;

    public JobStatusResponse() {
        this.njDate = "";
    }

    public boolean isNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public String getNjDate() {
        return njDate;
    }

    public void setNjDate(String njDate) {
        this.njDate = njDate;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(boolean isAssigned) {
        this.isAssigned = isAssigned;
    }

    public boolean isUnderEstimation() {
        return underEstimation;
    }

    public void setUnderEstimation(boolean underEstimation) {
        this.underEstimation = underEstimation;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public boolean isRequestedForPayment() {
        return requestedForPayment;
    }

    public void setRequestedForPayment(boolean requestedForPayment) {
        this.requestedForPayment = requestedForPayment;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
