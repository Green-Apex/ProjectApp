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
public class GetJobChatWebservice extends WebserviceBase {

    private final GetJobChatWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {

        String jobID = null;
        try {
            jobID = params.getString(Constants.JOBID);
            String startIndex = "&startIndex=" + params.getString(Constants.STARTINDEX);
            String endIndex = "&endIndex=" + params.getString(Constants.ENDINDEX);
            String url = Constants.GetJobChatWebservice + jobID + startIndex + endIndex;
            params.remove(Constants.JOBID);
            params.remove(Constants.STARTINDEX);
            params.remove(Constants.ENDINDEX);
            super.callService(url, params, method_type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        super.callService(Constants.GetJobChatWebservice, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.getJobChatWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.getJobChatWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.getJobChatWebserviceFailedWithMessage(message);
    }

    public GetJobChatWebservice(GetJobChatWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface GetJobChatWebserviceHandler {
        void getJobChatWebserviceStart();

        void getJobChatWebserviceSucessful(String response, String message);

        void getJobChatWebserviceFailedWithMessage(String message);
    }
}
