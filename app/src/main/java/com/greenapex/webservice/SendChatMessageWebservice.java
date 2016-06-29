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
public class SendChatMessageWebservice extends WebserviceBase {

    private final SendChatMessageWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {


        try {
            String jobId = params.getString(Constants.JOBID);
            String userid = "&userID=" + params.getString(Constants.USERID);
            String url = Constants.SendChatMessageWebservice + jobId + userid;
            super.callService(url, params, Constants.METHOD_POST);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        String ownerId = params.getString("ownerID");
        super.callService(Constants.SendChatMessageWebservice + ownerId, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.sendChatMessageWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.sendChatMessageWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.sendChatMessageWebserviceFailedWithMessage(message);
    }

    public SendChatMessageWebservice(SendChatMessageWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface SendChatMessageWebserviceHandler {
        void sendChatMessageWebserviceStart();

        void sendChatMessageWebserviceSucessful(String response, String message);

        void sendChatMessageWebserviceFailedWithMessage(String message);
    }
}
