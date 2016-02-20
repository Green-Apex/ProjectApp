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
public class GetCompletedJobWebservice extends WebserviceBase {

    private final GetCompletedJobWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {

        String ownerID = null;
        try {
            ownerID = params.getString("ownerID");
            String url = Constants.GetCompletedJobWebservice + ownerID;
            super.callService(url, params, Constants.METHOD_GET);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        super.callService(Constants.GetCompletedJobWebservice, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.GetCompletedJobWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.GetCompletedJobWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.GetCompletedJobWebserviceFailedWithMessage(message);
    }

    public GetCompletedJobWebservice(GetCompletedJobWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface GetCompletedJobWebserviceHandler {
        void GetCompletedJobWebserviceStart();

        void GetCompletedJobWebserviceSucessful(String response, String message);

        void GetCompletedJobWebserviceFailedWithMessage(String message);
    }
}
