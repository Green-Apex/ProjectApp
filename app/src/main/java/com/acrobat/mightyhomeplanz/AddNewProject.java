package com.acrobat.mightyhomeplanz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

public class AddNewProject extends Activity implements OnClickListener {

    Activity activity;
    ImageButton btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_project);

        activity = AddNewProject.this;
        init();
    }

    public void init() {
        btnClose = (ImageButton) findViewById(R.id.btnClose_AddNewProject);
        btnClose.setOnClickListener(this);

    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {
            case R.id.btnClose_AddNewProject:
                finish();
                overridePendingTransition(R.anim.nothing, R.anim.slide_out_left);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        findViewById(R.id.btnClose_AddNewProject).performClick();
    }
}
