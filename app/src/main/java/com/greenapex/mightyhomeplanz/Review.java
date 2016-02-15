package com.greenapex.mightyhomeplanz;

import com.greenapex.R;
import com.greenapex.widgets.CustomTextView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class Review extends Activity implements OnClickListener {

    Activity activity;
    CustomTextView tvSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_review);

        activity = Review.this;
        init();
    }

    public void init() {
        tvSave = (CustomTextView) findViewById(R.id.tvSave_Review);
        tvSave.setOnClickListener(this);
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {
            case R.id.tvSave_Review:
                finish();
                overridePendingTransition(R.anim.nothing, R.anim.effect_box_back);
                break;

        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.nothing, R.anim.effect_box_back);
    }
}
