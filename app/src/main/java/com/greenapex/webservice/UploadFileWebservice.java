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
public class UploadFileWebservice extends WebserviceBase {

    private final UploadFileWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {


        try {

            String url = Constants.UploadFileWebservice + params.getString(Constants.SELECTFILE);
            super.callService(url, params, Constants.METHOD_POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        String ownerId = params.getString("ownerID");
        super.callService(Constants.UploadFileWebservice + ownerId, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.uploadFileWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.uploadFileWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.uploadFileWebserviceFailedWithMessage(message);
    }

    public UploadFileWebservice(UploadFileWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface UploadFileWebserviceHandler {
        void uploadFileWebserviceStart();

        void uploadFileWebserviceSucessful(String response, String message);

        void uploadFileWebserviceFailedWithMessage(String message);
    }
}
