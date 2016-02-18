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
public class GetActiveJobWebservice extends WebserviceBase {

    private final GetActiveJobWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {

        String ownerID = null;
        try {
            ownerID = params.getString("ownerID");
            String url = Constants.getActiveJobWebservice + ownerID;
            super.callService(url, params, Constants.METHOD_GET);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        super.callService(Constants.getActiveJobWebservice, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.GetActiveJobWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.GetActiveJobWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.GetActiveJobWebserviceFailedWithMessage(message);
    }

    public GetActiveJobWebservice(GetActiveJobWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface GetActiveJobWebserviceHandler {
        void GetActiveJobWebserviceStart();

        void GetActiveJobWebserviceSucessful(String response, String message);

        void GetActiveJobWebserviceFailedWithMessage(String message);
    }
}
