package com.acrobat.mightyhomeplanz;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.acrobat.mightyhomeplanz.adapters.AlertsAdapter;
import com.acrobat.mightyhomeplanz.entities.ProjectModel;
import com.acrobat.widgets.CustomSwipeRefreshLayout;
import com.acrobat.widgets.CustomSwipeRefreshLayout.OnChildScrollUpListener;

public class Alerts extends Activity implements OnClickListener {

    Activity activity;
    ImageButton btnClose;
    CustomSwipeRefreshLayout swipeRefresh;
    ListView lv;

    ArrayList<ProjectModel> list = new ArrayList<ProjectModel>();
    AlertsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_alerts);

        activity = Alerts.this;
        init();
    }

    public void init() {
        btnClose = (ImageButton) findViewById(R.id.btnClose_Alerts);
        btnClose.setOnClickListener(this);

        swipeRefresh = (CustomSwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_Alerts);
        swipeRefresh.setColorSchemeResources(R.color.app_color, R.color.purple);
        swipeRefresh.setProgressViewOffset(false, 0, 100);

        lv = (ListView) findViewById(R.id.lv_Alerts);

        swipeRefresh.setOnChildScrollUpListener(new OnChildScrollUpListener() {
            @Override
            public boolean canChildScrollUp() {
                return lv.getFirstVisiblePosition() > 0 || lv.getChildAt(0) == null || lv.getChildAt(0).getTop() < 0;
            }
        });

        loadData();
        adapter = new AlertsAdapter(activity, list);
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
            case R.id.btnClose_Alerts:
                finish();
                overridePendingTransition(R.anim.nothing, R.anim.effect_box_back);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        findViewById(R.id.btnClose_Alerts).performClick();
    }

    public void loadData() {
        for (int i = 0; i < 10; i++) {
            ProjectModel data = new ProjectModel();
            data.setId("" + (i + 1));
            list.add(data);
        }
    }
}
