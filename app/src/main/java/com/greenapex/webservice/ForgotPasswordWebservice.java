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
public class ForgotPasswordWebservice extends WebserviceBase {

    private final ForgotPasswordWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {

        try {
            String email = params.getString("email");
            String url = Constants.forgotPasswordWebservice + "email=" + email;
            callService(url, params, method_type);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        try {
            String email = params.getString("email");
            String url = Constants.forgotPasswordWebservice + "email=" + email;
            callService(url, params, Constants.METHOD_POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void webserviceStart() {

        handler.ForgotPasswordWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.ForgotPasswordWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.ForgotPasswordWebserviceFailedWithMessage(message);
    }

    public ForgotPasswordWebservice(ForgotPasswordWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface ForgotPasswordWebserviceHandler {
        void ForgotPasswordWebserviceStart();

        void ForgotPasswordWebserviceSucessful(String response, String message);

        void ForgotPasswordWebserviceFailedWithMessage(String message);
    }
}
