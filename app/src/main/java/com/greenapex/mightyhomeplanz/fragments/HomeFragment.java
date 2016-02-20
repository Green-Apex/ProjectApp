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

public class HomeFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3;
    private MyAdapter pagerAdapter;
    private Utils utils;
    private Fragment newJobFragment;
    private Fragment completedJobFragment;
    private Fragment activeJobFragment;


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
        pagerAdapter = new MyAdapter(getActivity().getSupportFragmentManager());
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


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int scrollstate;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // utils.showToast("" + position);

                if (scrollstate == ViewPager.SCROLL_STATE_IDLE) {
                    switch (position) {
                        case 0: {
                            if (newJobFragment != null)
                                if (newJobFragment instanceof ProjectsFragment) {
                                    ((ProjectsFragment) newJobFragment).setSelectedPosition(0);
                                    newJobFragment.onResume();
                                }
                            break;
                        }
                        case 1: {
                            if (activeJobFragment != null) {
                                if (activeJobFragment instanceof ProjectsFragment) {
                                    ((ProjectsFragment) activeJobFragment).setSelectedPosition(1);
                                    activeJobFragment.onResume();
                                }
                            }

                            break;
                        }
                        case 2: {
                            if (completedJobFragment != null) {
                                if (completedJobFragment instanceof ProjectsFragment) {
                                    ((ProjectsFragment) completedJobFragment).setSelectedPosition(2);
                                    completedJobFragment.onResume();
                                }
                            }

                            break;
                        }

                    }
                }

//                pagerAdapter = new MyAdapter(getActivity().getSupportFragmentManager(), position);
//                viewPager.setAdapter(pagerAdapter);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (state == ViewPager.SCROLL_STATE_IDLE)
                    scrollstate = state;
            }
        });
        return x;

    }


    class MyAdapter extends FragmentPagerAdapter {


        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0: {
                    return newJobFragment = ProjectsFragment.newInstance(position);
                }
                case 1: {
                    return activeJobFragment = ProjectsFragment.newInstance(position);

                }
                case 2: {
                    return completedJobFragment = ProjectsFragment.newInstance(position);

                }
                default:
                    return null;
            }
//            return ProjectsFragment.newInstance(position);
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
