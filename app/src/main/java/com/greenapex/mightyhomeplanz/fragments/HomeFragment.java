package com.greenapex.mightyhomeplanz.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greenapex.R;
import com.greenapex.Request.models.OwnerIdRequest;
import com.greenapex.Utils.Constants;
import com.greenapex.Utils.Utils;
import com.greenapex.mightyhomeplanz.entities.ProjectModel;
import com.greenapex.response.models.UserResponse;
import com.greenapex.webservice.GetActiveJobWebservice;
import com.greenapex.webservice.GetCompletedJobWebservice;
import com.greenapex.webservice.GetNewJobWebservice;
import com.greenapex.webservice.GetTotalJobWebservice;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3;
    private MyAdapter pagerAdapter;
    private Utils utils;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate tab_layout and setup Views.
         */
        View x = inflater.inflate(R.layout.frag_home, null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        utils = new Utils(getActivity());
        /**
         * Set an Adapter for the View Pager
         */
        pagerAdapter = new MyAdapter(getActivity().getSupportFragmentManager(), 0);
        viewPager.setAdapter(pagerAdapter);

        /**
         * Now , this is a workaround , The setupWithViewPager dose't works
         * without the runnable . Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return x;

    }


    class MyAdapter extends FragmentPagerAdapter {
        int mPosition;

        public MyAdapter(FragmentManager fm, int position) {
            super(fm);
            mPosition = position;
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {

            return ProjectsFragment.newInstance(position);
        }

        @Override
        public int getCount() {

            return int_items;

        }


        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "NEW";
                case 1:
                    return "ACTIVE";
                case 2:
                    return "COMPLETED";
            }
            return null;
        }
    }

}
