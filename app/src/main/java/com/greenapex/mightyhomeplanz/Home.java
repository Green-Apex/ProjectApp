package com.greenapex.mightyhomeplanz;

import com.greenapex.R;
import com.greenapex.mightyhomeplanz.adapters.Menu_Custom_Adapter;
import com.greenapex.mightyhomeplanz.fragments.HomeFragment;
import com.greenapex.widgets.CustomRoundedImageView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class Home extends FragmentActivity implements OnClickListener {
    Activity activity;
    DrawerLayout mDrawerLayout;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    NavigationView navigationView;
    public static ListView listDrawer;
    LinearLayout llUser;
    RelativeLayout rlUser;
    CustomRoundedImageView iv;

    static String[] menus;
    public Menu_Custom_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        activity = Home.this;

        /**
         * Setup the DrawerLayout and NavigationView
         */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        setupToolbar();
        setupSideMenu();

        navigationView = (NavigationView) findViewById(R.id.navigationview_home);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new HomeFragment()).commit();

    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.findViewById(R.id.btnAdd_Home).setOnClickListener(this);
        toolbar.findViewById(R.id.btnMenu_Home).setOnClickListener(this);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.app_name, R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // mDrawerToggle.syncState();
    }

    public void setupSideMenu() {
        llUser = (LinearLayout) findViewById(R.id.llUser_SideMenu);
        rlUser = (RelativeLayout) findViewById(R.id.rlUser_SideMenu);

        iv = (CustomRoundedImageView) findViewById(R.id.ivUserPic_SideMenu);

        listDrawer = (ListView) findViewById(R.id.lvMenu);

        adapter = new Menu_Custom_Adapter(activity);
        listDrawer.setAdapter(adapter);
        listDrawer.setSelection(0);

        // Picasso.with(activity).load("http://mw2.google.com/mw-panoramio/photos/medium/121044966.jpg")
        // .placeholder(R.drawable.ic_launcher).into(iv);

        listDrawer.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerLayout.closeDrawers();
                switch (position) {
                    case 0:
                        startActivity(new Intent(activity, EditProfile.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case 1:
                        gotoAlertsFromHome(view);
                        break;
                    case 2:
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.containerView, new HomeFragment()).commit();
                        break;
                    case 4:
                        startActivity(new Intent(activity, Signin.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                        break;
                }
            }
        });
    }

    public void gotoAlertsFromHome(View v) {
        startActivity(new Intent(activity, Alerts.class));
        overridePendingTransition(R.anim.effect_box, R.anim.nothing);
    }

    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {
            case R.id.btnMenu_Home:
                if (mDrawerLayout.isDrawerOpen(navigationView))
                    mDrawerLayout.closeDrawers();
                else
                    mDrawerLayout.openDrawer(navigationView);
                break;

            case R.id.btnAdd_Home:
                startActivity(new Intent(activity, AddNewProject.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.nothing);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(navigationView))
            mDrawerLayout.closeDrawers();
        else
            super.onBackPressed();
    }
}