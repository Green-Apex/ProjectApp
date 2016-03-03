package com.greenapex.mightyhomeplanz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.greenapex.Adaptors.ImageViewPagerAdapter;
import com.greenapex.CustomViews.SquareImageView;
import com.greenapex.R;
import com.greenapex.Request.models.AddJobRequest;
import com.greenapex.Request.models.JobAddressRequest;
import com.greenapex.Utils.Constants;
import com.greenapex.Utils.Utils;
import com.greenapex.response.models.CommonResponse;
import com.greenapex.response.models.UserResponse;
import com.greenapex.webservice.AddJobWebservice;
import com.greenapex.widgets.CustomCheckBox;
import com.greenapex.widgets.CustomEditText;
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
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class AddNewProject extends Activity implements OnClickListener, AddJobWebservice.AddJobWebserviceHandler {

    Activity activity;
    private ImageButton btnCloseAddNewProject;
    private CustomEditText etProjectNameAddNewProject;
    private CustomEditText etProjectDescAddNewProject;
    private ImageButton imageButton1;
    private ImageButton imgBtnAddImage;
    private CustomEditText etStreetAddressAddNewProject;
    private CustomEditText etCityAddNewProject;
    private CustomEditText etStateAddNewProject;
    private CustomEditText etZipAddNewProject;
    private CustomCheckBox cbSaveAddressAddNewProject;
    private Spinner spUrgencyOfRequestAddNewProject;
    private CustomTextView tvSubmitAddNewProject;
    private Gson gson;
    private ProgressDialog progressDialog;
    private File photoFile;
    private Utils utils;
    private Uri cropped_Image;
    private String urgencyOfWork_string = "";
    private ImageButton imgBtnDeleteImage;
    private ArrayList<String> arrImages = new ArrayList<>();
    private ViewPager viewPagerImages;
    private RequestManager imageLoader;
    private ImageViewPagerAdapter imageViewPagerAdapter;
    private SquareImageView imgNoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_project);

        utils = new Utils(this);
        imageLoader = Glide.with(this);
//        activity = AddNewProject.this;
        gson = new GsonBuilder().create();
        progressDialog = new ProgressDialog(this);

        init();
    }

    public void init() {
        btnCloseAddNewProject = (ImageButton) findViewById(R.id.btnClose_AddNewProject);
        etProjectNameAddNewProject = (CustomEditText) findViewById(R.id.etProjectName_AddNewProject);
        etProjectDescAddNewProject = (CustomEditText) findViewById(R.id.etProjectDesc_AddNewProject);
        imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        imgBtnAddImage = (ImageButton) findViewById(R.id.imgBtnAddImage);
        etStreetAddressAddNewProject = (CustomEditText) findViewById(R.id.etStreetAddress_AddNewProject);
        etCityAddNewProject = (CustomEditText) findViewById(R.id.etCity_AddNewProject);
        etStateAddNewProject = (CustomEditText) findViewById(R.id.etState_AddNewProject);
        etZipAddNewProject = (CustomEditText) findViewById(R.id.etZip_AddNewProject);
        cbSaveAddressAddNewProject = (CustomCheckBox) findViewById(R.id.cbSaveAddress_AddNewProject);
        spUrgencyOfRequestAddNewProject = (Spinner) findViewById(R.id.spUrgencyOfRequest_AddNewProject);
        tvSubmitAddNewProject = (CustomTextView) findViewById(R.id.tvSubmit_AddNewProject);
        imgBtnDeleteImage = (ImageButton) findViewById(R.id.imgBtnDeleteImage);
        imgNoImage = (SquareImageView) findViewById(R.id.imgNoImage);
        imgBtnDeleteImage.setOnClickListener(this);
        imgBtnAddImage.setOnClickListener(this);
        btnCloseAddNewProject.setOnClickListener(this);
        tvSubmitAddNewProject.setOnClickListener(this);

        viewPagerImages = (ViewPager) findViewById(R.id.viewPagerImages);
        imageViewPagerAdapter = new ImageViewPagerAdapter(this, arrImages, imageLoader);
        viewPagerImages.setAdapter(imageViewPagerAdapter);

        spUrgencyOfRequestAddNewProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                urgencyOfWork_string = spUrgencyOfRequestAddNewProject.getAdapter().getItem(position).toString();
                Log.d("AddNewProject:", urgencyOfWork_string);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (arrImages.size() <= 0) {
            imgBtnDeleteImage.setVisibility(View.GONE);
            imgNoImage.setVisibility(View.VISIBLE);
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

    private void UpdateImageToServer(File imageFile) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(9999);
            RequestParams params = new RequestParams();
            params.put("file", imageFile.getAbsoluteFile());
            client.post(Constants.UploadFileWebservice, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    progressDialog.dismiss();
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
                    progressDialog.setMessage("Uploading Image...");
                    progressDialog.show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    progressDialog.dismiss();
                    Log.e(getClass().getSimpleName(), "Error:" + error.getLocalizedMessage());
                }

                @Override
                public void onProgress(long bytesWritten, long totalSize) {
                    super.onProgress(bytesWritten, totalSize);
                    progressDialog.setMessage(((int) ((100 * bytesWritten) / totalSize)) + "% uploaded");

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
                        arrImages.add(item.getAsString());
                        imageViewPagerAdapter = new ImageViewPagerAdapter(this, arrImages, imageLoader);
                        viewPagerImages.setAdapter(imageViewPagerAdapter);
                        if (arrImages.size() > 0) {
                            imgBtnDeleteImage.setVisibility(View.VISIBLE);
                            imgNoImage.setVisibility(View.GONE);
                        } else {
                            imgBtnDeleteImage.setVisibility(View.GONE);
                            imgNoImage.setVisibility(View.VISIBLE);
                        }
                    }

                } else {

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {
            case R.id.imgBtnAddImage: {
                new android.support.v7.app.AlertDialog.Builder(AddNewProject.this)
                        .setTitle("Select Image")
                        .setMessage("Where would you like to select the image from ?")
                        .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                // Ensure that there's a camera activity to handle the intent
                                if (takePictureIntent.resolveActivity(AddNewProject.this.getPackageManager()) != null) {
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

            case R.id.btnClose_AddNewProject:
                finish();
                overridePendingTransition(R.anim.nothing, R.anim.slide_out_left);
                break;
            case R.id.imgBtnDeleteImage:
                //TODO://delete image from array.
                int itemPosition = viewPagerImages.getCurrentItem();
                arrImages.remove(itemPosition);
                imageViewPagerAdapter = new ImageViewPagerAdapter(this, arrImages, imageLoader);
                viewPagerImages.setAdapter(imageViewPagerAdapter);
                if (arrImages.size() > 0) {
                    imgBtnDeleteImage.setVisibility(View.VISIBLE);
                } else {
                    imgBtnDeleteImage.setVisibility(View.GONE);
                    imgNoImage.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.tvSubmit_AddNewProject:

                AddJobRequest addJobRequest = new AddJobRequest();

                addJobRequest.setJobTitle(etProjectNameAddNewProject.getText().toString());
                addJobRequest.setJobDescription(etProjectDescAddNewProject.getText().toString());
                addJobRequest.setUrgencyOfWork(urgencyOfWork_string);
                /**
                 * temp code logic for multiple images pending.
                 */

                addJobRequest.setImages(arrImages);

                JobAddressRequest jobAddressRequest = new JobAddressRequest();
                jobAddressRequest.setAddressID("");
                jobAddressRequest.setStreetAddress(etStreetAddressAddNewProject.getText().toString());
                jobAddressRequest.setCity(etCityAddNewProject.getText().toString());
                jobAddressRequest.setState(etStateAddNewProject.getText().toString());
                String pincode = etZipAddNewProject.getText().toString();
                if(pincode.length()>0)
                {
                    jobAddressRequest.setPincode(Long.parseLong(etZipAddNewProject.getText().toString()));
                }


                addJobRequest.setAddress(jobAddressRequest);
                SharedPreferences sp = getSharedPreferences(Constants.mightyHomePlanz, MODE_PRIVATE);
                String userGson = sp.getString(Constants.UserData, "");
                UserResponse userResponse = gson.fromJson(userGson, UserResponse.class);
                addJobRequest.setUserID(userResponse.getUserID());
                String strParams = getGson().toJson(addJobRequest);
                AddJobWebservice addJobWebservice = new AddJobWebservice(this, this.getApplicationContext());
                try {
                    JSONObject params = new JSONObject(strParams);

                    //Constants.AddJobWebservice = "http://104.236.239.37:8080/v1/job/createjob?ownerID=" + ownerID;
                    addJobWebservice.callService(params);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


        }
    }

    public void imagePath(String realPath) {


    }


    @Override
    public void onBackPressed() {
        findViewById(R.id.btnClose_AddNewProject).performClick();
    }

    protected Gson getGson() {

        return gson;
    }

    @Override
    public void AddJobWebserviceStart() {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

    }

    @Override
    public void AddJobWebserviceSucessful(String response, String message) {
//        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        Log.d("Add New Job", response);
        progressDialog.dismiss();
        finish();


    }

    @Override
    public void AddJobWebserviceFailedWithMessage(String message) {
        Toast.makeText(this, "Fail:" + message, Toast.LENGTH_SHORT).show();
        Log.d("Add New Job", message);
        progressDialog.dismiss();

    }
}
