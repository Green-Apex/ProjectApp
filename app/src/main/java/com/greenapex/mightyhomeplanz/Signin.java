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
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greenapex.R;
import com.greenapex.Request.models.LoginRequest;
import com.greenapex.Request.models.SignInFbRequest;
import com.greenapex.Utils.Constants;
import com.greenapex.Utils.Utils;
import com.greenapex.response.models.UserResponse;
import com.greenapex.webservice.LoginWebservice;
import com.greenapex.webservice.SignUpWithFBWebservice;
import com.greenapex.widgets.CustomEditText;
import com.greenapex.widgets.CustomTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Signin extends Activity implements OnClickListener, LoginWebservice.LoginWebserviceHandler {

    Activity activity;
    CustomTextView tvCreateAccount, tvForgotPassword, tvSignin, tvFacebook;
    private CustomEditText etUsername_Signin;
    private CustomEditText etPassword_Signin;
    private Gson gson;
    private ProgressDialog progressDialog;
    private CustomTextView tvfb_Signin;
    private CallbackManager callbackManager;
    private UserResponse userGson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signin);

        activity = Signin.this;
        progressDialog = new ProgressDialog(this);
        gson = new GsonBuilder().create();
        init();

    }

    public void init() {
        tvCreateAccount = (CustomTextView) findViewById(R.id.tvCreateAccount_Signin);
        tvForgotPassword = (CustomTextView) findViewById(R.id.tvForgotPassword_Signin);
        tvSignin = (CustomTextView) findViewById(R.id.tvsignin_Signin);
        tvfb_Signin = (CustomTextView) findViewById(R.id.tvfb_Signin);
        etUsername_Signin = (CustomEditText) findViewById(R.id.etUsername_Signin);
        etPassword_Signin = (CustomEditText) findViewById(R.id.etPassword_Signin);

        tvCreateAccount.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        tvSignin.setOnClickListener(this);
        tvfb_Signin.setOnClickListener(this);

        /*
        pre-loading data for testing
         */
//        etUsername_Signin.setText("nilay.khandhar@green-apex.com");
//        etPassword_Signin.setText("nilay");
        etUsername_Signin.setText("");
        etPassword_Signin.setText("");
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
    }


    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {

            case R.id.tvCreateAccount_Signin:
                startActivity(new Intent(activity, Signup.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                break;

            case R.id.tvForgotPassword_Signin:
                startActivity(new Intent(activity, ForgotPassword.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                break;

            case R.id.tvsignin_Signin:
                if (validateData()) {
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setEmail(etUsername_Signin.getText().toString());
//                    loginRequest.setEmail("nilay.khandhar@green-apex.com");
//                    loginRequest.setPassword(Utils.stringToMd5(etPassword_Signin.getText().toString()));
                    loginRequest.setPassword(etPassword_Signin.getText().toString());
                    if (etUsername_Signin.getText().toString().equalsIgnoreCase("arpit.thakkar@green-apex.com"))
                        loginRequest.setRole(Constants.OWNER);
                    else if (etUsername_Signin.getText().toString().equalsIgnoreCase("niraj.darji@green-apex.com"))
                        loginRequest.setRole(Constants.PM);
                    else if (etUsername_Signin.getText().toString().equalsIgnoreCase("nilay.khandhar@green-apex.com"))
                        loginRequest.setRole(Constants.MM);
                    else if (etUsername_Signin.getText().toString().equalsIgnoreCase("admin@mighty.com"))
                        loginRequest.setRole(Constants.SPM);


                    String strParams = getGson().toJson(loginRequest);
                    LoginWebservice loginWebservice = new LoginWebservice(this, this);

                    try {
                        JSONObject params = new JSONObject(strParams);
                        loginWebservice.callService(params);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }


//                startActivity(new Intent(activity, Home.class));
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                finish();
                break;
            case R.id.tvfb_Signin: {
                progressDialog.show();
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Utils.showLog(getClass().getSimpleName(), Profile.getCurrentProfile().toString());
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.v("LoginActivity", response.toString());
                                        SignInWithFB(response);
                                        // Application code
                                        // String email = object.getString("email");
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,first_name,last_name,email,picture");
                        request.setParameters(parameters);
                        request.executeAsync();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancel() {
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(Signin.this, "Error: " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
                break;
            }
        }
    }

    private void SignInWithFB(GraphResponse response) {
        SignUpWithFBWebservice signUpWithFBWebservice = new SignUpWithFBWebservice(new SignUpWithFBWebservice.SignUpWithFBWebserviceHandler() {
            @Override
            public void signUpWithFBWebserviceStart() {
//                progressDialog.show();
            }

            @Override
            public void signUpWithFBWebserviceSucessful(String response, String message) {
//                progressDialog.dismiss();
                parseFbLoginResponse(response, message);
            }

            @Override
            public void signUpWithFBWebserviceFailedWithMessage(String message) {
                Toast.makeText(Signin.this, "Error:" + message, Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
            }
        }, this);
        try {
            JSONObject mResponse = response.getJSONObject();
            SignInFbRequest signInFbRequest = new SignInFbRequest();
            ;
            signInFbRequest.setFbID(mResponse.getString("id"));
            signInFbRequest.setFname(mResponse.getString("first_name"));
            signInFbRequest.setLname(mResponse.getString("last_name"));
            signInFbRequest.setEmail(mResponse.getString("email"));
            JSONObject params = new JSONObject(signInFbRequest.toString());
            signUpWithFBWebservice.callService(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateData() {
        boolean isvalid = true;
        if (etUsername_Signin.getText().length() <= 0) {
            Toast.makeText(this, "Looks like you forgot to enter your username !!!", Toast.LENGTH_SHORT).show();
            etUsername_Signin.requestFocus();
            isvalid = false;
            return isvalid;
        }
        if (etPassword_Signin.getText().length() <= 0) {
            Toast.makeText(this, "Looks like you forgot to enter your password !!!", Toast.LENGTH_SHORT).show();
            etPassword_Signin.requestFocus();
            isvalid = false;
            return isvalid;
        }


        return isvalid;
    }

    @Override
    public void loginWebserviceStart() {
//        Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show();
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }

    @Override
    public void loginWebserviceSucessful(String response, String message) {
//        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        if (response.length() > 0) {

            try {
                if (!response.equalsIgnoreCase("\"\"")) {
                    UserResponse userResponse = gson.fromJson(response, UserResponse.class);
                    SharedPreferences sharedPreferences = getSharedPreferences(Constants.mightyHomePlanz, Context.MODE_PRIVATE);
                    if (userResponse.toString() != null) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constants.UserData, userResponse.toString());
                        editor.commit();

                        startActivity(new Intent(this.getApplicationContext(), Home.class));
                        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                        finish();

                    } else {
                        Log.d("SignUp", "Error saving user response data to Shared Preference");
                    }
                } else {
                    Toast.makeText(Signin.this, message, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("SignUp", "Error saving user data in preferences");
            }
        }
    }

    @Override
    public void loginWebserviceFailedWithMessage(String message) {
        Toast.makeText(this, "Fail:" + message, Toast.LENGTH_SHORT).show();
        progressDialog.hide();
    }


    protected Gson getGson() {
        return gson;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void parseFbLoginResponse(String response, String message) {
        if (response.length() > 0) {

            try {
                if (!response.equalsIgnoreCase("\"\"")) {
                    UserResponse userResponse = gson.fromJson(response, UserResponse.class);
                    SharedPreferences sharedPreferences = getSharedPreferences(Constants.mightyHomePlanz, Context.MODE_PRIVATE);
                    if (userResponse.toString() != null) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constants.UserData, userResponse.toString());
                        editor.commit();

                        startActivity(new Intent(this.getApplicationContext(), Home.class));
                        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                        finish();
                    } else {
                        Log.d("SignUp", "Error saving user response data to Shared Preference");
                    }
                } else {
                    Toast.makeText(Signin.this, message, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("SignUp", "Error saving user data in preferences");
            }
        }
    }

    public UserResponse getUserGson() {
        return userGson;
    }

    public void setUserGson(UserResponse userGson) {
        this.userGson = userGson;
    }
}
