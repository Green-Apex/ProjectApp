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
public class ChangeJobStatusWebservice extends WebserviceBase {

    private final ChangeJobStatusWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {


        try {
            String jobID = params.getString(Constants.JOBID);
            String userID = "&userID=" + params.getString(Constants.USERID);
            String status = "&status=" + params.getString(Constants.JOBSTATUS);
            String url = Constants.ChangeJobStatusWebservice + jobID + userID + status;
            params.remove(Constants.JOBID);
            params.remove(Constants.USERID);
            params.remove(Constants.JOBSTATUS);
            super.callService(url, params, Constants.METHOD_POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        String jobID = params.getString(Constants.JOBID);
        String userID = "&userID=" + params.getString(Constants.USERID);
        String status = "&status" + params.getString(Constants.JOBSTATUS);
        String url = Constants.ChangeJobStatusWebservice + jobID + userID + status;
        params.remove(Constants.JOBID);
        params.remove(Constants.USERID);
        params.remove(Constants.JOBSTATUS);
        super.callService(url, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.changeJobStatusWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.changeJobStatusWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.changeJobStatusWebserviceFailedWithMessage(message);
    }

    public ChangeJobStatusWebservice(ChangeJobStatusWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface ChangeJobStatusWebserviceHandler {
        void changeJobStatusWebserviceStart();

        void changeJobStatusWebserviceSucessful(String response, String message);

        void changeJobStatusWebserviceFailedWithMessage(String message);
    }
}
