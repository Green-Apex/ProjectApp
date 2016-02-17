package com.greenapex.mightyhomeplanz;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greenapex.Request.models.ForgotPasswordRequest;
import com.greenapex.Utils.Utils;
import com.greenapex.webservice.ForgotPasswordWebservice;
import com.greenapex.widgets.CustomEditText;
import com.greenapex.widgets.CustomTextView;
import com.greenapex.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class ForgotPassword extends Activity implements OnClickListener, ForgotPasswordWebservice.ForgotPasswordWebserviceHandler {

    Activity activity;
    CustomTextView tvSend;
    private CustomEditText tvEmail;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forgotpassword);

        activity = ForgotPassword.this;
        gson = new GsonBuilder().create();
        init();
    }

    public void init() {
        tvSend = (CustomTextView) findViewById(R.id.tvSend_ForgotPassword);
        tvEmail=(CustomEditText)findViewById(R.id.etEmail_ForgotPassword);

        tvSend.setOnClickListener(this);
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {
            case R.id.tvSend_ForgotPassword:
               if(validatedata()) {
                   ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
                   forgotPasswordRequest.setEmail(tvEmail.getText().toString());

                   String strParams = getGson().toJson(forgotPasswordRequest);
                   ForgotPasswordWebservice forgotPasswordWebservice=new ForgotPasswordWebservice(this,this.getApplicationContext());
                   try {
                       JSONObject params=new JSONObject(strParams);
                       forgotPasswordWebservice.callService(params);
                   } catch (JSONException e) {
                       e.printStackTrace();
                   } catch (UnsupportedEncodingException e) {
                       e.printStackTrace();
                   }


//                   startActivity(new Intent(activity, Signin.class));
//                   overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                   finish();

                   break;
               }
        }
    }


    private Boolean validatedata() {
        Boolean isValid=true;
        if (tvEmail.getText().length() <= 0) {
            Toast.makeText(this, "Please enter email...", Toast.LENGTH_SHORT).show();
            tvEmail.requestFocus();
            isValid = false;
            return isValid;

        } else if (!Utils.isValidEmail(tvEmail.getText().toString())) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            tvEmail.requestFocus();
            isValid = false;
            return isValid;
        }


        return isValid;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(activity, Signin.class));
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
        finish();
    }

    @Override
    public void ForgotPasswordWebserviceStart() {

    }

    @Override
    public void ForgotPasswordWebserviceSucessful(String response, String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void ForgotPasswordWebserviceFailedWithMessage(String message) {
        Toast.makeText(this, "Fail:" + message, Toast.LENGTH_SHORT).show();
        Log.d(message, "Failed Forgot");

    }
    private Gson getGson() {
        return gson;
    }


}
