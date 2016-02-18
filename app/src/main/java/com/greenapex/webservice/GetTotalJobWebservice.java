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
public class GetTotalJobWebservice extends WebserviceBase {

    private final GetTotalJobWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {

        String ownerID = null;
        try {
            ownerID = params.getString("ownerID");
            String url = Constants.getTotalJobWebservice + ownerID;
            super.callService(url, params, Constants.METHOD_POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        super.callService(Constants.getTotalJobWebservice, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.GetTotalJobWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.GetTotalJobWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.GetTotalJobWebserviceFailedWithMessage(message);
    }

    public GetTotalJobWebservice(GetTotalJobWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface GetTotalJobWebserviceHandler {
        void GetTotalJobWebserviceStart();

        void GetTotalJobWebserviceSucessful(String response, String message);

        void GetTotalJobWebserviceFailedWithMessage(String message);
    }
}
