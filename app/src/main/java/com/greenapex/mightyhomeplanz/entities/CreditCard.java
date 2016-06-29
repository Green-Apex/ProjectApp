package com.greenapex.mightyhomeplanz.entities;

import com.google.gson.Gson;

/**
 * Created by admin on 28-Jun-16.by ${COMPUTERNAME}
 */
public class CreditCard {
    private String id;

    private String exp_year;

    private String exp_month;

    private String funding;

    private String name;

    private String cvc_check;

    private String brand;

    private String object;

    private String tokenization_method;

    private String last4;

    private String dynamic_last4;

    private String country;

    public CreditCard() {
        this.setObject("");
        this.setId("");
        this.setBrand("");
        this.setCountry("");
        this.setCvc_check("");
        this.setDynamic_last4("");
        this.setExp_month("");
        this.setExp_month("");
        this.setExp_year("");
        this.setFunding("");
        this.setId("");
        this.setLast4("");
        this.setName("");
        this.setTokenization_method("");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExp_year() {
        return exp_year;
    }

    public void setExp_year(String exp_year) {
        this.exp_year = exp_year;
    }

    public String getExp_month() {
        return exp_month;
    }

    public void setExp_month(String exp_month) {
        this.exp_month = exp_month;
    }

    public String getFunding() {
        return funding;
    }

    public void setFunding(String funding) {
        this.funding = funding;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCvc_check() {
        return cvc_check;
    }

    public void setCvc_check(String cvc_check) {
        this.cvc_check = cvc_check;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getTokenization_method() {
        return tokenization_method;
    }

    public void setTokenization_method(String tokenization_method) {
        this.tokenization_method = tokenization_method;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public String getDynamic_last4() {
        return dynamic_last4;
    }

    public void setDynamic_last4(String dynamic_last4) {
        this.dynamic_last4 = dynamic_last4;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }
}
