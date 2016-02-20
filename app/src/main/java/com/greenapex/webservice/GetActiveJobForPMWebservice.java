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
public class GetActiveJobForPMWebservice extends WebserviceBase {

    private final GetActiveJobForPMWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {



        super.callService(Constants.GetActiveJobForPMWebservice, params, Constants.METHOD_GET);


    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        super.callService(Constants.GetActiveJobForPMWebservice, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.getActiveJobForPMWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.getActiveJobForPMWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.getActiveJobForPMWebserviceFailedWithMessage(message);
    }

    public GetActiveJobForPMWebservice(GetActiveJobForPMWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface GetActiveJobForPMWebserviceHandler {
        void getActiveJobForPMWebserviceStart();

        void getActiveJobForPMWebserviceSucessful(String response, String message);

        void getActiveJobForPMWebserviceFailedWithMessage(String message);
    }
}
