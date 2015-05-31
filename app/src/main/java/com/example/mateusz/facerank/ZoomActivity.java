package com.example.mateusz.facerank;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Mateusz on 2015-05-21.
 */
public class ZoomActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoom_activity);

        if(savedInstanceState == null){
            ZoomFragment zoomFragment = ZoomFragment.newInstance(getIntent().getIntExtra("index", 0));
            getSupportFragmentManager().beginTransaction().add(R.id.container, zoomFragment).commit();

        }
    }

}
