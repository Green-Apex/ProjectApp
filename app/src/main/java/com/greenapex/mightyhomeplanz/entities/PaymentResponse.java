package com.greenapex.mightyhomeplanz.entities;

import com.google.gson.Gson;

/**
 * Created by admin on 28-Jun-16.by ${COMPUTERNAME}
 */
public class PaymentResponse {
    private String message;

    private String id;

    private String amount;

    private String userID;

    private PaymentToken token;

    private String jobID;

    private String transctionID;

    private String paymentGateway;

//    private Date date;

    private String milestoneID;

    public PaymentResponse() {
        setAmount("");
//        setDate(new Date());
        setId("");
        setJobID("");
        setMessage("");
        setMilestoneID("");
        setPaymentGateway("STRIPE");
        setTransctionID("");
        setUserID("");
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public PaymentToken getToken() {
        return token;
    }

    public void setToken(PaymentToken token) {
        this.token = token;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getTransctionID() {
        return transctionID;
    }

    public void setTransctionID(String transctionID) {
        this.transctionID = transctionID;
    }

    public String getPaymentGateway() {
        return paymentGateway;
    }

    public void setPaymentGateway(String paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }

    public String getMilestoneID() {
        return milestoneID;
    }

    public void setMilestoneID(String milestoneID) {
        this.milestoneID = milestoneID;
    }


}