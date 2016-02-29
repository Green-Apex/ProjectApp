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
public class AddJobWebservice extends WebserviceBase {

    private final AddJobWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {

        String ownerID = null;
        try {
            ownerID = params.getString("ownerID");
            String url = Constants.AddJobWebservice + ownerID;
            super.callService(url, params, Constants.METHOD_POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        String userID = params.getString("userID");
        params.remove("userID");
        super.callService(Constants.AddJobWebservice + userID, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.AddJobWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.AddJobWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.AddJobWebserviceFailedWithMessage(message);
    }

    public AddJobWebservice(AddJobWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface AddJobWebserviceHandler {
        void AddJobWebserviceStart();

        void AddJobWebserviceSucessful(String response, String message);

        void AddJobWebserviceFailedWithMessage(String message);
    }
}
