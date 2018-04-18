package com.nddcoder.places;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();

        Typeface logoTypeface = Typeface.createFromAsset(getAssets(), "Grandesign.ttf");
        TextView tvLogo = findViewById(R.id.tv_logo);
        tvLogo.setTypeface(logoTypeface);

        Typeface sloganTypeface = Typeface.createFromAsset(getAssets(), "Champagne.ttf");
        TextView tvSlogan = findViewById(R.id.tv_slogan);
        tvSlogan.setTypeface(sloganTypeface);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(StartActivity.this, MapsActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
