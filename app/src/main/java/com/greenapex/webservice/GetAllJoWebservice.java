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
public class GetAllJoWebservice extends WebserviceBase {

    private final GetAllJoWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {

        String userID = null;
        try {
            userID = params.getString("userID");
            String url = Constants.GetAllJoWebservice + userID;
//            params.remove("userID");
            super.callService(url, params, Constants.METHOD_GET);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        super.callService(Constants.GetAllJoWebservice, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.GetAllJoWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.GetAllJoWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.GetAllJoWebserviceFailedWithMessage(message);
    }

    public GetAllJoWebservice(GetAllJoWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface GetAllJoWebserviceHandler {
        void GetAllJoWebserviceStart();

        void GetAllJoWebserviceSucessful(String response, String message);

        void GetAllJoWebserviceFailedWithMessage(String message);
    }
}
