package com.greenapex.mightyhomeplanz;

import com.greenapex.widgets.CustomTextView;
import com.greenapex.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class ForgotPassword extends Activity implements OnClickListener {

    Activity activity;
    CustomTextView tvSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forgotpassword);

        activity = ForgotPassword.this;
        init();
    }

    public void init() {
        tvSend = (CustomTextView) findViewById(R.id.tvSend_ForgotPassword);
        tvSend.setOnClickListener(this);
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {
            case R.id.tvSend_ForgotPassword:
                startActivity(new Intent(activity, Signin.class));
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
