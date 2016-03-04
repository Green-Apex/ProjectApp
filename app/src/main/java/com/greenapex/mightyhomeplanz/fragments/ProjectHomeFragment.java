package com.greenapex.mightyhomeplanz.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.greenapex.R;
import com.greenapex.Request.models.AssignJobRequest;
import com.greenapex.Utils.Constants;
import com.greenapex.mightyhomeplanz.AddMilestone;
import com.greenapex.mightyhomeplanz.AddSow;
import com.greenapex.mightyhomeplanz.ShowMileStone;
import com.greenapex.mightyhomeplanz.entities.MileStoneModel;
import com.greenapex.response.models.JobDetailResponse;
import com.greenapex.response.models.MMListResponse;
import com.greenapex.webservice.AssignjobToMMWebservice;
import com.greenapex.webservice.ChangeJobStatusWebservice;
import com.greenapex.webservice.GetJobDetailByJobIdWebservice;
import com.greenapex.webservice.GetMMListWebservice;
import com.greenapex.webservice.UpdateJobDescriptionWebservice;
import com.greenapex.widgets.CustomTextView;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class ProjectHomeFragment extends BaseFragment implements OnClickListener {
    View view;
    Activity activity;

    static final int NUM_ITEMS = 6;
    ImageFragmentPagerAdapter imageFragmentPagerAdapter;
    ViewPager viewPager;
    CustomTextView tvStatus, tvReview;
    ImageButton btnAddMilestone;

    //public static final String[] IMAGE_NAME = {"eagle", "horse", "bonobo", "wolf", "owl", "bear",};
    private static final ArrayList<String> IMAGE_NAME = new ArrayList<>();
    private CustomTextView customTxtAssignJob;
    private CustomTextView tvMakePayment_fragProjectHome;
    private String selectJobID;
    private JobDetailResponse jobDetailResponse = new JobDetailResponse();
    private CustomTextView customTxtJobDescription;
    private CustomTextView customTxtStartDate;
    private CirclePageIndicator titleIndicator;
    private CustomTextView customTxtEditJobDescription;
    private View imgChangestatus;
    private ListView listMilestone;

    public static Fragment newInstance(String jobID) {

        ProjectHomeFragment f = new ProjectHomeFragment();
        Bundle params = new Bundle();
        params.putString(Constants.JOBID, jobID);
        f.setArguments(params);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_project_home, container, false);

        selectJobID = getArguments().getString(Constants.JOBID, "");
        activity = getActivity();

        init();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            //Only manually call onResume if fragment is already visible
            //Otherwise allow natural fragment lifecycle to call onResume
            onResume();
        }


    }
    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint())
            getJobDetail(selectJobID);
    }
    private void getJobDetail(String selectJobID) {
        GetJobDetailByJobIdWebservice getJobDetailByJobIdWebservice = new GetJobDetailByJobIdWebservice(new GetJobDetailByJobIdWebservice.GetJobDetailByJobIdWebserviceHandler() {
            @Override
            public void getJobDetailByJobIdWebserviceStart() {

            }

            @Override
            public void getJobDetailByJobIdWebserviceSucessful(String response, String message) {
                if (response != null && response.length() > 0) {
                    jobDetailResponse = getGson().fromJson(response, JobDetailResponse.class);
                }
                showLog(response);
                loadData();
               // init();
            }

            @Override
            public void getJobDetailByJobIdWebserviceFailedWithMessage(String message) {
                showToast(message);
                if (getActivity().getSupportFragmentManager().getFragments().size() > 0)
                    getActivity().getSupportFragmentManager().popBackStack();
            }
        }, getActivity());

        try {
            JSONObject params = new JSONObject();
            params.put(Constants.JOBID, selectJobID);
            getJobDetailByJobIdWebservice.callService(params, Constants.METHOD_GET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void init() {

        viewPager = (ViewPager) view.findViewById(R.id.pager_fragProjectHome);


        titleIndicator = (CirclePageIndicator) view
                .findViewById(R.id.pagerIndicator_fragProjectHome);


        customTxtStartDate = (CustomTextView) view.findViewById(R.id.customTxtStartDate);
        customTxtJobDescription = (CustomTextView) view.findViewById(R.id.customTxtJobDescription);
        customTxtEditJobDescription = (CustomTextView) view.findViewById(R.id.customTxtEditJobDescription);
        customTxtEditJobDescription.setOnClickListener(this);

        tvStatus = (CustomTextView) view.findViewById(R.id.tvStatus_fragProjectHome);
        tvStatus.setOnClickListener(this);
        tvReview = (CustomTextView) view.findViewById(R.id.tvReview_fragProjectHome);
        tvReview.setOnClickListener(this);
        customTxtAssignJob = (CustomTextView) view.findViewById(R.id.customTxtAssignJob);
        customTxtAssignJob.setOnClickListener(this);

        btnAddMilestone = (ImageButton) view.findViewById(R.id.btnAddMilestone_fragProjectHome);
        btnAddMilestone.setOnClickListener(this);
        tvMakePayment_fragProjectHome = (CustomTextView) view.findViewById(R.id.tvMakePayment_fragProjectHome);
        tvMakePayment_fragProjectHome.setOnClickListener(this);
        imgChangestatus = view.findViewById(R.id.imgChangestatus);
        imgChangestatus.setOnClickListener(this);
        listMilestone = (ListView) view.findViewById(R.id.listMilestone);

        // loadData();
    }

    private void loadData() {
        final ArrayList<String> arrMileStone = new ArrayList<>();
        for (MileStoneModel mileStone :
                jobDetailResponse.getMilestone()) {
            arrMileStone.add(mileStone.getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arrMileStone);
        listMilestone.setAdapter(adapter);
        listMilestone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showMileStone = new Intent(getActivity(), ShowMileStone.class);
                showMileStone.putExtra(Constants.MILESTONE, jobDetailResponse.getMilestone().get(position).toString());
                startActivity(showMileStone);
            }
        });
        IMAGE_NAME.clear();
        if (jobDetailResponse.getImages().size() > 0) {

            IMAGE_NAME.addAll(jobDetailResponse.getImages());

        } else {
            IMAGE_NAME.add(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResources().getResourcePackageName(R.drawable.noimage) + '/' + getResources().getResourceTypeName(R.drawable.noimage) + '/' + getResources().getResourceEntryName(R.drawable.noimage));
        }
        imageFragmentPagerAdapter = new ImageFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(imageFragmentPagerAdapter);
        titleIndicator.setViewPager(viewPager);



        if (jobDetailResponse.getJobStatus().equalsIgnoreCase(Constants.NEW)) {
            tvStatus.setText(Constants.NEW);
        } else if (jobDetailResponse.getJobStatus().equalsIgnoreCase(Constants.ASSIGNED)) {
            tvStatus.setText(Constants.ASSIGNED);
        } else if (jobDetailResponse.getJobStatus().equalsIgnoreCase(Constants.COMPLETED)) {
            tvStatus.setText(Constants.COMPLETED);
        } else if (jobDetailResponse.getJobStatus().equalsIgnoreCase(Constants.INPROGRESS)) {
            tvStatus.setText(Constants.INPROGRESS);
        } else if (jobDetailResponse.getJobStatus().equalsIgnoreCase(Constants.REJECTED)) {
            tvStatus.setText(Constants.REJECTED);
        } else if (jobDetailResponse.getJobStatus().equalsIgnoreCase(Constants.UNDER_ESTIMATION)) {
            tvStatus.setText(Constants.UNDERESTIMATION);
        } else if (jobDetailResponse.getJobStatus().equalsIgnoreCase(Constants.REQUESTED_FOR_PAYMENT)) {
            tvStatus.setText(Constants.REQUESTED_FOR_PAYMENT);
        }else if (jobDetailResponse.getJobStatus().equalsIgnoreCase(Constants.OWNER_REVIEW)) {
            tvStatus.setText(Constants.OWNERREVIEW);
        }

        customTxtJobDescription.setText(jobDetailResponse.getJobDescription());
        customTxtStartDate.setText("Start Date : " + jobDetailResponse.getJobCreationDate());

        if (getUserGson().getRole().equalsIgnoreCase(Constants.OWNER) && jobDetailResponse.getJobStatus().equalsIgnoreCase(Constants.UNDER_ESTIMATION)) {
            tvMakePayment_fragProjectHome.setVisibility(View.VISIBLE);
            customTxtAssignJob.setVisibility(View.GONE);
            btnAddMilestone.setVisibility(View.GONE);
            customTxtEditJobDescription.setVisibility(View.VISIBLE);
        } else if (getUserGson().getRole().equalsIgnoreCase(Constants.PM) && jobDetailResponse.getJobStatus().equalsIgnoreCase(Constants.NEW)) {
            tvMakePayment_fragProjectHome.setVisibility(View.GONE);
            customTxtAssignJob.setVisibility(View.VISIBLE);
            btnAddMilestone.setVisibility(View.GONE);
            customTxtEditJobDescription.setVisibility(View.GONE);
        } else if (getUserGson().getRole().equalsIgnoreCase(Constants.MM) && jobDetailResponse.getJobStatus().equalsIgnoreCase(Constants.ASSIGNED)) {
            tvMakePayment_fragProjectHome.setVisibility(View.GONE);
            customTxtAssignJob.setVisibility(View.GONE);
           // btnAddMilestone.setVisibility(View.VISIBLE);
            customTxtEditJobDescription.setVisibility(View.GONE);
        } else {
            tvMakePayment_fragProjectHome.setVisibility(View.GONE);
            customTxtAssignJob.setVisibility(View.GONE);
            btnAddMilestone.setVisibility(View.GONE);
            customTxtEditJobDescription.setVisibility(View.GONE);
        }
        if (getUserGson().getRole().equalsIgnoreCase(Constants.OWNER) && (jobDetailResponse.getJobStatus().equalsIgnoreCase(Constants.NEW) || jobDetailResponse.getJobStatus().equalsIgnoreCase(Constants.ASSIGNED))) {
            customTxtEditJobDescription.setVisibility(View.VISIBLE);
        }
        if (getUserGson().getRole().equalsIgnoreCase(Constants.MM) && jobDetailResponse.getJobStatus().equalsIgnoreCase(Constants.UNDER_ESTIMATION)) {
            customTxtAssignJob.setVisibility(View.GONE);
            tvReview.setVisibility(View.VISIBLE);
        }
        if(getUserGson().getRole().equalsIgnoreCase(Constants.MM))
            imgChangestatus.setVisibility(View.VISIBLE);
        else
            imgChangestatus.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {
            case R.id.imgChangestatus: {
                if (getUserGson().getRole().equalsIgnoreCase(Constants.MM))
                    showStatusList();
                break;
            }
            case R.id.customTxtAssignJob:

                showMMList();

                //if(PM) then Assign Job
                //IF MM then Add Sow
                //IF owner and SOW added then make payment
                //TODO://AssignJob
                break;

            case R.id.tvStatus_fragProjectHome:
                if (getUserGson().getRole().equalsIgnoreCase(Constants.MM))
                    showStatusList();
//                activity.startActivity(new Intent(activity, ChangeStatus.class));
//                activity.overridePendingTransition(R.anim.effect_box, R.anim.nothing);
                break;

            case R.id.tvReview_fragProjectHome:
                //addSOW();
                Intent addSow = new Intent(activity, AddSow.class);
                addSow.putExtra(Constants.JOBID, selectJobID);
                activity.startActivityForResult(addSow, Constants.ADDSOW);
                activity.overridePendingTransition(R.anim.effect_box, R.anim.nothing);
                break;

            case R.id.btnAddMilestone_fragProjectHome:
                activity.startActivity(new Intent(activity, AddMilestone.class));
                activity.overridePendingTransition(R.anim.effect_box, R.anim.nothing);
                break;
            case R.id.customTxtEditJobDescription: {
                editJobDescription();
                break;
            }
        }
    }

    private void showMMList() {
        GetMMListWebservice getMMListWebservice = new GetMMListWebservice(new GetMMListWebservice.GetMMListWebserviceHandler() {
            @Override
            public void getMMListWebserviceStart() {

            }

            @Override
            public void getMMListWebserviceSucessful(String response, String message) {
                ArrayList<MMListResponse> arrMM = new ArrayList<>();
                if (response != null && response.length() > 0) {
                    try {
                        JSONArray mResponse = new JSONArray(response);
                        if (mResponse != null && mResponse.length() > 0) {

                            for (int i = 0; i < mResponse.length(); i++) {
                                MMListResponse mmListResponse = new MMListResponse();
                                mmListResponse = getGson().fromJson(mResponse.get(i).toString(), MMListResponse.class);
                                arrMM.add(mmListResponse);

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                showLog(message);
                showMMList(arrMM);
            }

            @Override
            public void getMMListWebserviceFailedWithMessage(String message) {

            }
        }, getActivity());

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

    private void editJobDescription() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        final EditText edittext = new EditText(getActivity());
        //alert.setMessage("");
        alert.setTitle("Job Description");
        edittext.setText(customTxtJobDescription.getText());
        alert.setView(edittext);

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String jobDescription = edittext.getText().toString();
                customTxtJobDescription.setText(jobDescription);
                UpdateJobDescriptionToServer(jobDescription);
                //showToast(jobDescription);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                dialog.dismiss();
            }
        });

        alert.show();
    }

    private void UpdateJobDescriptionToServer(String jobDescription) {
        UpdateJobDescriptionWebservice updateJobDescriptionWebservice = new UpdateJobDescriptionWebservice(new UpdateJobDescriptionWebservice.UpdateJobDescriptionWebserviceHandler() {
            @Override
            public void updateJobDescriptionWebserviceStart() {

            }

            @Override
            public void updateJobDescriptionWebserviceSucessful(String response, String message) {

                if (response != null && response.length() > 0) {
                    jobDetailResponse = getGson().fromJson(response, JobDetailResponse.class);
                }

            }

            @Override
            public void updateJobDescriptionWebserviceFailedWithMessage(String message) {
                customTxtJobDescription.setText(jobDetailResponse.getJobDescription());
                showToast(message);
            }
        }, getActivity());
        JSONObject params = new JSONObject();
        try {
            params.put(Constants.JOBID, jobDetailResponse.getJobID());
            params.put(Constants.USERID, getUserGson().getUserID());
            params.put("jobDescription", jobDescription);
            updateJobDescriptionWebservice.callService(params, Constants.METHOD_POST);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private void addSOW() {

    }

    private void showMMList(final ArrayList<MMListResponse> arrMM) {
        final ArrayList<String> arrNameList = new ArrayList<>();
        for (int i = 0; i < arrMM.size(); i++) {
            String mmName = arrMM.get(i).getFname() + " " + arrMM.get(i).getLname();
            arrNameList.add(mmName);
        }

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        builderSingle.setIcon(R.mipmap.ic_launcher);
        builderSingle.setTitle("Please Select :");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.addAll(arrNameList);

        builderSingle.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        String mmID = arrMM.get(which).getUserID();

                        AssignJobToMM(mmID);

                    }
                });
        builderSingle.show();
    }

    private void showStatusList() {
        final ArrayList<String> arrNameList = new ArrayList<>();

        arrNameList.add(Constants.UNDERESTIMATION);
        arrNameList.add(Constants.INPROGRESS);
        arrNameList.add(Constants.COMPLETED);
        arrNameList.add(Constants.CANCEL);

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        builderSingle.setIcon(R.mipmap.ic_launcher);
        builderSingle.setTitle("Please Select :");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.addAll(arrNameList);

        builderSingle.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String statusSelected = "";
                        switch (which) {
                            case 0: {
                                statusSelected = "UNDER_ESTIMATION";
                                break;
                            }
                            case 1: {
                                statusSelected = "INPROGRESS";
                                break;
                            }
                            case 2: {
                                statusSelected = "COMPLETED";
                                break;
                            }
                            case 3: {
                                statusSelected = "CANCEL";
                                break;
                            }
                        }

                        changeJobStatus(statusSelected);

                    }
                });
        builderSingle.show();
    }

    private void changeJobStatus(String statusSelected) {
        ChangeJobStatusWebservice changeJobStatusWebservice = new ChangeJobStatusWebservice(new ChangeJobStatusWebservice.ChangeJobStatusWebserviceHandler() {
            @Override
            public void changeJobStatusWebserviceStart() {

            }

            @Override
            public void changeJobStatusWebserviceSucessful(String response, String message) {
                showToast(response);
                tvReview.setVisibility(View.VISIBLE);
            }

            @Override
            public void changeJobStatusWebserviceFailedWithMessage(String message) {
                showToast(message);
            }
        }, getActivity());

        try {
            JSONObject params = new JSONObject();
            params.put(Constants.JOBID, jobDetailResponse.getJobID());
            params.put(Constants.USERID, getUserGson().getUserID());
            params.put(Constants.JOBSTATUS, statusSelected);
            changeJobStatusWebservice.callService(params, Constants.METHOD_POST);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void AssignJobToMM(String mmID) {
//        customTxtAssignJob.setVisibility(View.GONE);
//        tvReview.setVisibility(View.VISIBLE);
//        return;
        AssignjobToMMWebservice assignjobToMMWebservice = new AssignjobToMMWebservice(new AssignjobToMMWebservice.AssignjobToMMWebserviceHandler() {
            @Override
            public void assignjobToMMWebserviceStart() {

            }

            @Override
            public void assignjobToMMWebserviceSucessful(String response, String message) {
                showLog(response);
                showToast(message);
                customTxtAssignJob.setVisibility(View.GONE);
            }

            @Override
            public void assignjobToMMWebserviceFailedWithMessage(String message) {
                showToast(message);
            }
        }, getActivity());

        try {
            AssignJobRequest assignJobRequest = new AssignJobRequest();
            assignJobRequest.setMmID(mmID);
            assignJobRequest.setRole(getUserGson().getRole());
            assignJobRequest.setJobID(selectJobID);
            assignJobRequest.setPmID(getUserGson().getUserID());
            JSONObject params = new JSONObject(assignJobRequest.toString());
            assignjobToMMWebservice.callService(params, Constants.METHOD_POST);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static class ImageFragmentPagerAdapter extends FragmentPagerAdapter {
        public ImageFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return IMAGE_NAME.size();
        }

        @Override
        public Fragment getItem(int position) {
            SwipeFragment fragment = new SwipeFragment();
            return SwipeFragment.newInstance(position);
        }
    }

    public static class SwipeFragment extends BaseFragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View swipeView = inflater.inflate(R.layout.swipe_fragment, container, false);
            ImageView imageView = (ImageView) swipeView.findViewById(R.id.imageView);
            Bundle bundle = getArguments();
            int position = bundle.getInt("position");
            String imageFileName = IMAGE_NAME.get(position);
            //int imgResId = getResources().getIdentifier(imageFileName, "drawable", "com.greenapex");
            if (!imageFileName.contains("noimage"))
                getImageLoader().load(Constants.BaseImageDomain + imageFileName).into(imageView);
            else {
                getImageLoader().load(Uri.parse(imageFileName)).into(imageView);
            }

//            imageView.setImageURI();
            return swipeView;
        }

        static SwipeFragment newInstance(int position) {
            SwipeFragment swipeFragment = new SwipeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            swipeFragment.setArguments(bundle);
            return swipeFragment;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.ADDSOW: {
                if (resultCode == Activity.RESULT_OK) {
                    getJobDetail(selectJobID);
                }
                break;
            }
        }
    }
}
