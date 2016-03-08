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
public class DeleteDocWebservice extends WebserviceBase {

    private final DeleteDocWebserviceHandler handler;

    private Context context = null;

    public void callService(@NonNull final JSONObject params, int method_type) throws UnsupportedEncodingException {


        try {
            String userID = params.getString(Constants.USERID);
            String jobID = "&jobID=" + params.getString(Constants.JOBID);
            String docID = "&docID" + params.getString(Constants.DOCID);
            String url = Constants.DeleteDocWebservice + userID + jobID + docID;
            params.remove(Constants.USERID);
            params.remove(Constants.JOBID);
            params.remove(Constants.DOCID);
            super.callService(url, params, Constants.METHOD_POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callService(@NonNull JSONObject params) throws UnsupportedEncodingException, JSONException {
        String userID = params.getString(Constants.USERID);
        String jobID = "&jobID=" + params.getString(Constants.JOBID);
        String docID = "&docID=" + params.getString(Constants.DOCID);
        String url = Constants.DeleteDocWebservice + userID + jobID + docID;
        params.remove(Constants.USERID);
        params.remove(Constants.JOBID);
        params.remove(Constants.DOCID);
        super.callService(url, params, Constants.METHOD_POST);
    }


    @Override
    public void webserviceStart() {

        handler.deleteDocWebserviceStart();
    }

    @Override
    public void webserviceOnProgress(int update) {

    }

    @Override
    public void webserviceSucessful(String response, String message) {
        handler.deleteDocWebserviceSucessful(response, message);
    }

    @Override
    public void webserviceFailedWithMessage(String message) {
        handler.deleteDocWebserviceFailedWithMessage(message);
    }

    public DeleteDocWebservice(DeleteDocWebserviceHandler aHandler, Context mContext) {
        super(mContext);
        handler = aHandler;
        context = mContext;

    }

    public interface DeleteDocWebserviceHandler {
        void deleteDocWebserviceStart();

        void deleteDocWebserviceSucessful(String response, String message);

        void deleteDocWebserviceFailedWithMessage(String message);
    }
}
