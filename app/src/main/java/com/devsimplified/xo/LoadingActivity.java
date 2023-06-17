package com.devsimplified.xo;

import android.content.res.Configuration;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;


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



        /*-----------------------------------------------------------------------*/
        /******************************  log qualifier   ****************************/
        /*-----------------------------------------------------------------------*/
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int densityDpi = displayMetrics.densityDpi;
        float density = displayMetrics.density;
        float scaledDensity = displayMetrics.scaledDensity;
        float xdpi = displayMetrics.xdpi;
        float ydpi = displayMetrics.ydpi;

        String qualifier = "";
        if (densityDpi == DisplayMetrics.DENSITY_LOW) {
            qualifier = "ldpi";
        } else if (densityDpi == DisplayMetrics.DENSITY_MEDIUM) {
            qualifier = "mdpi";
        } else if (densityDpi == DisplayMetrics.DENSITY_HIGH) {
            qualifier = "hdpi";
        } else if (densityDpi == DisplayMetrics.DENSITY_XHIGH) {
            qualifier = "xhdpi";
        } else if (densityDpi == DisplayMetrics.DENSITY_XXHIGH) {
            qualifier = "xxhdpi";
        } else if (densityDpi == DisplayMetrics.DENSITY_XXXHIGH) {
            qualifier = "xxxhdpi";
        } else {
            qualifier = "Unknown";
        }

        Log.i("Qualifier", "Density: " + qualifier);
        Log.i("Qualifier", "Density DPI: " + densityDpi);
        Log.i("Qualifier", "Density: " + density);
        Log.i("Qualifier", "Scaled Density: " + scaledDensity);
        Log.i("Qualifier", "X DPI: " + xdpi);
        Log.i("Qualifier", "Y DPI: " + ydpi);


        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
       // String qualifier = "";
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                qualifier = "small";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                qualifier = "normal";
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                qualifier = "large";
                break;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                qualifier = "xlarge";
                break;
            default:
                qualifier = "Unknown";
                break;
        }
        Log.d("Qualifier", "Screen Size: " + qualifier);
        /*-----------------------------------------------------------------------*/
        /******************************  log qualifier   ****************************/
        /*-----------------------------------------------------------------------*/

    }
}
