package com.example.mateusz.facerank;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * Created by Mateusz on 2015-05-21.
 */
public class ZoomActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        Log.d("Fragmenty", "ZoomActivity: OnCreate");

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }

        if(savedInstanceState == null){
            ZoomFragment zoomFragment = new ZoomFragment();

            zoomFragment.setArguments(getIntent().getExtras());

            Log.d("Fragmenty", "ZoomActivity: getArguments");

            Log.d("Fragmenty", "ZoomActivity: add fragment "+ findViewById(R.id.container));
            getSupportFragmentManager().beginTransaction().add(R.id.container, zoomFragment).commit();

        }
    }

    public int getShownIndex(){

        return getIntent().getExtras().getInt("index", 0);

    }

}
