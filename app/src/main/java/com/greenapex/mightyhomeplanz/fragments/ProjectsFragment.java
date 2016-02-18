package com.greenapex.mightyhomeplanz.fragments;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greenapex.Request.models.OwnerIdRequest;
import com.greenapex.Utils.Constants;
import com.greenapex.Utils.Utils;
import com.greenapex.mightyhomeplanz.adapters.ProjectsAdapter;
import com.greenapex.mightyhomeplanz.entities.ProjectModel;
import com.greenapex.webservice.GetActiveJobWebservice;
import com.greenapex.webservice.GetCompletedJobWebservice;
import com.greenapex.webservice.GetNewJobWebservice;
import com.greenapex.widgets.CustomSwipeRefreshLayout;
import com.greenapex.widgets.CustomSwipeRefreshLayout.OnChildScrollUpListener;
import com.greenapex.R;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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

import org.json.JSONException;
import org.json.JSONObject;

public class ProjectsFragment extends Fragment {
    private static Gson gson;
    private static ProgressDialog mProgressDialog;
    private static ArrayList<ProjectModel> list = new ArrayList<>();
    View view;
    Activity activity;
    CustomSwipeRefreshLayout swipeRefresh;


    //    ArrayList<ProjectModel> list = new ArrayList<ProjectModel>();
    // ProjectsAdapter adapter;

    int mPosition;
    private Utils utils;

    public static Fragment newInstance(int position) {
        ProjectsFragment f = new ProjectsFragment();
        Bundle b = new Bundle();
        b.putInt("position", position);
        f.setArguments(b);
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_projects, container, false);
        activity = getActivity();
        utils = new Utils(activity);
        if (getArguments() != null) {
            mPosition = getArguments().getInt("position");
            //list = getArguments().getParcelableArrayList("list");
            Utils.showLog(getClass().getSimpleName(), "Position: " + mPosition);
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

        final ListView lv = (ListView) view.findViewById(R.id.lv_fragNewProjects);

        swipeRefresh.setOnChildScrollUpListener(new OnChildScrollUpListener() {
            @Override
            public boolean canChildScrollUp() {
                return lv.getFirstVisiblePosition() > 0 || lv.getChildAt(0) == null || lv.getChildAt(0).getTop() < 0;
            }
        });


        final ProjectsAdapter adapter = new ProjectsAdapter(activity, list, mPosition);
        loadData(lv, adapter);
        swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                loadData(lv, adapter);
                lv.invalidate();
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void loadData(ListView lv, ProjectsAdapter adapter) {
        list.clear();
        for (int i = 0; i < 1; i++) {
            ProjectModel data = new ProjectModel();
            data.setId("" + (i + 1));
            list.add(data);
        }

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction mFragmentTransaction;

                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new ProjectDetailFragment()).addToBackStack("SS")
                        .commit();
            }
        });
//        SharedPreferences sp = getActivity().getSharedPreferences(Constants.mightyHomePlanz, Context.MODE_PRIVATE);
//        String userGson = sp.getString(Constants.UserData, "");
//        String ownerID = "";
//        if (userGson.length() > 0) {
//            UserResponse userResponse = gson.fromJson(userGson, UserResponse.class);
//            ownerID = userResponse.getOwnerID();
//        }
//        if (!ownerID.equalsIgnoreCase("")) {
//            //ArrayList<ProjectModel> list = new ArrayList<>();
//            switch (mPosition) {
//                case 0: {
//                    Getnewjoblistbyownerid(ownerID);
//                    list.clear();
//                    for (int i = 0; i < 1; i++) {
//                        ProjectModel data = new ProjectModel();
//                        data.setId("" + (i + 1));
//                        list.add(data);
//                    }
//
//                    break;
//                }
//                case 1: {
//                    Getactivejoblistbyowner(ownerID);
//                    list.clear();
//                    for (int i = 0; i < 2; i++) {
//                        ProjectModel data = new ProjectModel();
//                        data.setId("" + (i + 1));
//                        list.add(data);
//                    }
//
//                    break;
//                }
//                case 2: {
//                    Getcompletedjoblistbyowner(ownerID);
//                    list.clear();
//                    for (int i = 0; i < 3; i++) {
//                        ProjectModel data = new ProjectModel();
//                        data.setId("" + (i + 1));
//                        list.add(data);
//                    }
//
//                    break;
//                }
//            }


//        }


        //  Gettotaljobsbyowner(ownerID);
    }


    private void Getcompletedjoblistbyowner(String ownerID) {
        try {
            GetCompletedJobWebservice getCompletedJobWebservice = new GetCompletedJobWebservice(new GetCompletedJobWebservice.GetCompletedJobWebserviceHandler() {
                @Override
                public void GetCompletedJobWebserviceStart() {
                    mProgressDialog.setMessage("loading...");
                    if (!mProgressDialog.isShowing()) mProgressDialog.show();
                }

                @Override
                public void GetCompletedJobWebserviceSucessful(String response, String message) {
                    if (mProgressDialog.isShowing()) mProgressDialog.hide();
                    Log.d("Success", message);
                    if (response != null && response.length() > 0) {


                    }


                }

                @Override
                public void GetCompletedJobWebserviceFailedWithMessage(String message) {
                    if (mProgressDialog.isShowing()) mProgressDialog.hide();
                }
            }, activity);
            OwnerIdRequest ownerIdRequest = new OwnerIdRequest();
            ownerIdRequest.setOwnerID(ownerID);


            JSONObject params = new JSONObject(ownerIdRequest.toString());
            getCompletedJobWebservice.callService(params, Constants.METHOD_GET);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void Getactivejoblistbyowner(String ownerID) {
        try {
            GetActiveJobWebservice getActiveJobWebservice = new GetActiveJobWebservice(new GetActiveJobWebservice.GetActiveJobWebserviceHandler() {
                @Override
                public void GetActiveJobWebserviceStart() {
                    mProgressDialog.setMessage("loading...");
                    if (!mProgressDialog.isShowing()) mProgressDialog.show();
                }

                @Override
                public void GetActiveJobWebserviceSucessful(String response, String message) {
                    if (mProgressDialog.isShowing()) mProgressDialog.hide();
                    Log.d("Success", message);
                    if (response != null && response.length() > 0) {


                    }

                }

                @Override
                public void GetActiveJobWebserviceFailedWithMessage(String message) {
                    if (mProgressDialog.isShowing()) mProgressDialog.hide();
                }
            }, activity);
            OwnerIdRequest ownerIdRequest = new OwnerIdRequest();
            ownerIdRequest.setOwnerID(ownerID);


            JSONObject params = new JSONObject(ownerIdRequest.toString());

            getActiveJobWebservice.callService(params, Constants.METHOD_GET);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private void Getnewjoblistbyownerid(String ownerID) {
        try {
            GetNewJobWebservice getNewJobWebservice = new GetNewJobWebservice(new GetNewJobWebservice.GetNewJobWebserviceHandler() {
                @Override
                public void GetNewJobWebserviceStart() {
                    mProgressDialog.setMessage("loading...");
                    if (!mProgressDialog.isShowing()) mProgressDialog.show();
                }

                @Override
                public void GetNewJobWebserviceSucessful(String response, String message) {
                    if (mProgressDialog.isShowing()) mProgressDialog.hide();
                    Log.d("Success", message);
                    if (response != null && response.length() > 0) {


                    }

                }

                @Override
                public void GetNewJobWebserviceFailedWithMessage(String message) {
                    if (mProgressDialog.isShowing()) mProgressDialog.hide();
                }
            }, activity);

            OwnerIdRequest ownerIdRequest = new OwnerIdRequest();
            ownerIdRequest.setOwnerID(ownerID);

            JSONObject params = new JSONObject(ownerIdRequest.toString());

            getNewJobWebservice.callService(params, Constants.METHOD_GET);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

}
