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
public class AddPaymentWebservice extends WebserviceBase {

    private final AddPaymentWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {


        try {
            String jobID = params.getString(Constants.JOBID);
            String userID = "&userID=" + params.getString(Constants.USERID);
            String status = "&status=" + params.getString(Constants.JOBSTATUS);
            String url = Constants.AddPaymentWebservice + jobID + userID + status;
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
        String url = Constants.AddPaymentWebservice;
        super.callService(url, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.addPaymentWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.addPaymentWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.addPaymentWebserviceFailedWithMessage(message);
    }

    public AddPaymentWebservice(AddPaymentWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface AddPaymentWebserviceHandler {
        void addPaymentWebserviceStart();

        void addPaymentWebserviceSucessful(String response, String message);

        void addPaymentWebserviceFailedWithMessage(String message);
    }
}
