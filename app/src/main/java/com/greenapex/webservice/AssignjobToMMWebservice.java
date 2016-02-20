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
public class AssignjobToMMWebservice extends WebserviceBase {

    private final AssignjobToMMWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {


        try {
            String mmID = params.getString(Constants.MMID);
            String role = "&role=" + params.getString("role");
            String jobID = "&jobID=" + params.getString("jobID");
            String pmID = "&pmID=" + params.getString("pmID");
            String url = Constants.AssignjobToMMWebservice + mmID + role + jobID + pmID;
            super.callService(url, params, Constants.METHOD_POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        String ownerId = params.getString("ownerID");
        super.callService(Constants.AssignjobToMMWebservice + ownerId, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.assignjobToMMWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.assignjobToMMWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.assignjobToMMWebserviceFailedWithMessage(message);
    }

    public AssignjobToMMWebservice(AssignjobToMMWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface AssignjobToMMWebserviceHandler {
        void assignjobToMMWebserviceStart();

        void assignjobToMMWebserviceSucessful(String response, String message);

        void assignjobToMMWebserviceFailedWithMessage(String message);
    }
}
