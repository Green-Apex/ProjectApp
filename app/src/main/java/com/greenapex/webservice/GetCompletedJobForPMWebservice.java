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
public class GetCompletedJobForPMWebservice extends WebserviceBase {

    private final GetCompletedJobForPMWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {

        super.callService(Constants.GetCompletedJobForPMWebservice, params, Constants.METHOD_GET);


    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        super.callService(Constants.GetCompletedJobForPMWebservice, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.getCompletedJobForPMWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.getCompletedJobForPMWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.getCompletedJobForPMWebserviceFailedWithMessage(message);
    }

    public GetCompletedJobForPMWebservice(GetCompletedJobForPMWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface GetCompletedJobForPMWebserviceHandler {
        void getCompletedJobForPMWebserviceStart();

        void getCompletedJobForPMWebserviceSucessful(String response, String message);

        void getCompletedJobForPMWebserviceFailedWithMessage(String message);
    }
}
