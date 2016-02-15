package com.acrobat.mightyhomeplanz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class MightyHomePlanz extends Activity {

    Thread mThread;
    private static final int SPLASH_SCREEN_TIME = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(SPLASH_SCREEN_TIME);

                    runOnUiThread(new Runnable() {
                        @SuppressLint("NewApi")
                        public void run() {
                            startActivity(new Intent(MightyHomePlanz.this, Signin.class));
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
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
