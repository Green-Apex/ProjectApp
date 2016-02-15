package com.greenapex.mightyhomeplanz;

import com.greenapex.R;
import com.greenapex.widgets.CustomTextView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class EditProfile extends Activity implements OnClickListener {

    Activity activity;
    CustomTextView tvSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit_profile);

        activity = EditProfile.this;
        init();
    }

    public void init() {
        tvSave = (CustomTextView) findViewById(R.id.tvSave_EditProfile);
        tvSave.setOnClickListener(this);
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {
            case R.id.tvSave_EditProfile:
                finish();
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                break;

        }
    }
}
