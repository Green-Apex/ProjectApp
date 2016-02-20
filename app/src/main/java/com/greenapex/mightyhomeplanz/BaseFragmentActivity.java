package com.greenapex.mightyhomeplanz;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greenapex.Utils.Constants;
import com.greenapex.Utils.Utils;
import com.greenapex.response.models.UserResponse;

/**
 * Created by admin on 20-Feb-16.by ${COMPUTERNAME}
 */
public class BaseFragmentActivity extends FragmentActivity {

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
    protected UserResponse getUserGson() {
        return userGson;
    }
    protected Gson getGson() {
        return gson;
    }
}
