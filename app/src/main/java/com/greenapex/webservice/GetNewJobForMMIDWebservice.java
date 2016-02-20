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
public class GetNewJobForMMIDWebservice extends WebserviceBase {

    private final GetNewJobForMMIDWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {

        String mmID = null;
        try {
            mmID = params.getString("mmID");
            String url = Constants.GetNewJobForMMIDWebservice + mmID;
            super.callService(url, params, Constants.METHOD_GET);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        super.callService(Constants.GetNewJobForMMIDWebservice, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.getNewJobForMMIDWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.getNewJobForMMIDWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.getNewJobForMMIDWebserviceFailedWithMessage(message);
    }

    public GetNewJobForMMIDWebservice(GetNewJobForMMIDWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface GetNewJobForMMIDWebserviceHandler {
        void getNewJobForMMIDWebserviceStart();

        void getNewJobForMMIDWebserviceSucessful(String response, String message);

        void getNewJobForMMIDWebserviceFailedWithMessage(String message);
    }
}
