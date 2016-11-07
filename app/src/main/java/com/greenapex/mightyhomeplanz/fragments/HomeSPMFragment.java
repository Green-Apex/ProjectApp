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
import com.greenapex.Utils.Utils;

public class HomeSPMFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2;
    private ProjectHomeAdaptor pagerAdapter;
    private Utils utils;
    private Fragment joFragment;
    private Fragment pmFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate tab_layout and setup Views.
         */
        View x = inflater.inflate(R.layout.frag_home, null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("JO"));
        tabLayout.addTab(tabLayout.newTab().setText("PM"));
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        utils = new Utils(getActivity());
        /**
         * Set an Adapter for the View Pager
         */
        pagerAdapter = new ProjectHomeAdaptor(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                pagerAdapter.getItem(tab.getPosition()).setUserVisibleHint(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return x;

    }


    class ProjectHomeAdaptor extends FragmentPagerAdapter {


        public ProjectHomeAdaptor(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
//            Fragment frag = null;
            switch (position) {
                case 0: {
                    return joFragment = JoFragment.newInstance(position);
                }
                case 1: {
                    return pmFragment = PmFragment.newInstance(position);

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
                    return "JO";
                case 1:
                    return "PM";
            }
            return null;
        }
    }

}
