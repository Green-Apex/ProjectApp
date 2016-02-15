package com.acrobat;

/**
 * Created by rultech on 15/2/16.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by admin on 21-Oct-15.
 */
public abstract class WebserviceBase extends AsyncHttpResponseHandler {

    private final AsyncHttpClient client;

    @Override
    public void onProgress(long bytesWritten, long totalSize) {
        int update = (int) (bytesWritten);
        webserviceOnProgress(update);
        super.onProgress(bytesWritten, totalSize);
    }

    @Override
    public void onStart() {
        super.onStart();
        webserviceStart();

    }

    @Override
    public void onPreProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
        super.onPreProcessResponse(instance, response);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        webserviceFailedWithMessage("Unable to process please check your internet and try again...");
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


//        try {
//            //Whatever charset your bytes are endoded in
//            Charset charset = Charset.forName("UTF-8");
//            CharsetDecoder decoder = charset.newDecoder();
//
////ByteBuffer.wrap simply wraps the byte array, it does not allocate new memory for it
//            ByteBuffer srcBuffer = ByteBuffer.wrap(responseBody);
////Now, we decode our srcBuffer into a new CharBuffer (yes, new memory allocated here, no can do)
//            CharBuffer resBuffer = null;
//            resBuffer = decoder.decode(srcBuffer);
//            StringBuilder yourStringBuilder = new StringBuilder(resBuffer);
//            parseResponse(yourStringBuilder.toString());
//        } catch (CharacterCodingException e) {
//            e.printStackTrace();
//        }
//
////CharBuffer implements CharSequence interface, which StringBuilder fully support in it's methods
////
//        String response = new String(responseBody, Charset.defaultCharset());
        ByteArrayInputStream bis = new ByteArrayInputStream(responseBody);
        BufferedReader r = new BufferedReader(new InputStreamReader(bis));
        StringBuilder total = new StringBuilder();
        String line;
        try {
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            parseResponse(total.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String response = new String(responseBody, Charset.defaultCharset());
//        parseResponse(response);
    }


    // private final WebserviceHandler handler;
    private Context context;


    public abstract void webserviceStart();

    public abstract void webserviceOnProgress(int update);

    public abstract void webserviceSucessful(String response, String message);

    public abstract void webserviceFailedWithMessage(String message);


    public WebserviceBase(Context mContext) {
//        this.handler =handler aHandler;
        context = mContext;
        client = new AsyncHttpClient();
        client.setResponseTimeout(9999999);
        client.setLoggingLevel(Log.ERROR);
    }

    public abstract void callService(@NonNull final JSONObject params) throws UnsupportedEncodingException, JSONException;

    protected void callService(String url, final JSONObject params) throws UnsupportedEncodingException {
//        if (Utils.isInternetConnected()) {
//
//            StringEntity entity = new StringEntity(params.toString());
////            AsyncHttpClient client = new AsyncHttpClient();
//            client.post(context, url, entity, "application/json", this);
//
//            webserviceStart();
//        } else {
//            webserviceFailedWithMessage("Oops!!! Please check your internet.");
//        }


    }

    protected void callService(String url) {
//        if (Utils.isInternetConnected()) {
//            client.get(context, url, this);
//            webserviceStart();
//        } else {
//            webserviceFailedWithMessage("Oops!!! Please check your internet.");
//        }


    }

    protected void parseResponse(String response) {

        try {
//            Gson gson = new GsonBuilder().create();
//            CommonResponse commonResponse = gson.fromJson(response, CommonResponse.class);
//
//
//            if (commonResponse.isResult()) {
//                if (commonResponse.getData() != null) {
//
//                    if (commonResponse.getData().isJsonObject()) {
//                        webserviceSucessful(commonResponse.getData().getAsJsonObject().toString(), commonResponse.getMessage());
//                    } else {
//                        webserviceSucessful(commonResponse.getData().getAsJsonArray().toString(), commonResponse.getMessage());
//                    }
//
//                } else {
//                    if (commonResponse.getMessage() != null)
//                        webserviceFailedWithMessage("Unable to process please check your internet and try again...");
//                }
//            } else {
//                webserviceFailedWithMessage("Unable to process please try again...");
//            }
        } catch (Exception e) {
            webserviceFailedWithMessage("Unable to process please try again...");
            e.printStackTrace();
        }
    }
}
