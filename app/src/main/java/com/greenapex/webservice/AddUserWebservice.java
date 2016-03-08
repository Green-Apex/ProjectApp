package com.greenapex.webservice;

import android.content.Context;
import android.support.annotation.NonNull;

import com.greenapex.Utils.Constants;
import com.greenapex.WebserviceBase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by admin on 15-Oct-15.
 */
public class AddUserWebservice extends WebserviceBase {

    private final AddUserWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {
        try {
            String userID = params.getString(Constants.USERID);
            String url = Constants.AddUserWebservice + userID;
            params.remove(Constants.USERID);
            super.callService(url, params, method_type);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        super.callService(Constants.AddUserWebservice, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.addUserWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.addUserWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.addUserWebserviceFailedWithMessage(message);
    }

    public AddUserWebservice(AddUserWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface AddUserWebserviceHandler {
        void addUserWebserviceStart();

        void addUserWebserviceSucessful(String response, String message);

        void addUserWebserviceFailedWithMessage(String message);
    }
}
