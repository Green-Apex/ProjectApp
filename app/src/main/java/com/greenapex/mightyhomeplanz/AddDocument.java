package com.greenapex.mightyhomeplanz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.greenapex.R;
import com.greenapex.Request.models.JobDOCModel;
import com.greenapex.Utils.Constants;
import com.greenapex.response.models.CommonResponse;
import com.greenapex.webservice.AddJobDocWebservice;
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

import cz.msebera.android.httpclient.Header;

public class AddDocument extends BaseActivity implements OnClickListener {

    Activity activity;
    ImageButton btnClose;
    CustomEditText etTitle;
    CustomTextView tvBrowse, tvSubmit, tvClear;
    private CustomTextView customFileName;
    private static File selectedfilePath;
    private static String selectedfilePathName;
    private ProgressDialog progressDialog;
    private static String uploadedFilePath;
    private static String JobID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_document);
        progressDialog = new ProgressDialog(this);
        activity = AddDocument.this;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            JobID = bundle.getString(Constants.JOBID);
        }
        init();
    }

    public void init() {
        btnClose = (ImageButton) findViewById(R.id.btnClose_AddDocument);
        etTitle = (CustomEditText) findViewById(R.id.etTitle_AddDocument);
        tvBrowse = (CustomTextView) findViewById(R.id.tvBrowse_AddDocument);
        tvSubmit = (CustomTextView) findViewById(R.id.tvSubmit_AddDocument);
        tvClear = (CustomTextView) findViewById(R.id.tvClear_AddDocument);
        customFileName = (CustomTextView) findViewById(R.id.customFileName);
        btnClose.setOnClickListener(this);
        tvBrowse.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        tvClear.setOnClickListener(this);
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {
            case R.id.btnClose_AddDocument:
                finish();
                overridePendingTransition(R.anim.nothing, R.anim.effect_box_back);
                break;

            case R.id.tvSubmit_AddDocument:
                AddJobDocWebService();

                overridePendingTransition(R.anim.nothing, R.anim.effect_box_back);
                break;

            case R.id.tvClear_AddDocument:
                etTitle.setText("");
                break;
            case R.id.tvBrowse_AddDocument:
                new FileChooser(activity).setFileListener(new FileChooser.FileSelectedListener() {
                    @Override
                    public void fileSelected(final File file) {
                        // do something with the file
                        selectedfilePath = file;
                        selectedfilePathName = selectedfilePath.getName();
                        customFileName.setText(selectedfilePathName);
                        UpdateImageToServer(selectedfilePath);
                        // Toast.makeText(AddSow.this, "file select +" + file.getName(), Toast.LENGTH_SHORT).show();
                    }
                }).showDialog();
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
                        uploadedFilePath = item.getAsString();

                    }

                } else {

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void AddJobDocWebService() {
        AddJobDocWebservice addJobDocWebservice = new AddJobDocWebservice(new AddJobDocWebservice.AddJobDocWebserviceHandler() {
            @Override
            public void addJobDocWebserviceStart() {

            }

            @Override
            public void addJobDocWebserviceSucessful(String response, String message) {
                showLog(message);
                showToast(message);
                finish();
            }

            @Override
            public void addJobDocWebserviceFailedWithMessage(String message) {
                showLog(message);
                showToast(message);
            }
        }, this);

        try {
            JobDOCModel jobDOCModel = new JobDOCModel();
            jobDOCModel.setDocTitle(etTitle.getText().toString());
            jobDOCModel.setUploadedUserID(getUserGson().getUserID());
            jobDOCModel.setDocPath(uploadedFilePath);
            JSONObject params = new JSONObject(jobDOCModel.toString());
            params.put(Constants.USERID, getUserGson().getUserID());
            params.put(Constants.JOBID, JobID);
            addJobDocWebservice.callService(params,Constants.METHOD_POST);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        findViewById(R.id.btnClose_AddDocument).performClick();
    }
}
