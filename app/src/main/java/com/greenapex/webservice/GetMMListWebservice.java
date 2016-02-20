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
public class GetMMListWebservice extends WebserviceBase {

    private final GetMMListWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {

        String role = null;
        try {
            role = params.getString("role");
            String url = Constants.GetMMListWebservice + role;
            super.callService(url, params, Constants.METHOD_GET);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        super.callService(Constants.GetMMListWebservice, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.getMMListWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.getMMListWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.getMMListWebserviceFailedWithMessage(message);
    }

    public GetMMListWebservice(GetMMListWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface GetMMListWebserviceHandler {
        void getMMListWebserviceStart();

        void getMMListWebserviceSucessful(String response, String message);

        void getMMListWebserviceFailedWithMessage(String message);
    }
}
