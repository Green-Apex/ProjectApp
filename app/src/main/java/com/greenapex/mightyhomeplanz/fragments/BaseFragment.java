package com.greenapex.mightyhomeplanz.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greenapex.Utils.Constants;
import com.greenapex.Utils.Utils;
import com.greenapex.response.models.UserResponse;

/**
 * Created by admin on 20-Feb-16.by ${COMPUTERNAME}
 */
public class BaseFragment extends Fragment {

    public Utils utils;
    private SharedPreferences sharedpreferences;
    private Gson gson;
    private UserResponse userGson;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        gson = new GsonBuilder().create();
        sharedpreferences = getActivity().getSharedPreferences(Constants.mightyHomePlanz, Context.MODE_PRIVATE);
        userGson = gson.fromJson(getUserPreference(), UserResponse.class);
        utils = new Utils(getActivity());
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
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
