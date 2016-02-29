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
import com.greenapex.webservice.GetNewJobWebservice;
import com.greenapex.widgets.CustomSwipeRefreshLayout;
import com.greenapex.widgets.CustomSwipeRefreshLayout.OnChildScrollUpListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ProjectsNewFragment extends BaseFragment {
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
        ProjectsNewFragment f = new ProjectsNewFragment();
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
//        if (!isViewShown)
//            loadData();
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

        getnewjoblistbyuserId();

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



    private void getnewjoblistbyuserId() {
        try {
            GetNewJobWebservice getNewJobWebservice = new GetNewJobWebservice(new GetNewJobWebservice.GetNewJobWebserviceHandler() {
                @Override
                public void GetNewJobWebserviceStart() {


                }

                @Override
                public void GetNewJobWebserviceSucessful(String response, String message) {
                    parseJobList(response, message);

                }

                @Override
                public void GetNewJobWebserviceFailedWithMessage(String message) {
                    showToast(message);
                }
            }, activity);

            OwnerIdRequest ownerIdRequest = new OwnerIdRequest();
            ownerIdRequest.setUserID(getUserGson().getUserID());

            JSONObject params = new JSONObject(ownerIdRequest.toString());

            getNewJobWebservice.callService(params, Constants.METHOD_GET);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }



//    private void getcompletedjoblistbyMMID() {
//        try {
//            GetCompletedJobForMMIDWebservice getCompletedJobForMMIDWebservice = new GetCompletedJobForMMIDWebservice(new GetCompletedJobForMMIDWebservice.GetCompletedJobForMMIDWebserviceHandler() {
//                @Override
//                public void getCompletedJobForMMIDWebserviceStart() {
//
//                }
//
//                @Override
//                public void getCompletedJobForMMIDWebserviceSucessful(String response, String message) {
//                    parseJobList(response, message);
//                }
//
//                @Override
//                public void getCompletedJobForMMIDWebserviceFailedWithMessage(String message) {
//                    showToast(message);
//                }
//            }, getActivity());
//
//
//            MMIdRequest mmIdRequest = new MMIdRequest();
//            mmIdRequest.setMmID(getUserGson().getMmID());
//            JSONObject params = new JSONObject(mmIdRequest.toString());
//            getCompletedJobForMMIDWebservice.callService(params, Constants.METHOD_GET);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void getactivejoblistbyMMID() {
//        try {
//            GetActiveJobForMMIDWebservice getActiveJobForMMIDWebservice = new GetActiveJobForMMIDWebservice(new GetActiveJobForMMIDWebservice.GetActiveJobForMMIDWebserviceHandler() {
//                @Override
//                public void getActiveJobForMMIDWebserviceStart() {
//
//                }
//
//                @Override
//                public void getActiveJobForMMIDWebserviceSucessful(String response, String message) {
//                    parseJobList(response, message);
//                }
//
//                @Override
//                public void getActiveJobForMMIDWebserviceFailedWithMessage(String message) {
//                    showToast(message);
//                }
//            }, getActivity());
//
//
//            MMIdRequest mmIdRequest = new MMIdRequest();
//            mmIdRequest.setMmID(getUserGson().getMmID());
//            JSONObject params = new JSONObject(mmIdRequest.toString());
//
//            getActiveJobForMMIDWebservice.callService(params, Constants.METHOD_GET);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void getnewjoblistbyMMID() {
//        try {
//            GetNewJobForMMIDWebservice getNewJobForMMIDWebservice = new GetNewJobForMMIDWebservice(new GetNewJobForMMIDWebservice.GetNewJobForMMIDWebserviceHandler() {
//                @Override
//                public void getNewJobForMMIDWebserviceStart() {
//
//                }
//
//                @Override
//                public void getNewJobForMMIDWebserviceSucessful(String response, String message) {
//                    parseJobList(response, message);
//                }
//
//                @Override
//                public void getNewJobForMMIDWebserviceFailedWithMessage(String message) {
//                    showToast(message);
//                }
//            }, getActivity());
//
//            MMIdRequest mmIdRequest = new MMIdRequest();
//            mmIdRequest.setMmID(getUserGson().getMmID());
//            JSONObject params = new JSONObject(mmIdRequest.toString());
//
//            getNewJobForMMIDWebservice.callService(params, Constants.METHOD_GET);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//
//    }

    private void parseJobList(String response, String message) {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
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
                //showToast(message);
                txtNoData.setText("No Job Found");
            }
        }
    }


}
