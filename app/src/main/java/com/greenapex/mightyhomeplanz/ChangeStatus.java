package com.greenapex.mightyhomeplanz;

import com.greenapex.R;
import com.greenapex.widgets.CustomTextView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Spinner;

public class ChangeStatus extends Activity implements OnClickListener {
    Activity activity;
    ImageButton btnClose;
    Spinner spStatus;
    CustomTextView tvSubmit, tvClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_change_status);

        activity = ChangeStatus.this;
        init();
    }

    public void init() {
        btnClose = (ImageButton) findViewById(R.id.btnClose_ChangeStatus);
        spStatus = (Spinner) findViewById(R.id.spStatus_ChangeStatus);
        tvSubmit = (CustomTextView) findViewById(R.id.tvSubmit_ChangeStatus);
        tvClear = (CustomTextView) findViewById(R.id.tvClear_ChangeStatus);
        btnClose.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        tvClear.setOnClickListener(this);
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {
            case R.id.btnClose_ChangeStatus:
                finish();
                overridePendingTransition(R.anim.nothing, R.anim.effect_box_back);
                break;

            case R.id.tvSubmit_ChangeStatus:
                finish();
                overridePendingTransition(R.anim.nothing, R.anim.effect_box_back);
                break;

            case R.id.tvClear_ChangeStatus:
                spStatus.setSelection(0);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        findViewById(R.id.btnClose_ChangeStatus).performClick();
    }
}
