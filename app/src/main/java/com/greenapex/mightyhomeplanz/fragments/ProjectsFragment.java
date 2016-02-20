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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greenapex.R;
import com.greenapex.Request.models.MMIdRequest;
import com.greenapex.Request.models.OwnerIdRequest;
import com.greenapex.Utils.Constants;
import com.greenapex.Utils.Utils;
import com.greenapex.mightyhomeplanz.adapters.ProjectsAdapter;
import com.greenapex.mightyhomeplanz.entities.JobModel;
import com.greenapex.webservice.GetActiveJobForMMIDWebservice;
import com.greenapex.webservice.GetActiveJobForPMWebservice;
import com.greenapex.webservice.GetActiveJobWebservice;
import com.greenapex.webservice.GetCompletedJobForMMIDWebservice;
import com.greenapex.webservice.GetCompletedJobForPMWebservice;
import com.greenapex.webservice.GetCompletedJobWebservice;
import com.greenapex.webservice.GetNewJobForMMIDWebservice;
import com.greenapex.webservice.GetNewJobForPMWebservice;
import com.greenapex.webservice.GetNewJobWebservice;
import com.greenapex.widgets.CustomSwipeRefreshLayout;
import com.greenapex.widgets.CustomSwipeRefreshLayout.OnChildScrollUpListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ProjectsFragment extends BaseFragment {
    private static Gson gson;
    private static ProgressDialog mProgressDialog;
    private static ArrayList<JobModel> list = new ArrayList<>();
    View view;
    Activity activity;
    CustomSwipeRefreshLayout swipeRefresh;
    private int selectedPosition;

    //    ArrayList<ProjectModel> list = new ArrayList<ProjectModel>();
    // ProjectsAdapter adapter;

    private ProjectsAdapter adapter;
    private ListView lv;

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

    public void loadData() {

        getJobs();


    }

    private void getJobs() {


        switch (getSelectedPosition()) {
                case 0: {
                    if (getUserGson().getRole().equalsIgnoreCase(Constants.OWNER))
                        getnewjoblistbyownerid(getUserGson().getOwnerID());
                    else if (getUserGson().getRole().equalsIgnoreCase(Constants.PM))
                        getNewJobListforPM();
                    else if (getUserGson().getRole().equalsIgnoreCase(Constants.MM))
                        getnewjoblistbyMMID();



                    break;
                }
                case 1: {
                    if (getUserGson().getRole().equalsIgnoreCase(Constants.OWNER))
                        getactivejoblistbyowner(getUserGson().getOwnerID());
                    else if (getUserGson().getRole().equalsIgnoreCase(Constants.PM))
                        getActiveJobListforPM();
                    else if (getUserGson().getRole().equalsIgnoreCase(Constants.MM))
                        getactivejoblistbyMMID();

                    break;
                }
                case 2: {
                    if (getUserGson().getRole().equalsIgnoreCase(Constants.OWNER))
                        getcompletedjoblistbyowner(getUserGson().getOwnerID());
                    else if (getUserGson().getRole().equalsIgnoreCase(Constants.PM))
                        getCompletedJobListforPM();
                    else if (getUserGson().getRole().equalsIgnoreCase(Constants.PM))
                        getcompletedjoblistbyMMID();

                    break;
                }
            }


        }


    private void showlistView() {
        lv.setAdapter(null);
        adapter = new ProjectsAdapter(activity, list);
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
    }

    private void getcompletedjoblistbyowner(String ownerID) {
        try {
            GetCompletedJobWebservice getCompletedJobWebservice = new GetCompletedJobWebservice(new GetCompletedJobWebservice.GetCompletedJobWebserviceHandler() {
                @Override
                public void GetCompletedJobWebserviceStart() {

                }

                @Override
                public void GetCompletedJobWebserviceSucessful(String response, String message) {
                    parseJobList(response, message);
                }

                @Override
                public void GetCompletedJobWebserviceFailedWithMessage(String message) {
                    showToast(message);

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

    private void getactivejoblistbyowner(String ownerID) {
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
            ownerIdRequest.setOwnerID(ownerID);


            JSONObject params = new JSONObject(ownerIdRequest.toString());

            getActiveJobWebservice.callService(params, Constants.METHOD_GET);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private void getnewjoblistbyownerid(String ownerID) {
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
            ownerIdRequest.setOwnerID(ownerID);

            JSONObject params = new JSONObject(ownerIdRequest.toString());

            getNewJobWebservice.callService(params, Constants.METHOD_GET);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    private void getNewJobListforPM() {
        GetNewJobForPMWebservice getNewJobForPMWebservice = new GetNewJobForPMWebservice(new GetNewJobForPMWebservice.GetNewJobForPMWebserviceHandler() {
            @Override
            public void getNewJobForPMWebserviceStart() {

            }

            @Override
            public void getNewJobForPMWebserviceSucessful(String response, String message) {
                parseJobList(response, message);
            }

            @Override
            public void getNewJobForPMWebserviceFailedWithMessage(String message) {
                showToast(message);
            }
        }, getActivity());

        try {
            JSONObject params = new JSONObject();
            getNewJobForPMWebservice.callService(params, Constants.METHOD_GET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void getActiveJobListforPM() {
        GetActiveJobForPMWebservice getActiveJobForPMWebservice = new GetActiveJobForPMWebservice(new GetActiveJobForPMWebservice.GetActiveJobForPMWebserviceHandler() {
            @Override
            public void getActiveJobForPMWebserviceStart() {

            }

            @Override
            public void getActiveJobForPMWebserviceSucessful(String response, String message) {
                parseJobList(response, message);
            }

            @Override
            public void getActiveJobForPMWebserviceFailedWithMessage(String message) {
                showToast(message);
            }
        }, getActivity());
        try {
            JSONObject params = new JSONObject();
            getActiveJobForPMWebservice.callService(params, Constants.METHOD_GET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void getCompletedJobListforPM() {
        GetCompletedJobForPMWebservice getCompletedJobForPMWebservice = new GetCompletedJobForPMWebservice(new GetCompletedJobForPMWebservice.GetCompletedJobForPMWebserviceHandler() {
            @Override
            public void getCompletedJobForPMWebserviceStart() {

            }

            @Override
            public void getCompletedJobForPMWebserviceSucessful(String response, String message) {
                parseJobList(response, message);
            }

            @Override
            public void getCompletedJobForPMWebserviceFailedWithMessage(String message) {
                showToast(message);
            }
        }, getActivity());

        try {
            JSONObject params = new JSONObject();
            getCompletedJobForPMWebservice.callService(params, Constants.METHOD_GET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void getcompletedjoblistbyMMID() {
        try {
            GetCompletedJobForMMIDWebservice getCompletedJobForMMIDWebservice = new GetCompletedJobForMMIDWebservice(new GetCompletedJobForMMIDWebservice.GetCompletedJobForMMIDWebserviceHandler() {
                @Override
                public void getCompletedJobForMMIDWebserviceStart() {

                }

                @Override
                public void getCompletedJobForMMIDWebserviceSucessful(String response, String message) {
                    parseJobList(response, message);
                }

                @Override
                public void getCompletedJobForMMIDWebserviceFailedWithMessage(String message) {
                    showToast(message);
                }
            }, getActivity());


            MMIdRequest mmIdRequest = new MMIdRequest();
            mmIdRequest.setMmID(getUserGson().getMmID());
            JSONObject params = new JSONObject(mmIdRequest.toString());
            getCompletedJobForMMIDWebservice.callService(params, Constants.METHOD_GET);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void getactivejoblistbyMMID() {
        try {
            GetActiveJobForMMIDWebservice getActiveJobForMMIDWebservice = new GetActiveJobForMMIDWebservice(new GetActiveJobForMMIDWebservice.GetActiveJobForMMIDWebserviceHandler() {
                @Override
                public void getActiveJobForMMIDWebserviceStart() {

                }

                @Override
                public void getActiveJobForMMIDWebserviceSucessful(String response, String message) {
                    parseJobList(response, message);
                }

                @Override
                public void getActiveJobForMMIDWebserviceFailedWithMessage(String message) {
                    showToast(message);
                }
            }, getActivity());


            MMIdRequest mmIdRequest = new MMIdRequest();
            mmIdRequest.setMmID(getUserGson().getMmID());
            JSONObject params = new JSONObject(mmIdRequest.toString());

            getActiveJobForMMIDWebservice.callService(params, Constants.METHOD_GET);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private void getnewjoblistbyMMID() {
        try {
            GetNewJobForMMIDWebservice getNewJobForMMIDWebservice = new GetNewJobForMMIDWebservice(new GetNewJobForMMIDWebservice.GetNewJobForMMIDWebserviceHandler() {
                @Override
                public void getNewJobForMMIDWebserviceStart() {

                }

                @Override
                public void getNewJobForMMIDWebserviceSucessful(String response, String message) {
                    parseJobList(response, message);
                }

                @Override
                public void getNewJobForMMIDWebserviceFailedWithMessage(String message) {
                    showToast(message);
                }
            }, getActivity());

            MMIdRequest mmIdRequest = new MMIdRequest();
            mmIdRequest.setMmID(getUserGson().getMmID());
            JSONObject params = new JSONObject(mmIdRequest.toString());

            getNewJobForMMIDWebservice.callService(params, Constants.METHOD_GET);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

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
                    utils.showToast(message);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                showToast(message);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Utils.showLog(getActivity().getClass().getSimpleName(), "got on resume position" + getSelectedPosition());
        list.clear();
        loadData();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }
}
