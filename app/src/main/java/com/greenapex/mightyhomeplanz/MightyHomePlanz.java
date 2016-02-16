package com.greenapex.mightyhomeplanz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greenapex.R;
import com.greenapex.Utils.Constants;
import com.greenapex.response.models.UserResponse;

public class MightyHomePlanz extends Activity {

    Thread mThread;
    private static final int SPLASH_SCREEN_TIME = 500;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private UserResponse userGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getSharedPreferences(Constants.mightyHomePlanz, Context.MODE_PRIVATE);

        gson = new GsonBuilder().create();

        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(SPLASH_SCREEN_TIME);

                    runOnUiThread(new Runnable() {
                        @SuppressLint("NewApi")
                        public void run() {
                            // check if user is logged in or not
                            String userData = sharedPreferences.getString(Constants.mightyHomePlanz, "");
                            if (userData.length() > 0) {
                                UserResponse userResponse = gson.fromJson(userData, UserResponse.class);
                                if (userResponse.getEmail().length() > 0) {
                                    startActivity(new Intent(MightyHomePlanz.this, Home.class));
                                    finish();
                                }
                            } else {
                                startActivity(new Intent(MightyHomePlanz.this, Signin.class));
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                finish();
                            }

                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        mThread.start();
    }

}
