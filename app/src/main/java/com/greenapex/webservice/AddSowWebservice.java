package com.greenapex.webservice;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.greenapex.Request.models.AddSowRequest;
import com.greenapex.Utils.Constants;
import com.greenapex.WebserviceBase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by admin on 15-Oct-15.
 */
public class AddSowWebservice extends WebserviceBase {

    private final AddSowWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {


        try {
            AddSowRequest addSowRequest = new GsonBuilder().create().fromJson(params.toString(), AddSowRequest.class);
            String jobId = addSowRequest.getJobId().toLowerCase();
            String role = "&role=" + addSowRequest.getRole().toLowerCase();
            String userid = "&userID=" + addSowRequest.getUserID().toLowerCase();
            String url = Constants.AddSowWebservice + jobId + role + userid;
            super.callService(url, params, Constants.METHOD_POST);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        String ownerId = params.getString("ownerID");
        super.callService(Constants.AddSowWebservice + ownerId, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.addSowWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.addSowWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.addSowWebserviceFailedWithMessage(message);
    }

    public AddSowWebservice(AddSowWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface AddSowWebserviceHandler {
        void addSowWebserviceStart();

        void addSowWebserviceSucessful(String response, String message);

        void addSowWebserviceFailedWithMessage(String message);
    }
}
