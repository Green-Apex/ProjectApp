package com.acrobat.mightyhomeplanz;

import com.acrobat.widgets.CustomTextView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class Signup extends Activity implements OnClickListener {

    Activity activity;
    CustomTextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signup);

        activity = Signup.this;
        init();
    }

    public void init() {
        tvRegister = (CustomTextView) findViewById(R.id.tvRegister_Signup);
        tvRegister.setOnClickListener(this);
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {
            case R.id.tvRegister_Signup:
                startActivity(new Intent(activity, Signup.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                break;

        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(activity, Signin.class));
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
        finish();
    }
}
