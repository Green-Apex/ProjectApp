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
public class ChangeMileStoneStatusWebservice extends WebserviceBase {

    private final ChangeMileStoneStatusWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {


        try {
            String jobID = params.getString(Constants.JOBID);
            String userID = "&userID=" + params.getString(Constants.USERID);
            String milestoneID = "&milestoneID=" + params.getString(Constants.MILESTONEID);
            String status = "&completed=" + params.getString(Constants.MILESTONE_STATUS);
            String url = Constants.ChangeMileStoneStatusWebservice + jobID + userID + milestoneID;
            params.remove(Constants.JOBID);
            params.remove(Constants.USERID);
            params.remove(Constants.MILESTONEID);
            params.remove(Constants.MILESTONE_STATUS);
            super.callService(url, params, Constants.METHOD_POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        String jobID = params.getString(Constants.JOBID);
        String userID = "&userID=" + params.getString(Constants.USERID);
        String milestoneID = "&milestoneID=" + params.getString(Constants.MILESTONEID);
        String status = "&completed=" + params.getString(Constants.MILESTONE_STATUS);
        String url = Constants.ChangeMileStoneStatusWebservice + jobID + userID + milestoneID + status;
        params.remove(Constants.JOBID);
        params.remove(Constants.USERID);
        params.remove(Constants.MILESTONEID);
        params.remove(Constants.MILESTONE_STATUS);
        super.callService(url, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.changeMileStoneStatusWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.changeMileStoneStatusWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.changeMileStoneStatusWebserviceFailedWithMessage(message);
    }

    public ChangeMileStoneStatusWebservice(ChangeMileStoneStatusWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface ChangeMileStoneStatusWebserviceHandler {
        void changeMileStoneStatusWebserviceStart();

        void changeMileStoneStatusWebserviceSucessful(String response, String message);

        void changeMileStoneStatusWebserviceFailedWithMessage(String message);
    }
}
