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
import com.greenapex.mightyhomeplanz.AddDocument;
import com.greenapex.mightyhomeplanz.adapters.ViewDocsAdapter;
import com.greenapex.mightyhomeplanz.entities.ProjectModel;
import com.greenapex.widgets.CustomSwipeRefreshLayout;
import com.greenapex.widgets.CustomSwipeRefreshLayout.OnChildScrollUpListener;

import java.util.ArrayList;

public class ViewDocsFragment extends Fragment implements OnClickListener {
    View view;
    Activity activity;

    CustomSwipeRefreshLayout swipeRefresh;
    ListView lv;
    ImageButton btnAdd;

    ArrayList<ProjectModel> list = new ArrayList<ProjectModel>();
    ViewDocsAdapter adapter;

    public static Fragment newInstance(String jobID) {
        ViewDocsFragment f = new ViewDocsFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_view_docs, container, false);

        activity = getActivity();

        init();
        return view;
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

        loadData();
        adapter = new ViewDocsAdapter(activity, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }

        });

        swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                loadData();
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
                startActivity(new Intent(activity, AddDocument.class));
                activity.overridePendingTransition(R.anim.effect_box, R.anim.nothing);
                break;
        }
    }

    public void loadData() {
        for (int i = 0; i < 3; i++) {
            ProjectModel data = new ProjectModel();
            data.setId("" + (i + 1));
            list.add(data);
        }
    }

}
