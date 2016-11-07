package com.greenapex.mightyhomeplanz.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greenapex.R;
import com.greenapex.Request.models.OwnerIdRequest;
import com.greenapex.Utils.Constants;
import com.greenapex.mightyhomeplanz.adapters.PmAdapter;
import com.greenapex.mightyhomeplanz.entities.JobOwnerModel;
import com.greenapex.webservice.GetAllPmWebservice;
import com.greenapex.widgets.CustomSwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by RulTech Solutions Private Limited on 30/8/16.
 */
public class PmFragment extends BaseFragment {

    private View view;
    private Activity activity;
    public static Gson gson;
    private ProgressDialog mProgressDialog;
    private CustomSwipeRefreshLayout swipeRefresh;
    private TextView txtNoData;
    private ListView lv;
    public ArrayList<JobOwnerModel> joList = new ArrayList<>();
    private PmAdapter adapter;

    public static Fragment newInstance(int position) {
        PmFragment f = new PmFragment();
        Bundle b = new Bundle();
        b.putInt("position", position);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

        swipeRefresh.setOnChildScrollUpListener(new CustomSwipeRefreshLayout.OnChildScrollUpListener() {
            @Override
            public boolean canChildScrollUp() {
                return lv.getFirstVisiblePosition() > 0 || lv.getChildAt(0) == null || lv.getChildAt(0).getTop() < 0;
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                joList.clear();
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

    private void showlistView() {
        if (joList.size() > 0) {
            txtNoData.setText("");
            txtNoData.setVisibility(View.GONE);
        } else {
            txtNoData.setText("No Jo Found!");
        }
        if (lv != null) {
            lv.setAdapter(null);
            adapter = new PmAdapter(activity, joList);
            lv.setAdapter(adapter);
            lv.invalidate();
            adapter.notifyDataSetChanged();
        }
    }


    public void loadData() {
        getPmlistbyuserID();
    }

    private void getPmlistbyuserID() {
        try {
            GetAllPmWebservice getAllPmWebservice = new GetAllPmWebservice(new GetAllPmWebservice.GetAllPmWebserviceHandler() {
                @Override
                public void GetAllPmWebserviceStart() {

                }

                @Override
                public void GetAllPmWebserviceSucessful(String response, String message) {
                    parsePmList(response, message);
                }

                @Override
                public void GetAllPmWebserviceFailedWithMessage(String message) {
                    showToast(message);
                }
            }, activity);


            OwnerIdRequest ownerIdRequest = new OwnerIdRequest();
            ownerIdRequest.setUserID(getUserGson().getUserID());


            JSONObject params = new JSONObject(ownerIdRequest.toString());

            getAllPmWebservice.callService(params, Constants.METHOD_GET);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private void parsePmList(String response, String message) {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
        swipeRefresh.setRefreshing(false);
        Log.d("Success", message);
        if (response != null && response.length() > 0) {
            try {
                JSONArray mResponse = new JSONArray(response);
                joList.clear();
                if (mResponse != null && mResponse.length() > 0) {
                    for (int i = 0; i < mResponse.length(); i++) {
                        String temp= mResponse.get(i).toString();
                        JobOwnerModel jobitem = getGson().fromJson(temp, JobOwnerModel.class);
                        joList.add(jobitem);
                    }
                    showlistView();
                } else {
                    utils.showToast(message);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                txtNoData.setText("No Job Found");
                // showToast(message);
            }
        }
    }

}
