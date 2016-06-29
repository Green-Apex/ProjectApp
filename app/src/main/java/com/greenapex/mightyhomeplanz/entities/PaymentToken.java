package com.greenapex.mightyhomeplanz.entities;

import com.google.gson.Gson;

/**
 * Created by admin on 28-Jun-16.by ${COMPUTERNAME}
 */
public class PaymentToken {
    private String id;

    private String created;

    private CreditCard card;

    private String object;

    private String used;

    private String type;

    private String client_ip;

    public PaymentToken() {
        this.setCard(new CreditCard());
        this.setClient_ip("");
        this.setCreated("");
        this.setId("");
        this.setObject("");
        this.setType("");
        this.setUsed("");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public CreditCard getCard() {
        return card;
    }

    public void setCard(CreditCard card) {
        this.card = card;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }
}
