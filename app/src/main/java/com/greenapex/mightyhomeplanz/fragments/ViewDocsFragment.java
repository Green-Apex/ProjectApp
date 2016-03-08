package com.greenapex.mightyhomeplanz.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.greenapex.R;
import com.greenapex.Request.models.JobDOCModel;
import com.greenapex.Utils.Constants;
import com.greenapex.mightyhomeplanz.AddDocument;
import com.greenapex.mightyhomeplanz.adapters.ViewDocsAdapter;
import com.greenapex.response.models.JobDetailResponse;
import com.greenapex.webservice.GetJobDetailByJobIdWebservice;
import com.greenapex.widgets.CustomSwipeRefreshLayout;
import com.greenapex.widgets.CustomSwipeRefreshLayout.OnChildScrollUpListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ViewDocsFragment extends BaseFragment implements OnClickListener {
    View view;
    Activity activity;

    CustomSwipeRefreshLayout swipeRefresh;
    ListView lv;
    ImageButton btnAdd;

    ArrayList<JobDOCModel> list = new ArrayList<JobDOCModel>();
    ViewDocsAdapter adapter;
    private String selectJobID;
    private JobDetailResponse jobDetailResponse;

    public static Fragment newInstance(String jobID) {
        ViewDocsFragment f = new ViewDocsFragment();
        Bundle params = new Bundle();
        params.putString(Constants.JOBID, jobID);
        f.setArguments(params);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_view_docs, container, false);
        selectJobID = getArguments().getString(Constants.JOBID, "");
        activity = getActivity();

        init();

        return view;
    }

    private void getJobDetail(String selectJobID) {
        GetJobDetailByJobIdWebservice getJobDetailByJobIdWebservice = new GetJobDetailByJobIdWebservice(new GetJobDetailByJobIdWebservice.GetJobDetailByJobIdWebserviceHandler() {
            @Override
            public void getJobDetailByJobIdWebserviceStart() {

            }

            @Override
            public void getJobDetailByJobIdWebserviceSucessful(String response, String message) {
                swipeRefresh.setRefreshing(false);
                if (response != null && response.length() > 0) {
                    jobDetailResponse = getGson().fromJson(response, JobDetailResponse.class);
                }
                showLog(response);
                loadData();
                // init();
            }

            @Override
            public void getJobDetailByJobIdWebserviceFailedWithMessage(String message) {
                swipeRefresh.setRefreshing(false);
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

    @Override
    public void onResume() {
        super.onResume();
    }

    public void init() {
        btnAdd = (ImageButton) view.findViewById(R.id.btnAddDoc_fragViewDocs);
        btnAdd.setOnClickListener(this);

        swipeRefresh = (CustomSwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout_fragViewDocs);
        swipeRefresh.setColorSchemeResources(R.color.app_color, R.color.purple);
        swipeRefresh.setProgressViewOffset(false, 0, 100);

        lv = (ListView) view.findViewById(R.id.lv_fragViewDocs);

        swipeRefresh.setOnChildScrollUpListener(new OnChildScrollUpListener() {
            @Override
            public boolean canChildScrollUp() {
                return lv.getFirstVisiblePosition() > 0 || lv.getChildAt(0) == null || lv.getChildAt(0).getTop() < 0;
            }
        });

        getJobDetail(selectJobID);


        swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                getJobDetail(selectJobID);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {
            case R.id.btnAddDoc_fragViewDocs:
                Intent addDocIntent = new Intent(activity, AddDocument.class);
                addDocIntent.putExtra(Constants.JOBID, selectJobID);
                startActivity(addDocIntent);
                activity.overridePendingTransition(R.anim.effect_box, R.anim.nothing);
                break;
        }
    }

    public void loadData() {
        list.addAll(jobDetailResponse.getJobDoc());
        adapter = new ViewDocsAdapter(activity, list, getUserGson(), selectJobID);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }

        });
//        for (int i = 0; i < 3; i++) {
//            ProjectModel data = new ProjectModel();
//            data.setId("" + (i + 1));
//            list.add(data);
//        }
    }

}
