package com.acrobat.mightyhomeplanz;

import com.acrobat.widgets.CustomTextView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

public class AddMilestone extends Activity implements OnClickListener {
    Activity activity;
    ImageButton btnClose;
    CustomTextView tvSubmit, tvClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_milestone);

        activity = AddMilestone.this;
        init();
    }

    public void init() {
        btnClose = (ImageButton) findViewById(R.id.btnClose_AddMilestone);
        tvSubmit = (CustomTextView) findViewById(R.id.tvSubmit_AddMilestone);
        tvClear = (CustomTextView) findViewById(R.id.tvClear_AddMilestone);
        btnClose.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        tvClear.setOnClickListener(this);
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {
            case R.id.btnClose_AddMilestone:
                finish();
                overridePendingTransition(R.anim.nothing, R.anim.effect_box_back);
                break;

            case R.id.tvSubmit_AddMilestone:
                finish();
                overridePendingTransition(R.anim.nothing, R.anim.effect_box_back);
                break;

            case R.id.tvClear_AddMilestone:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        findViewById(R.id.btnClose_AddMilestone).performClick();
    }
}
