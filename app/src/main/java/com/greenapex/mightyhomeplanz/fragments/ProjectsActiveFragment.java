package com.greenapex.mightyhomeplanz.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greenapex.R;
import com.greenapex.Request.models.OwnerIdRequest;
import com.greenapex.Utils.Constants;
import com.greenapex.mightyhomeplanz.adapters.ProjectsAdapter;
import com.greenapex.mightyhomeplanz.entities.JobModel;
import com.greenapex.webservice.GetActiveJobWebservice;
import com.greenapex.widgets.CustomSwipeRefreshLayout;
import com.greenapex.widgets.CustomSwipeRefreshLayout.OnChildScrollUpListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ProjectsActiveFragment extends BaseFragment {
    private static Gson gson;
    private static ProgressDialog mProgressDialog;
    private static ArrayList<JobModel> list = new ArrayList<>();
    View view;
    Activity activity;
    CustomSwipeRefreshLayout swipeRefresh;

    //    ArrayList<ProjectModel> list = new ArrayList<ProjectModel>();
    // ProjectsAdapter adapter;

    private ProjectsAdapter adapter;
    private ListView lv;
    private TextView txtNoData;

    public static Fragment newInstance(int position) {
        ProjectsActiveFragment f = new ProjectsActiveFragment();
        Bundle b = new Bundle();
        b.putInt("position", position);
        f.setArguments(b);
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_projects, container, false);


        activity = getActivity();
        int mPosition = 0;
        if (getArguments() != null) {
            mPosition = getArguments().getInt("position");
        }
        init();
        return view;
    }

    public void init() {
        mProgressDialog = new ProgressDialog(activity);
        gson = new GsonBuilder().create();
        swipeRefresh = (CustomSwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout_fragNewProjects);
        swipeRefresh.setColorSchemeResources(R.color.app_color, R.color.purple);
        swipeRefresh.setProgressViewOffset(false, 0, 100);
        txtNoData = (TextView) view.findViewById(R.id.txtNoData);
        lv = (ListView) view.findViewById(R.id.lv_fragNewProjects);

        swipeRefresh.setOnChildScrollUpListener(new OnChildScrollUpListener() {
            @Override
            public boolean canChildScrollUp() {
                return lv.getFirstVisiblePosition() > 0 || lv.getChildAt(0) == null || lv.getChildAt(0).getTop() < 0;
            }
        });


//        adapter = new ProjectsAdapter(activity, list, getSelectedPosition());
//        loadData(lv, adapter);
        swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                loadData();
                lv.invalidate();
                adapter.notifyDataSetChanged();
            }
        });

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
            loadData();
    }

    public void loadData() {

        getJobs();


    }

    private void getJobs() {

//        list.clear();
//        list.add(new JobModel());
//        list.add(new JobModel());
//        showlistView();
        getactivejoblistbyuserID();


    }


    private void showlistView() {
        if (list.size() > 0) {
            txtNoData.setText("");
            txtNoData.setVisibility(View.GONE);
        } else {
            txtNoData.setText("No Job Found!");
        }
        if (lv != null) {
            lv.setAdapter(null);
            adapter = new ProjectsAdapter(activity, list, getImageLoader());
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    JobModel selectedJobItem = (JobModel) parent.getItemAtPosition(position);
                    Bundle params = new Bundle();
                    params.putString(Constants.JOBID, selectedJobItem.getJobID());
                    Fragment projectFragment = new ProjectDetailFragment();
                    projectFragment.setArguments(params);
                    FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction mFragmentTransaction;

                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.containerView, projectFragment).addToBackStack("SS")
                            .commit();
                }
            });
            lv.invalidate();
            adapter.notifyDataSetChanged();
        }

    }


    private void getactivejoblistbyuserID() {
        try {
            GetActiveJobWebservice getActiveJobWebservice = new GetActiveJobWebservice(new GetActiveJobWebservice.GetActiveJobWebserviceHandler() {
                @Override
                public void GetActiveJobWebserviceStart() {

                }

                @Override
                public void GetActiveJobWebserviceSucessful(String response, String message) {
                    parseJobList(response, message);

                }

                @Override
                public void GetActiveJobWebserviceFailedWithMessage(String message) {
                    showToast(message);
                }
            }, activity);
            OwnerIdRequest ownerIdRequest = new OwnerIdRequest();
            ownerIdRequest.setUserID(getUserGson().getUserID());


            JSONObject params = new JSONObject(ownerIdRequest.toString());

            getActiveJobWebservice.callService(params, Constants.METHOD_GET);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }



    private void parseJobList(String response, String message) {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
        swipeRefresh.setRefreshing(false);
        Log.d("Success", message);
        if (response != null && response.length() > 0) {
            try {
                JSONArray mResponse = new JSONArray(response);
                list.clear();
                if (mResponse != null && mResponse.length() > 0) {
                    for (int i = 0; i < mResponse.length(); i++) {
                        JobModel jobitem = getGson().fromJson(mResponse.get(i).toString(), JobModel.class);
                        list.add(jobitem);
                    }
                    showlistView();
                } else {
                    //utils.showToast(message);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                txtNoData.setText("No Job Found");
               // showToast(message);
            }
        }
    }


}
