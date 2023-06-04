package com.devsimplified.xo;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;


public class LoadingActivity extends Activity {

    private static final int LOADING_DELAY = 2000; // Delay time for the loading simulation in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // Simulate loading process with a delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity after the loading delay
                Intent intent = new Intent(LoadingActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        }, LOADING_DELAY);
    }
}
