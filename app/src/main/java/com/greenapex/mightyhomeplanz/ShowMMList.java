package com.greenapex.mightyhomeplanz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.greenapex.R;
import com.greenapex.Request.models.AssignJobRequest;
import com.greenapex.Utils.Constants;
import com.greenapex.mightyhomeplanz.adapters.MMUserListAdapter;
import com.greenapex.response.models.MMListResponse;
import com.greenapex.webservice.AssignjobToMMWebservice;
import com.greenapex.webservice.GetMMListWebservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by admin on 08-Mar-16.by ${COMPUTERNAME}
 */
public class ShowMMList extends BaseActivity {
    private ListView listView;
    private ArrayList<MMListResponse> arrMMList = new ArrayList<>();
    private RequestManager imageloader;
    private ProgressDialog progressDialog;
    private String selectedJobId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmlist);
        setTitle("Select Mighty Manager");
        progressDialog = new ProgressDialog(this);
        Bundle params = getIntent().getExtras();
        if (params != null) {
            selectedJobId = params.getString(Constants.JOBID, "");
        }
        if (selectedJobId == null && selectedJobId.equalsIgnoreCase("")) {
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageloader = Glide.with(this);
        init();
        getmmList();
    }

    private void getmmList() {

        GetMMListWebservice getMMListWebservice = new GetMMListWebservice(new GetMMListWebservice.GetMMListWebserviceHandler() {
            @Override
            public void getMMListWebserviceStart() {

            }

            @Override
            public void getMMListWebserviceSucessful(String response, String message) {
                arrMMList.clear();
                if (response != null && response.length() > 0) {
                    try {
                        JSONArray mResponse = new JSONArray(response);
                        if (mResponse != null && mResponse.length() > 0) {

                            for (int i = 0; i < mResponse.length(); i++) {
                                MMListResponse mmListResponse = new MMListResponse();
                                mmListResponse = getGson().fromJson(mResponse.get(i).toString(), MMListResponse.class);
                                arrMMList.add(mmListResponse);

                            }
                            loaddata();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                showLog(message);

            }

            @Override
            public void getMMListWebserviceFailedWithMessage(String message) {

            }
        }, this);

        try {
            JSONObject params = new JSONObject();
            params.put(Constants.USERID, getUserGson().getUserID());
            getMMListWebservice.callService(params, Constants.METHOD_GET);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    private void loaddata() {

        MMUserListAdapter mmUserListAdapter = new MMUserListAdapter(this, arrMMList, imageloader);
        listView.setAdapter(mmUserListAdapter);


    }

    private void init() {
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MMListResponse mmListResponse = (MMListResponse) parent.getItemAtPosition(position);
                //showToast(mmListResponse.toString());
                AssignJobToMM(mmListResponse.getUserID());
            }
        });
    }

    private void AssignJobToMM(String mmID) {
//        customTxtAssignJob.setVisibility(View.GONE);
//        tvReview.setVisibility(View.VISIBLE);
//        return;
        AssignjobToMMWebservice assignjobToMMWebservice = new AssignjobToMMWebservice(new AssignjobToMMWebservice.AssignjobToMMWebserviceHandler() {
            @Override
            public void assignjobToMMWebserviceStart() {
                progressDialog.setMessage("loading...");
                progressDialog.show();
            }

            @Override
            public void assignjobToMMWebserviceSucessful(String response, String message) {
                progressDialog.dismiss();
                showLog(response);
                showToast(message);
                setResult(Activity.RESULT_OK);
                finish();
            }

            @Override
            public void assignjobToMMWebserviceFailedWithMessage(String message) {
                showToast(message);
                progressDialog.dismiss();
            }
        }, this);

        try {
            AssignJobRequest assignJobRequest = new AssignJobRequest();
            assignJobRequest.setMmID(mmID);
            assignJobRequest.setRole(getUserGson().getRole());
            assignJobRequest.setJobID(selectedJobId);
            assignJobRequest.setPmID(getUserGson().getUserID());
            JSONObject params = new JSONObject(assignJobRequest.toString());
            assignjobToMMWebservice.callService(params, Constants.METHOD_POST);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
