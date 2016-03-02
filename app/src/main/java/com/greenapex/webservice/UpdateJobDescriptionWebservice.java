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
public class UpdateJobDescriptionWebservice extends WebserviceBase {

    private final UpdateJobDescriptionWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {


        try {
            String jobID = params.getString(Constants.JOBID);
            String userID = "&userID=" + params.getString(Constants.USERID);
            String url = Constants.UpdateJobDescriptionWebservice + jobID + userID;
            params.remove(Constants.JOBID);
            params.remove(Constants.USERID);
            super.callService(url, params, Constants.METHOD_POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        String ownerId = params.getString("ownerID");
        super.callService(Constants.UpdateJobDescriptionWebservice + ownerId, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.updateJobDescriptionWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.updateJobDescriptionWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.updateJobDescriptionWebserviceFailedWithMessage(message);
    }

    public UpdateJobDescriptionWebservice(UpdateJobDescriptionWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface UpdateJobDescriptionWebserviceHandler {
        void updateJobDescriptionWebserviceStart();

        void updateJobDescriptionWebserviceSucessful(String response, String message);

        void updateJobDescriptionWebserviceFailedWithMessage(String message);
    }
}
