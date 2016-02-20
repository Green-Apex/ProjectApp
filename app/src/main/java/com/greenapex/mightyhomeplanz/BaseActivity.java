package com.greenapex.mightyhomeplanz;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greenapex.Utils.Constants;
import com.greenapex.Utils.Utils;
import com.greenapex.response.models.UserResponse;

/**
 * Created by admin on 21-Feb-16.by ${COMPUTERNAME}
 */
public class BaseActivity extends Activity {
    private Gson gson;
    private SharedPreferences sharedpreferences;
    private UserResponse userGson;
    private Utils utils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new GsonBuilder().create();
        sharedpreferences = getSharedPreferences(Constants.mightyHomePlanz, Context.MODE_PRIVATE);
        userGson = gson.fromJson(getUserPreference(), UserResponse.class);
        utils = new Utils(this);
    }


    public void showLog(String msg) {
        Utils.showLog(getClass().getSimpleName(), msg);
    }

    public void showToast(String msg) {
        if (msg.length() > 0)
            utils.showToast(msg);
    }

    protected String getUserPreference() {
        return sharedpreferences.getString(Constants.UserData, "");

    }

    protected boolean setUserPreference(String data) {
        boolean isSuccess = false;


        if (data.equalsIgnoreCase("")) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Constants.UserData, data);
            editor.commit();
            isSuccess = true;
            userGson = gson.fromJson(getUserPreference(), UserResponse.class);
        } else {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Constants.UserData, data);
            editor.commit();
            isSuccess = true;
            userGson = gson.fromJson(getUserPreference(), UserResponse.class);
        }


        return isSuccess;


    }

    protected UserResponse getUserGson() {
        return userGson;
    }

    protected Gson getGson() {
        return gson;
    }
}
