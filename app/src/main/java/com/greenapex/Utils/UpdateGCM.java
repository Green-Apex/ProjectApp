package com.greenapex.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.greenapex.webservice.AddUpdateGcmWebservice;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by admin on 12-Mar-16.by ${COMPUTERNAME}
 */
public class UpdateGCM {
    private final Activity mActivity;
    private final UpdateGcmWebserviceHandler mHandler;
    private String email;
    private GoogleCloudMessaging gcmID;
    private String regid;
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String PROPERTY_APP_VERSION = "appVersion";
    protected final String SENDER_ID = "772008974284";
    private final String TAG = UpdateGCM.this.getClass().getSimpleName();

    public UpdateGCM(Activity mActivity, String email, UpdateGcmWebserviceHandler mHandler) {
        this.email = email;
        this.mActivity = mActivity;
        this.mHandler = mHandler;

    }


    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context
                    .getPackageManager()
                    .getPackageInfo(context
                            .getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public void updateGCMID() {
        if (checkPlayServices()) {
            gcmID = GoogleCloudMessaging
                    .getInstance(mActivity);
            regid = getRegistrationId(mActivity);

            if (regid
                    .isEmpty()) {
                registerInBackground();
            } else {
                Log.d(TAG, "No valid Google Play Services APK found.");
            }
        } else {
            Toast.makeText(mActivity, "Google Play Services not found, Notification will not work for you.", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerInBackground() {

        Log.d(TAG, "########################################");
        Log.d(TAG, "Current Device's Registration ID is: " + regid);
        final AddUpdateGcmWebservice addUpdateGcmWebservice = new AddUpdateGcmWebservice(new AddUpdateGcmWebservice.AddUpdateGcmWebserviceHandler() {
            @Override
            public void addUpdateGcmWebserviceStart() {
                mHandler.updateGcmWebserviceStart();
            }

            @Override
            public void addUpdateGcmWebserviceSucessful(String response, String message) {
                mHandler.updateGcmWebserviceSucessful(response, message);
                Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
                Log.d(TAG, response);
                Log.d(TAG, "GCMID" + regid);
            }

            @Override
            public void addUpdateGcmWebserviceFailedWithMessage(String message) {
                mHandler.updateGcmWebserviceFailedWithMessage(message);
                Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
            }
        }, mActivity);


        new Thread(new Runnable() {
            //
            @Override
            public void run() {
                try {
                    JSONObject params = new JSONObject();
                    if (gcmID == null) {
                        gcmID = GoogleCloudMessaging
                                .getInstance(mActivity);
                    }
                    regid = gcmID
                            .register(SENDER_ID);
                    Log.d(TAG, "Current Device's Registration ID is: " + regid);
                    params.put(Constants.USER_EMAIL, email);
                    params.put(Constants.GCMID, regid);
                    addUpdateGcmWebservice.callService(params, Constants.METHOD_POST);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                // String msg = "";
//                try {
//                    if (gcmID == null) {
//                        gcmID = GoogleCloudMessaging
//                                .getInstance(mActivity);
//                    }
//                    regid = gcmID
//                            .register(SENDER_ID);
//                    Log.d(TAG, "########################################");
//                    Log.d(TAG, "Current Device's Registration ID is: " + regid);
//                    VolleyRequests vReq = new VolleyRequests(ActivitySplash.this, mHandler);
//                    Map<String, String> params = new HashMap<String, String>();
//                    String url = BebuzeeConstants.BaseDomain + BebuzeeConstants.AppController + "?action=checkdevice&userid=" + userid;
//                    params.put("userid", userid);
//                    params.put("deviceid", regid);
//                    params.put("device_type", BebuzeeConstants.DEVICE_TYPE);
//                    vReq.PostRequest(url, BebuzeeConstants.FROM_GCM_REGISTRATION, params);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                    // msg = "Error :" + ex.getMessage();
//                }
//            }
//        }).start();

    }

    private SharedPreferences getGCMPreferences(Context context) {
        return mActivity.getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);
    }

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(mActivity);
        String registrationId = prefs
                .getString(PROPERTY_REG_ID, "");
        if (registrationId
                .isEmpty()) {
            Log.d(TAG, "Registration ID not found.");
            return "";
        }
        int registeredVersion = prefs
                .getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(mActivity);
        if (registeredVersion != currentVersion) {
            Log.d(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(mActivity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil
                    .isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil
                        .getErrorDialog(resultCode, mActivity, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.d(TAG, "This device is not supported - Google Play Services.");

            }
            return false;
        }
        return true;
    }

    public interface UpdateGcmWebserviceHandler {
        void updateGcmWebserviceStart();

        void updateGcmWebserviceSucessful(String response, String message);

        void updateGcmWebserviceFailedWithMessage(String message);
    }
}
