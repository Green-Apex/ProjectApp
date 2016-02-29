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
public class GetNewJobWebservice extends WebserviceBase {

    private final GetNewJobWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {

        String userID = null;
        try {
            userID = params.getString("userID");
            String url = Constants.GetNewJobWebservice + userID;
            super.callService(url, params, Constants.METHOD_GET);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        super.callService(Constants.GetNewJobWebservice, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.GetNewJobWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.GetNewJobWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.GetNewJobWebserviceFailedWithMessage(message);
    }

    public GetNewJobWebservice(GetNewJobWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface GetNewJobWebserviceHandler {
        void GetNewJobWebserviceStart();

        void GetNewJobWebserviceSucessful(String response, String message);

        void GetNewJobWebserviceFailedWithMessage(String message);
    }
}
