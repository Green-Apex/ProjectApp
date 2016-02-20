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
public class GetJobDetailByJobIdWebservice extends WebserviceBase {

    private final GetJobDetailByJobIdWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {

        String jobID = null;
        try {
            jobID = params.getString("jobID");
            String url = Constants.GetJobDetailByJobIdWebservice + jobID;
            super.callService(url, params, Constants.METHOD_GET);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        super.callService(Constants.GetJobDetailByJobIdWebservice, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.getJobDetailByJobIdWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.getJobDetailByJobIdWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.getJobDetailByJobIdWebserviceFailedWithMessage(message);
    }

    public GetJobDetailByJobIdWebservice(GetJobDetailByJobIdWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface GetJobDetailByJobIdWebserviceHandler {
        void getJobDetailByJobIdWebserviceStart();

        void getJobDetailByJobIdWebserviceSucessful(String response, String message);

        void getJobDetailByJobIdWebserviceFailedWithMessage(String message);
    }
}
