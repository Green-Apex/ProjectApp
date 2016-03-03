package com.greenapex.mightyhomeplanz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greenapex.R;
import com.greenapex.Request.models.AddressRequest;
import com.greenapex.Request.models.RegisterRequest;
import com.greenapex.Utils.Constants;
import com.greenapex.Utils.Utils;
import com.greenapex.response.models.UserResponse;
import com.greenapex.webservice.RegisterWebservice;
import com.greenapex.widgets.CustomEditText;
import com.greenapex.widgets.CustomTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Signup extends Activity implements OnClickListener, RegisterWebservice.RegisterWebserviceHandler {

    private ScrollView scrollView1;
    private CustomEditText etUsernameSignup;
    private CustomEditText etPasswordSignup;
    private CustomEditText etConfirmPasswordSignup;
    private CustomEditText etFirstnameSignup;
    private CustomEditText etLastnameSignup;
    private CustomEditText etEmailSignup;
    private CustomEditText etStreetnameSignup;
    private CustomEditText etCitySignup;
    private CustomEditText etStateSignup;
    private CustomEditText etZipCodeSignup;
    private CustomEditText etContactSignup;
    private CustomEditText etCommunicationPreferenceSignup;
    private CustomTextView tvRegisterSignup;
    private Gson gson;
    private RegisterRequest registerRequest;
    private SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signup);

//        activity = Signup.this;
        gson = new GsonBuilder().create();
        progressDialog = new ProgressDialog(this);
        init();
    }

    public void init() {
        scrollView1 = (ScrollView) findViewById(R.id.scrollView1);
        etUsernameSignup = (CustomEditText) findViewById(R.id.etUsername_Signup);
        etPasswordSignup = (CustomEditText) findViewById(R.id.etPassword_Signup);
        etConfirmPasswordSignup = (CustomEditText) findViewById(R.id.etConfirmPassword_Signup);
        etFirstnameSignup = (CustomEditText) findViewById(R.id.etFirstname_Signup);
        etLastnameSignup = (CustomEditText) findViewById(R.id.etLastname_Signup);
        etEmailSignup = (CustomEditText) findViewById(R.id.etEmail_Signup);
        etStreetnameSignup = (CustomEditText) findViewById(R.id.etStreetname_Signup);
        etCitySignup = (CustomEditText) findViewById(R.id.etCity_Signup);
        etStateSignup = (CustomEditText) findViewById(R.id.etState_Signup);
        etZipCodeSignup = (CustomEditText) findViewById(R.id.etZipCode_Signup);
        etContactSignup = (CustomEditText) findViewById(R.id.etContact_Signup);
        etCommunicationPreferenceSignup = (CustomEditText) findViewById(R.id.etCommunicationPreference_Signup);
        tvRegisterSignup = (CustomTextView) findViewById(R.id.tvRegister_Signup);
        tvRegisterSignup.setOnClickListener(this);
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {
            case R.id.tvRegister_Signup:

                if (validateData()) {
                    String strParams = getGson().toJson(getRegisterRequest());
                    RegisterWebservice registerWebservice = new RegisterWebservice(this, this.getApplicationContext());
                    try {
                        JSONObject params = new JSONObject(strParams);
                        registerWebservice.callService(params);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                break;

        }
    }

    private boolean validateData() {
        boolean isValid = true;
        if (etUsernameSignup.getText().toString().trim().length() <= 0) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            etUsernameSignup.requestFocus();
            isValid = false;
            return isValid;
        }
        if (etPasswordSignup.getText().length() <= 0) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            etPasswordSignup.requestFocus();
            return isValid;
        } else {
            if (etConfirmPasswordSignup.getText().length() <= 0) {
                Toast.makeText(this, "Please enter Confirm Password", Toast.LENGTH_SHORT).show();
                etConfirmPasswordSignup.requestFocus();
                isValid = false;
                return isValid;
            } else {
                if (!etPasswordSignup.getText().toString().equalsIgnoreCase(etConfirmPasswordSignup.getText().toString())) {
                    Toast.makeText(this, "Passwords does not match please reenter the Passwords", Toast.LENGTH_SHORT).show();
                    etPasswordSignup.requestFocus();
                    isValid = false;
                    return isValid;
                }
            }
        }

        if (etEmailSignup.getText().length() <= 0) {
            Toast.makeText(this, "Please enter email...", Toast.LENGTH_SHORT).show();
            etEmailSignup.requestFocus();
            isValid = false;
            return isValid;

        } else if (!Utils.isValidEmail(etEmailSignup.getText().toString())) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            etEmailSignup.requestFocus();
            isValid = false;
            return isValid;
        }


        return isValid;

    }

    private RegisterRequest getRegisterRequest() {
        registerRequest = new RegisterRequest();

        registerRequest.setFname(etFirstnameSignup.getText().toString());
        registerRequest.setLname(etLastnameSignup.getText().toString());
        registerRequest.setEmail(etEmailSignup.getText().toString());
        registerRequest.setContactNo(etContactSignup.getText().toString());
        registerRequest.setPwd(etConfirmPasswordSignup.getText().toString());

        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setStreetAddress(etStreetnameSignup.getText().toString());
        addressRequest.setCity(etCitySignup.getText().toString());
        addressRequest.setState(etStateSignup.getText().toString());
        addressRequest.setZip(Long.valueOf(etZipCodeSignup.getText().toString()));

        registerRequest.setAddress(addressRequest);

        registerRequest.setRole("owner");

        return registerRequest;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this.getApplicationContext(), Signin.class));
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
        finish();
    }

    @Override
    public void registerWebserviceStart() {
//        Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show();
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }

    @Override
    public void registerWebserviceSucessful(String response, String message) {
//        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        Log.d("SignUp", response);
        progressDialog.dismiss();
        if (response.length() > 0) {
            UserResponse userResponse = gson.fromJson(response, UserResponse.class);
            sharedPreferences = getSharedPreferences(Constants.mightyHomePlanz, Context.MODE_PRIVATE);
            try {
                if (userResponse.toString() != null) {
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString(Constants.UserData, userResponse.toString());
//                    editor.commit();

                    startActivity(new Intent(this.getApplicationContext(), Signin.class));
                    overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                    finish();
                } else {
                    Log.d("SignUp", "Error saving user response data to Shared Preference");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error saving user data in preferences", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void registerWebserviceFailedWithMessage(String message) {
        Toast.makeText(this, "Fail:" + message, Toast.LENGTH_SHORT).show();
        Log.d("SignUp", message);
        progressDialog.dismiss();
    }

    protected Gson getGson() {

        return gson;
    }
}
