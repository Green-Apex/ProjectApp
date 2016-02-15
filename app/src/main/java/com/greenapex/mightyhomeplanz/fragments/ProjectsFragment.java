package com.greenapex.mightyhomeplanz.fragments;

import java.util.ArrayList;


import com.greenapex.mightyhomeplanz.adapters.ProjectsAdapter;
import com.greenapex.mightyhomeplanz.entities.ProjectModel;
import com.greenapex.widgets.CustomSwipeRefreshLayout;
import com.greenapex.widgets.CustomSwipeRefreshLayout.OnChildScrollUpListener;
import com.greenapex.R;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ProjectsFragment extends Fragment {
    View view;
    Activity activity;
    CustomSwipeRefreshLayout swipeRefresh;
    ListView lv;

    ArrayList<ProjectModel> list = new ArrayList<ProjectModel>();
    ProjectsAdapter adapter;

    int mPosition = 0;

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

        if (getArguments() != null)
            mPosition = getArguments().getInt("position");

        init();
        return view;
    }

    public void init() {
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

        loadData();
        adapter = new ProjectsAdapter(activity, list, mPosition);
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

        swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                loadData();
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void loadData() {
        for (int i = 0; i < 10; i++) {
            ProjectModel data = new ProjectModel();
            data.setId("" + (i + 1));
            list.add(data);
        }
    }
}
