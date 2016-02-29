package com.greenapex.mightyhomeplanz.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.greenapex.R;
import com.greenapex.Request.models.AssignJobRequest;
import com.greenapex.Utils.Constants;
import com.greenapex.mightyhomeplanz.AddMilestone;
import com.greenapex.mightyhomeplanz.AddSow;
import com.greenapex.mightyhomeplanz.ChangeStatus;
import com.greenapex.response.models.JobDetailResponse;
import com.greenapex.response.models.MMListResponse;
import com.greenapex.webservice.AssignjobToMMWebservice;
import com.greenapex.webservice.GetJobDetailByJobIdWebservice;
import com.greenapex.webservice.GetMMListWebservice;
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

    public static final String[] IMAGE_NAME = {"eagle", "horse", "bonobo", "wolf", "owl", "bear",};
    private CustomTextView customTxtAssignJob;
    private CustomTextView tvMakePayment_fragProjectHome;
    private String selectJobID;
    private JobDetailResponse jobDetailResponse = new JobDetailResponse();
    private CustomTextView customTxtJobDescription;
    private CustomTextView customTxtStartDate;

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
        imageFragmentPagerAdapter = new ImageFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.pager_fragProjectHome);
        viewPager.setAdapter(imageFragmentPagerAdapter);

        CirclePageIndicator titleIndicator = (CirclePageIndicator) view
                .findViewById(R.id.pagerIndicator_fragProjectHome);
        titleIndicator.setViewPager(viewPager);

        customTxtStartDate = (CustomTextView) view.findViewById(R.id.customTxtStartDate);
        customTxtJobDescription = (CustomTextView) view.findViewById(R.id.customTxtJobDescription);


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


       // loadData();
    }

    private void loadData() {
        if (jobDetailResponse.getJobStatus().isNew())
            tvStatus.setText(Constants.NEW);
        else if (jobDetailResponse.getJobStatus().isAssigned())
            tvStatus.setText(Constants.ASSIGNED);
        else if (jobDetailResponse.getJobStatus().isCompleted())
            tvStatus.setText(Constants.COMPLETED);
        else if (jobDetailResponse.getJobStatus().isInProgress())
            tvStatus.setText(Constants.INPROGRESS);
        else if (jobDetailResponse.getJobStatus().isRejected())
            tvStatus.setText(Constants.REJECTED);
        else if (jobDetailResponse.getJobStatus().isUnderEstimation())
            tvStatus.setText(Constants.UNDERESTIMATION);
        else if (jobDetailResponse.getJobStatus().isRequestedForPayment())
            tvStatus.setText(Constants.REQUESTED_FOR_PAYMENT);

        customTxtJobDescription.setText(jobDetailResponse.getJobDescription());
        customTxtStartDate.setText("Start Date : " + jobDetailResponse.getJobCreationDate());
        if (getUserGson().getRole().equalsIgnoreCase(Constants.OWNER) && jobDetailResponse.getJobStatus().isUnderEstimation()) {
            tvMakePayment_fragProjectHome.setVisibility(View.VISIBLE);
            customTxtAssignJob.setVisibility(View.GONE);
            btnAddMilestone.setVisibility(View.GONE);
        } else if (getUserGson().getRole().equalsIgnoreCase(Constants.PM) && jobDetailResponse.getJobStatus().isNew()) {
            tvMakePayment_fragProjectHome.setVisibility(View.GONE);
            customTxtAssignJob.setVisibility(View.VISIBLE);
            btnAddMilestone.setVisibility(View.GONE);
        } else if (getUserGson().getRole().equalsIgnoreCase(Constants.MM) && jobDetailResponse.getJobStatus().isAssigned()) {
            tvMakePayment_fragProjectHome.setVisibility(View.GONE);
            customTxtAssignJob.setVisibility(View.GONE);
            btnAddMilestone.setVisibility(View.VISIBLE);
        } else {
            tvMakePayment_fragProjectHome.setVisibility(View.GONE);
            customTxtAssignJob.setVisibility(View.GONE);
            btnAddMilestone.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {
            case R.id.customTxtAssignJob:
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
                    params.put(Constants.ROLE, getUserGson().getRole());
                    getMMListWebservice.callService(params, Constants.METHOD_GET);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                //if(PM) then Assign Job
                //IF MM then Add Sow
                //IF owner and SOW added then make payment
                //TODO://AssignJob
                break;

            case R.id.tvStatus_fragProjectHome:
                activity.startActivity(new Intent(activity, ChangeStatus.class));
                activity.overridePendingTransition(R.anim.effect_box, R.anim.nothing);
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
        builderSingle.setIcon(R.drawable.ic_launcher);
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
                        String mmID = arrMM.get(which).getMmID();

                        AssignJobToMM(mmID);
//                        AlertDialog.Builder builderInner = new AlertDialog.Builder(
//                                getActivity());
//                        builderInner.setMessage(strName + " MMID: " + mmID);
//                        builderInner.setTitle("Your Selected Item is");
//                        builderInner.setPositiveButton(
//                                "Ok",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(
//                                            DialogInterface dialog,
//                                            int which) {
//                                        dialog.dismiss();
//                                    }
//                                });
//                        builderInner.show();
                    }
                });
        builderSingle.show();
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
                customTxtAssignJob.setVisibility(View.GONE);
                tvReview.setVisibility(View.VISIBLE);
                showToast(message);
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
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            SwipeFragment fragment = new SwipeFragment();
            return SwipeFragment.newInstance(position);
        }
    }

    public static class SwipeFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View swipeView = inflater.inflate(R.layout.swipe_fragment, container, false);
            ImageView imageView = (ImageView) swipeView.findViewById(R.id.imageView);
            Bundle bundle = getArguments();
            int position = bundle.getInt("position");
            String imageFileName = IMAGE_NAME[position];
            int imgResId = getResources().getIdentifier(imageFileName, "drawable", "com.acrobat.mightyhomeplanz");
            imageView.setImageResource(imgResId);
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
