package com.greenapex.mightyhomeplanz.entities;

public interface PaymentForm {
    public String getCardNumber();
    public String getCvc();
    public Integer getExpMonth();
    public Integer getExpYear();
    public String getCurrency();
}
