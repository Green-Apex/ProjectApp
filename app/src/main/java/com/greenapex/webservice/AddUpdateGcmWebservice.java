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
public class AddUpdateGcmWebservice extends WebserviceBase {

    private final AddUpdateGcmWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {
        try {
            String email = params.getString(Constants.USER_EMAIL);
            String gcmID = "&gcmID="+params.getString(Constants.GCMID);
            String url = Constants.AddUpdateGcmWebservice + email+gcmID;
            params.remove(Constants.USER_EMAIL);
            params.remove(Constants.GCMID);
            super.callService(url, params, method_type);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        super.callService(Constants.AddUpdateGcmWebservice, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.addUpdateGcmWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.addUpdateGcmWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.addUpdateGcmWebserviceFailedWithMessage(message);
    }

    public AddUpdateGcmWebservice(AddUpdateGcmWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface AddUpdateGcmWebserviceHandler {
        void addUpdateGcmWebserviceStart();

        void addUpdateGcmWebserviceSucessful(String response, String message);

        void addUpdateGcmWebserviceFailedWithMessage(String message);
    }
}
