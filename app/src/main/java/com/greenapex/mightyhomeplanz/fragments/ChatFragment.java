package com.greenapex.mightyhomeplanz.fragments;

import java.util.ArrayList;

import com.greenapex.mightyhomeplanz.adapters.ChatAdapter;
import com.greenapex.mightyhomeplanz.entities.ProjectModel;
import com.greenapex.widgets.CustomSwipeRefreshLayout;
import com.greenapex.widgets.CustomSwipeRefreshLayout.OnChildScrollUpListener;
import com.greenapex.widgets.CustomTextView;
import com.greenapex.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ChatFragment extends Fragment implements OnClickListener {
    View view;
    Activity activity;

    CustomSwipeRefreshLayout swipeRefresh;
    ListView lv;
    CustomTextView tvSend;

    ArrayList<ProjectModel> list = new ArrayList<ProjectModel>();
    ChatAdapter adapter;

    public static Fragment newInstance() {
        ChatFragment f = new ChatFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_chat, container, false);

        activity = getActivity();

        init();
        return view;
    }

    public void init() {
        tvSend = (CustomTextView) view.findViewById(R.id.tvSend_fragChat);
        tvSend.setOnClickListener(this);

        swipeRefresh = (CustomSwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout_fragChat);
        swipeRefresh.setColorSchemeResources(R.color.app_color, R.color.purple);
        swipeRefresh.setProgressViewOffset(false, 0, 100);

        lv = (ListView) view.findViewById(R.id.lv_fragChat);

        swipeRefresh.setOnChildScrollUpListener(new OnChildScrollUpListener() {
            @Override
            public boolean canChildScrollUp() {
                return lv.getFirstVisiblePosition() > 0 || lv.getChildAt(0) == null || lv.getChildAt(0).getTop() < 0;
            }
        });

        loadData();
        adapter = new ChatAdapter(activity, list);
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
            case R.id.tvSend_fragChat:
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
