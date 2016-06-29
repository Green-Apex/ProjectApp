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
public class SignUpWithFBWebservice extends WebserviceBase {

    private final SignUpWithFBWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {


        try {
            String email = params.getString("email");
            String password = params.getString("password");
            String role = params.getString("role");
            String url = Constants.SignUpWithFBWebservice + "email=" + email + "&password=" + password + "&role=" + role;

            callService(url, params, method_type);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        try {

            String url = Constants.SignUpWithFBWebservice;

            callService(url, params, Constants.METHOD_POST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void webserviceStart() {

        handler.signUpWithFBWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.signUpWithFBWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.signUpWithFBWebserviceFailedWithMessage(message);
    }

    public SignUpWithFBWebservice(SignUpWithFBWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface SignUpWithFBWebserviceHandler {
        void signUpWithFBWebserviceStart();

        void signUpWithFBWebserviceSucessful(String response, String message);

        void signUpWithFBWebserviceFailedWithMessage(String message);
    }
}
