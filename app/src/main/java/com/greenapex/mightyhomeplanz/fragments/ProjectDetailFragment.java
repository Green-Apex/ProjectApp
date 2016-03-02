package com.greenapex.mightyhomeplanz.fragments;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greenapex.R;
import com.greenapex.Utils.Constants;
import com.greenapex.Utils.Utils;

public class ProjectDetailFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3;
    private Fragment projectHomeFragment;
    private Fragment viewDocsFragment;
    private Fragment chatFragment;
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
        tabLayout.addTab(tabLayout.newTab().setText("HOME"));
        tabLayout.addTab(tabLayout.newTab().setText("VIEW DOCS"));
        tabLayout.addTab(tabLayout.newTab().setText("CHAT"));
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        utils = new Utils(getActivity());
        /**
         * Set an Adapter for the View Pager
         */
        String selectedJob = getArguments().getString(Constants.JOBID, "0");
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager(), selectedJob));

        /**
         * Now , this is a workaround , The setupWithViewPager dose't works
         * without the runnable . Maybe a Support Library Bug .
         */
//        pagerAdapter = new MyAdapter(getActivity().getSupportFragmentManager(),selectedJob);
//        viewPager.setAdapter(pagerAdapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//                pagerAdapter.getItem(tab.getPosition()).setUserVisibleHint(true);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return x;

    }

    class MyAdapter extends FragmentPagerAdapter {
        String selectedJob;

        public MyAdapter(FragmentManager fm, String selectedJob) {
            super(fm);
            this.selectedJob = selectedJob;
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    return projectHomeFragment = ProjectHomeFragment.newInstance(selectedJob);
                }
                case 1: {
                    return viewDocsFragment = ViewDocsFragment.newInstance(selectedJob);

                }
                case 2: {
                    return chatFragment = ChatFragment.newInstance(selectedJob);

                }
                default:
                    return null;
            }

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
                    return "HOME";
                case 1:
                    return "VIEW DOCS";
                case 2:
                    return "CHAT";
            }
            return null;
        }
    }

}
