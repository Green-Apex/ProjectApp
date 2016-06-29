package com.greenapex.mightyhomeplanz.entities;

import com.google.gson.Gson;

/**
 * Created by admin on 28-Jun-16.by ${COMPUTERNAME}
 */
public class Date {
    private String chronology;

    private String millis;

    public Date() {
        setChronology("");
        setMillis(String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }

    public String getChronology() {
        return chronology;
    }

    public void setChronology(String chronology) {
        this.chronology = chronology;
    }

    public String getMillis() {
        return millis;
    }

    public void setMillis(String millis) {
        this.millis = millis;
    }


}