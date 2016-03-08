package com.greenapex.mightyhomeplanz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.greenapex.R;
import com.greenapex.Request.models.UpdateUserRequest;
import com.greenapex.Utils.Constants;
import com.greenapex.Utils.Utils;
import com.greenapex.mightyhomeplanz.entities.AddressModel;
import com.greenapex.response.models.AddressResponse;
import com.greenapex.response.models.CommonResponse;
import com.greenapex.response.models.UserResponse;
import com.greenapex.webservice.UpdateUserWebservice;
import com.greenapex.widgets.CustomRoundedImageView;
import com.greenapex.widgets.CustomTextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class EditProfile extends BaseActivity implements OnClickListener {

    Activity activity;
    CustomTextView tvSave;
    private EditText etFirstname_EditProfile;
    private EditText etLastname_EditProfile;
    private EditText etEmail_EditProfile;
    private EditText etStreetname_EditProfile;
    private EditText etCity_EditProfile;
    private EditText etState_EditProfile;
    private EditText etZipCode_EditProfile;
    private EditText etContact_EditProfile;
    private ProgressDialog mProgressDialog;
    private CustomTextView tvChangePicture_EditProfile;
    private CustomTextView tvRemovePicture_EditProfile;
    private CustomRoundedImageView ivUserPic_SideMenu;
    private static File photoFile;
    private Utils utils;
    private static Uri cropped_Image;
    private static String uploadedImagePath = "";
    private RequestManager imageloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit_profile);

        utils = new Utils(this);
        imageloader = Glide.with(EditProfile.this);
        activity = EditProfile.this;
        mProgressDialog = new ProgressDialog(this);
        init();
        loadData();
    }

    private void loadData() {

        etFirstname_EditProfile.setText(getUserGson().getFname());
        etLastname_EditProfile.setText(getUserGson().getLname());
        etEmail_EditProfile.setText(getUserGson().getEmail());
        etStreetname_EditProfile.setText(getUserGson().getAddress().getStreetAddress());
        etCity_EditProfile.setText(getUserGson().getAddress().getCity());
        etState_EditProfile.setText(getUserGson().getAddress().getState());
        etZipCode_EditProfile.setText(getUserGson().getAddress().getZip());
        imageloader.load(Constants.BaseImageDomain + getUserGson().getProfilePic()).placeholder(R.drawable.noimage).into(ivUserPic_SideMenu);


    }

    public void init() {
        etFirstname_EditProfile = (EditText) findViewById(R.id.etFirstname_EditProfile);
        etLastname_EditProfile = (EditText) findViewById(R.id.etLastname_EditProfile);
        etEmail_EditProfile = (EditText) findViewById(R.id.etEmail_EditProfile);
        etStreetname_EditProfile = (EditText) findViewById(R.id.etStreetname_EditProfile);
        etCity_EditProfile = (EditText) findViewById(R.id.etCity_EditProfile);
        etState_EditProfile = (EditText) findViewById(R.id.etState_EditProfile);
        etZipCode_EditProfile = (EditText) findViewById(R.id.etZipCode_EditProfile);
        etContact_EditProfile = (EditText) findViewById(R.id.etContact_EditProfile);
        tvChangePicture_EditProfile = (CustomTextView) findViewById(R.id.tvChangePicture_EditProfile);
        tvChangePicture_EditProfile.setOnClickListener(this);
        tvRemovePicture_EditProfile = (CustomTextView) findViewById(R.id.tvRemovePicture_EditProfile);
        tvRemovePicture_EditProfile.setOnClickListener(this);
        ivUserPic_SideMenu = (CustomRoundedImageView) findViewById(R.id.ivUserPic_SideMenu);
        tvSave = (CustomTextView) findViewById(R.id.tvSave_EditProfile);
        tvSave.setOnClickListener(this);
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {
            case R.id.tvChangePicture_EditProfile: {
                new android.support.v7.app.AlertDialog.Builder(this)
                        .setTitle("Select Image")
                        .setMessage("Where would you like to select the image from ?")
                        .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                // Ensure that there's a camera activity to handle the intent
                                if (takePictureIntent.resolveActivity(EditProfile.this.getPackageManager()) != null) {
                                    // Create the File where the photo should go
                                    photoFile = null;
                                    try {
                                        photoFile = utils.createImageFile();
                                    } catch (IOException ex) {
                                        // Error occurred while creating the File
                                        ex.printStackTrace();
                                    }
                                    // Continue only if the File was successfully created
                                    if (photoFile != null) {
                                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                                Uri.fromFile(photoFile));
                                        startActivityForResult(takePictureIntent, Constants.ACTION_IMAGE_CAPTURE);
                                    }
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto, Constants.ACTION_PICK);
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
                break;
            }
            case R.id.tvRemovePicture_EditProfile: {
                ivUserPic_SideMenu.setImageBitmap(null);
                break;
            }
            case R.id.tvSave_EditProfile:
                if (isValid()) {
                    UpdateUserRequest updateUserRequest = new UpdateUserRequest();
                    updateUserRequest.setFname(etFirstname_EditProfile.getText().toString());
                    updateUserRequest.setLname(etLastname_EditProfile.getText().toString());
                    updateUserRequest.setEmail(etEmail_EditProfile.getText().toString());
                    AddressModel addressModel = new AddressModel();
                    addressModel.setStreetAddress(etStreetname_EditProfile.getText().toString());
                    addressModel.setCity(etCity_EditProfile.getText().toString());
                    addressModel.setState(etState_EditProfile.getText().toString());
                    addressModel.setZip(etZipCode_EditProfile.getText().toString());
                    updateUserRequest.setAddress(addressModel);
                    updateUserRequest.setContactNo(etContact_EditProfile.getText().toString());
                    updateUserRequest.setProfilePic(uploadedImagePath);
//                    updateUserRequest.setPwd();
                    UpdateUserOnServer(updateUserRequest);
                }

                break;

        }
    }

    private void UpdateUserOnServer(final UpdateUserRequest updateUserRequest) {
        UpdateUserWebservice updateUserWebservice = new UpdateUserWebservice(new UpdateUserWebservice.UpdateUserWebserviceHandler() {
            @Override
            public void updateUserWebserviceStart() {
                mProgressDialog.setMessage("updating...");
                mProgressDialog.show();
            }

            @Override
            public void updateUserWebserviceSucessful(String response, String message) {
                showToast(message);
                showLog(message);
                mProgressDialog.dismiss();
                updateUserData(updateUserRequest);
                finish();
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
            }

            @Override
            public void updateUserWebserviceFailedWithMessage(String message) {
                showLog(message);
                showToast(message);
                mProgressDialog.dismiss();
            }
        }, this);

        try {
            JSONObject params = new JSONObject(updateUserRequest.toString());
            params.put(Constants.USERID, getUserGson().getUserID());
            updateUserWebservice.callService(params, Constants.METHOD_POST);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void updateUserData(UpdateUserRequest updateUserRequest) {
        UserResponse userResponse = getUserGson();
        userResponse.setFname(updateUserRequest.getFname());
        userResponse.setLname(updateUserRequest.getLname());
        AddressResponse addressModel = getGson().fromJson(updateUserRequest.getAddress().toString(), AddressResponse.class);
        userResponse.setAddress(addressModel);
        setUserPreference(userResponse.toString());

    }

    private boolean isValid() {
        boolean isSuccess = true;
        if (etFirstname_EditProfile.getText().length() <= 0) {
            Toast.makeText(EditProfile.this, "Please enter First name", Toast.LENGTH_SHORT).show();
            etFirstname_EditProfile.requestFocus();
            isSuccess = false;
            return isSuccess;
        }

        if (etLastname_EditProfile.getText().length() <= 0) {
            Toast.makeText(EditProfile.this, "Please enter Last name", Toast.LENGTH_SHORT).show();
            etLastname_EditProfile.requestFocus();
            isSuccess = false;
            return isSuccess;
        }
        if (etEmail_EditProfile.getText().length() <= 0) {
            Toast.makeText(EditProfile.this, "Please enter Email", Toast.LENGTH_SHORT).show();
            etEmail_EditProfile.requestFocus();
            isSuccess = false;
            return isSuccess;
        }


        return isSuccess;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case Constants.ACTION_IMAGE_CAPTURE:
                if (resultCode == Activity.RESULT_OK) {
//                    Uri selectedImage = imageReturnedIntent.getData();
                    if (!doCrop(Uri.fromFile(photoFile)))
                        utils.showToast("Error Cropping Image");
                    //imageview.setImageURI(selectedImage);
                }

                break;
            case Constants.ACTION_PICK:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    if (!doCrop(selectedImage))
                        utils.showToast("Error Cropping Image");
                    //imageview.setImageURI(selectedImage);
                }
                break;
            case Constants.CROP_PIC_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {

                    if (cropped_Image != null) {

                        File imageFile = new File(cropped_Image.getPath());
                        UpdateImageToServer(imageFile);

                    }
                }
                break;
        }
    }

    private boolean doCrop(Uri picUri) {
        File tmpCropFile = null;
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            tmpCropFile = utils.createImageFile();
            cropped_Image = Uri.fromFile(tmpCropFile);
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, cropped_Image);
            startActivityForResult(cropIntent, Constants.CROP_PIC_REQUEST_CODE);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void UpdateImageToServer(File imageFile) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(9999);
            RequestParams params = new RequestParams();
            params.put("file", imageFile.getAbsoluteFile());
            client.post(Constants.UploadFileWebservice, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    mProgressDialog.dismiss();
                    ByteArrayInputStream bis = new ByteArrayInputStream(responseBody);
                    BufferedReader r = new BufferedReader(new InputStreamReader(bis));
                    StringBuilder total = new StringBuilder();
                    String line;
                    try {
                        while ((line = r.readLine()) != null) {
                            total.append(line);
                        }
                        parseResponse(total.toString(), statusCode);
                        //Toast.makeText(AddNewProject.this, "Response: " + total.toString(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStart() {
                    super.onStart();
                    mProgressDialog.setMessage("Uploading Image...");
                    mProgressDialog.show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    mProgressDialog.dismiss();
                    Log.e(getClass().getSimpleName(), "Error:" + error.getLocalizedMessage());
                }

                @Override
                public void onProgress(long bytesWritten, long totalSize) {
                    super.onProgress(bytesWritten, totalSize);
                    mProgressDialog.setMessage(((int) ((100 * bytesWritten) / totalSize)) + "% uploaded");

                }

            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(getClass().getSimpleName(), "Error:" + e.getLocalizedMessage());
        }

    }

    protected void parseResponse(String response, int statusCode) {

        try {
            Gson gson = new GsonBuilder().create();
            CommonResponse commonResponse = gson.fromJson(response, CommonResponse.class);
            if (statusCode == 200) {
                if (commonResponse.getData().isJsonObject()) {


                } else if (commonResponse.getData().isJsonArray()) {

                    for (JsonElement item :
                            commonResponse.getData().getAsJsonArray()) {
                        uploadedImagePath = item.getAsString();
                        ivUserPic_SideMenu.setImageURI(cropped_Image);
                    }

                } else {

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
