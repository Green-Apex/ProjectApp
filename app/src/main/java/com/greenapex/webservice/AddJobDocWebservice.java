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
public class AddJobDocWebservice extends WebserviceBase {

    private final AddJobDocWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {

        String ownerID = null;
        try {
            String userID = params.getString(Constants.USERID);
            String jobID = "&jobID=" + params.getString(Constants.JOBID);
            String url = Constants.AddJobDocWebservice + userID + jobID;
            params.remove(Constants.USERID);
            params.remove(Constants.JOBID);
            super.callService(url, params, Constants.METHOD_POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        String userID = params.getString(Constants.USERID);
        String jobID = "&jobID=" + params.getString(Constants.JOBID);
        String url = Constants.AddJobDocWebservice + userID + jobID;
        params.remove(Constants.USERID);
        params.remove(Constants.JOBID);
        super.callService(url, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.addJobDocWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.addJobDocWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.addJobDocWebserviceFailedWithMessage(message);
    }

    public AddJobDocWebservice(AddJobDocWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface AddJobDocWebserviceHandler {
        void addJobDocWebserviceStart();

        void addJobDocWebserviceSucessful(String response, String message);

        void addJobDocWebserviceFailedWithMessage(String message);
    }
}
