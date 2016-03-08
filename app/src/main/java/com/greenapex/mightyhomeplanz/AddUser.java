package com.greenapex.mightyhomeplanz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

import com.greenapex.R;
import com.greenapex.Request.models.AddUserRequest;
import com.greenapex.Utils.Constants;
import com.greenapex.mightyhomeplanz.entities.AddressModel;
import com.greenapex.webservice.AddUserWebservice;
import com.greenapex.widgets.CustomEditText;
import com.greenapex.widgets.CustomTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class AddUser extends BaseActivity implements OnClickListener {

    Activity activity;
    CustomTextView tvSave;
    private CustomEditText etZipCode_EditProfile;
    private CustomEditText etState_EditProfile;
    private CustomEditText etCity_EditProfile;
    private CustomEditText etStreetname_EditProfile;
    private CustomEditText etEmail_EditProfile;
    private CustomEditText etLastname_EditProfile;
    private CustomEditText etFirstname_EditProfile;
    private CustomEditText etPassword;
    private CustomEditText etConfirmPassword;
    private CustomEditText etContact_EditProfile;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_user);
        mProgressDialog = new ProgressDialog(this);
        activity = AddUser.this;
        init();
    }

    public void init() {

        etPassword = (CustomEditText) findViewById(R.id.etPassword);
        etConfirmPassword = (CustomEditText) findViewById(R.id.etConfirmPassword);
        etFirstname_EditProfile = (CustomEditText) findViewById(R.id.etFirstname_EditProfile);
        etLastname_EditProfile = (CustomEditText) findViewById(R.id.etLastname_EditProfile);
        etEmail_EditProfile = (CustomEditText) findViewById(R.id.etEmail_EditProfile);
        etStreetname_EditProfile = (CustomEditText) findViewById(R.id.etStreetname_EditProfile);
        etCity_EditProfile = (CustomEditText) findViewById(R.id.etCity_EditProfile);
        etState_EditProfile = (CustomEditText) findViewById(R.id.etState_EditProfile);
        etZipCode_EditProfile = (CustomEditText) findViewById(R.id.etZipCode_EditProfile);
        etContact_EditProfile = (CustomEditText) findViewById(R.id.etContact_EditProfile);
        tvSave = (CustomTextView) findViewById(R.id.tvSave_EditProfile);
        tvSave.setOnClickListener(this);
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {
            case R.id.tvSave_EditProfile:
                if (isValid()) {
                    AddUserRequest addUserRequest = new AddUserRequest();
                    addUserRequest.setFname(etFirstname_EditProfile.getText().toString());
                    addUserRequest.setLname(etLastname_EditProfile.getText().toString());
                    addUserRequest.setEmail(etEmail_EditProfile.getText().toString());
                    addUserRequest.setPwd(etPassword.getText().toString());
                    AddressModel addressModel = new AddressModel();
                    addressModel.setStreetAddress(etStreetname_EditProfile.getText().toString());
                    addressModel.setCity(etCity_EditProfile.getText().toString());
                    addressModel.setState(etState_EditProfile.getText().toString());
                    addressModel.setZip(etZipCode_EditProfile.getText().toString());
                    addUserRequest.setAddress(addressModel);
                    AddUserOnServer(addUserRequest);

                }

                break;

        }
    }

    private void AddUserOnServer(AddUserRequest addUserRequest) {
        AddUserWebservice addUserWebservice = new AddUserWebservice(new AddUserWebservice.AddUserWebserviceHandler() {
            @Override
            public void addUserWebserviceStart() {
                mProgressDialog.setMessage("processing...");
                mProgressDialog.show();
            }

            @Override
            public void addUserWebserviceSucessful(String response, String message) {
                mProgressDialog.dismiss();
                showLog(message);
                showToast(message);
                if (response.length() > 0) {

                    finish();
                    overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);

                }
            }

            @Override
            public void addUserWebserviceFailedWithMessage(String message) {
                mProgressDialog.dismiss();
                showLog(message);
                showToast(message);
            }
        }, this);

        try {
            JSONObject params = new JSONObject(addUserRequest.toString());
            params.put(Constants.USERID, getUserGson().getUserID());
            addUserWebservice.callService(params, Constants.METHOD_POST);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public boolean isValid() {
        boolean isSuccess = true;
        if (etFirstname_EditProfile.getText().length() <= 0) {
            Toast.makeText(AddUser.this, "Please enter First name", Toast.LENGTH_SHORT).show();
            etFirstname_EditProfile.requestFocus();
            isSuccess = false;
            return isSuccess;
        }

        if (etLastname_EditProfile.getText().length() <= 0) {
            Toast.makeText(AddUser.this, "Please enter Last name", Toast.LENGTH_SHORT).show();
            etLastname_EditProfile.requestFocus();
            isSuccess = false;
            return isSuccess;
        }
        if (etEmail_EditProfile.getText().length() <= 0) {
            Toast.makeText(AddUser.this, "Please enter Email", Toast.LENGTH_SHORT).show();
            etEmail_EditProfile.requestFocus();
            isSuccess = false;
            return isSuccess;
        }
        if (etPassword.getText().length() <= 0) {
            showToast("Please enter password.");
            etPassword.requestFocus();
            isSuccess = false;
            return isSuccess;
        } else {
            if (!etPassword.getText().toString().equalsIgnoreCase(etConfirmPassword.getText().toString())) {
                showLog("Password does not match.");
                etConfirmPassword.requestFocus();
                isSuccess = false;
                return isSuccess;
            }
        }

        return isSuccess;
    }
}
