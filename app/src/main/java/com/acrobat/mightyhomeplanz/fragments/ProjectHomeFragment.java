package com.acrobat.mightyhomeplanz.fragments;

import com.acrobat.mightyhomeplanz.AddMilestone;
import com.acrobat.mightyhomeplanz.ChangeStatus;
import com.acrobat.mightyhomeplanz.R;
import com.acrobat.mightyhomeplanz.Review;
import com.acrobat.widgets.CustomTextView;
import com.viewpagerindicator.CirclePageIndicator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ProjectHomeFragment extends Fragment implements OnClickListener {
    View view;
    Activity activity;

    static final int NUM_ITEMS = 6;
    ImageFragmentPagerAdapter imageFragmentPagerAdapter;
    ViewPager viewPager;
    CustomTextView tvStatus, tvReview;
    ImageButton btnAddMilestone;

    public static final String[] IMAGE_NAME = {"eagle", "horse", "bonobo", "wolf", "owl", "bear",};

    public static Fragment newInstance() {
        ProjectHomeFragment f = new ProjectHomeFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_project_home, container, false);

        activity = getActivity();

        init();
        return view;
    }

    public void init() {
        imageFragmentPagerAdapter = new ImageFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.pager_fragProjectHome);
        viewPager.setAdapter(imageFragmentPagerAdapter);

        CirclePageIndicator titleIndicator = (CirclePageIndicator) view
                .findViewById(R.id.pagerIndicator_fragProjectHome);
        titleIndicator.setViewPager(viewPager);

        tvStatus = (CustomTextView) view.findViewById(R.id.tvStatus_fragProjectHome);
        tvStatus.setOnClickListener(this);
        tvReview = (CustomTextView) view.findViewById(R.id.tvReview_fragProjectHome);
        tvReview.setOnClickListener(this);

        btnAddMilestone = (ImageButton) view.findViewById(R.id.btnAddMilestone_fragProjectHome);
        btnAddMilestone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {
            case R.id.tvStatus_fragProjectHome:
                activity.startActivity(new Intent(activity, ChangeStatus.class));
                activity.overridePendingTransition(R.anim.effect_box, R.anim.nothing);
                break;

            case R.id.tvReview_fragProjectHome:
                activity.startActivity(new Intent(activity, Review.class));
                activity.overridePendingTransition(R.anim.effect_box, R.anim.nothing);
                break;

            case R.id.btnAddMilestone_fragProjectHome:
                activity.startActivity(new Intent(activity, AddMilestone.class));
                activity.overridePendingTransition(R.anim.effect_box, R.anim.nothing);
                break;
        }
    }

    public static class ImageFragmentPagerAdapter extends FragmentPagerAdapter {
        public ImageFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            SwipeFragment fragment = new SwipeFragment();
            return SwipeFragment.newInstance(position);
        }
    }

    public static class SwipeFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View swipeView = inflater.inflate(R.layout.swipe_fragment, container, false);
            ImageView imageView = (ImageView) swipeView.findViewById(R.id.imageView);
            Bundle bundle = getArguments();
            int position = bundle.getInt("position");
            String imageFileName = IMAGE_NAME[position];
            int imgResId = getResources().getIdentifier(imageFileName, "drawable", "com.acrobat.mightyhomeplanz");
            imageView.setImageResource(imgResId);
            return swipeView;
        }

        static SwipeFragment newInstance(int position) {
            SwipeFragment swipeFragment = new SwipeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            swipeFragment.setArguments(bundle);
            return swipeFragment;
        }
    }
}
