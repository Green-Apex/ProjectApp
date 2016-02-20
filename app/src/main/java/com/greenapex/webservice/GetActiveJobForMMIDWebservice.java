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
public class GetActiveJobForMMIDWebservice extends WebserviceBase {

    private final GetActiveJobForMMIDWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {


        String mmID = null;
        try {
            mmID = params.getString("mmID");
            String url = Constants.GetActiveJobForMMIDWebservice + mmID;
            super.callService(url, params, Constants.METHOD_GET);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        super.callService(Constants.GetActiveJobForMMIDWebservice, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.getActiveJobForMMIDWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.getActiveJobForMMIDWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.getActiveJobForMMIDWebserviceFailedWithMessage(message);
    }

    public GetActiveJobForMMIDWebservice(GetActiveJobForMMIDWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface GetActiveJobForMMIDWebserviceHandler {
        void getActiveJobForMMIDWebserviceStart();

        void getActiveJobForMMIDWebserviceSucessful(String response, String message);

        void getActiveJobForMMIDWebserviceFailedWithMessage(String message);
    }
}
