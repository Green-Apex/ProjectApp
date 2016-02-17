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
public class RegisterWebservice extends WebserviceBase {

    private final RegisterWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {

        super.callService(Constants.registerWebservice, params, method_type);

    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        super.callService(Constants.registerWebservice, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.registerWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.registerWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.registerWebserviceFailedWithMessage(message);
    }

    public RegisterWebservice(RegisterWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface RegisterWebserviceHandler {
        void registerWebserviceStart();

        void registerWebserviceSucessful(String response, String message);

        void registerWebserviceFailedWithMessage(String message);
    }
}
