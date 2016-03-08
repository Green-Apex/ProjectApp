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
public class UpdateUserWebservice extends WebserviceBase {

    private final UpdateUserWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {
        try {
            String userID = params.getString(Constants.USERID);
            String url = Constants.UpdateUserWebservice + userID;
            params.remove(Constants.USERID);
            super.callService(url, params, method_type);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        super.callService(Constants.UpdateUserWebservice, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.updateUserWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.updateUserWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.updateUserWebserviceFailedWithMessage(message);
    }

    public UpdateUserWebservice(UpdateUserWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface UpdateUserWebserviceHandler {
        void updateUserWebserviceStart();

        void updateUserWebserviceSucessful(String response, String message);

        void updateUserWebserviceFailedWithMessage(String message);
    }
}
