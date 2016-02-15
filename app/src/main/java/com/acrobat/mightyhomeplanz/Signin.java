package com.acrobat.mightyhomeplanz;

import com.acrobat.widgets.CustomTextView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class Signin extends Activity implements OnClickListener {

    Activity activity;
    CustomTextView tvCreateAccount, tvForgotPassword, tvSignin, tvFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signin);

        activity = Signin.this;
        init();

    }

    public void init() {
        tvCreateAccount = (CustomTextView) findViewById(R.id.tvCreateAccount_Signin);
        tvForgotPassword = (CustomTextView) findViewById(R.id.tvForgotPassword_Signin);
        tvSignin = (CustomTextView) findViewById(R.id.tvsignin_Signin);

        tvCreateAccount.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        tvSignin.setOnClickListener(this);
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {
            case R.id.tvCreateAccount_Signin:
                startActivity(new Intent(activity, Signup.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                break;

            case R.id.tvForgotPassword_Signin:
                startActivity(new Intent(activity, ForgotPassword.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                break;

            case R.id.tvsignin_Signin:
                startActivity(new Intent(activity, Home.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                break;
        }
    }
}
