package com.example.mateusz.facerank;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

/**
 * Created by Mateusz on 2015-05-21.
 */
public class ZoomActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Fragmenty", "ZoomActivity: OnCreate");

        /*ratingBar = (RatingBar)findViewById(R.id.ratingBar2);
        Log.d("Fragmenty", "ZoomActivity: OnCreate "+ ratingBar);

        if(ratingBar != null){
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser){
                    MyListFragment.myPictures.get(getShownIndex()).setRating(rating);
                    setResult(RESULT_OK);
                    finish();
                }
            });
        }*/

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }

        if(savedInstanceState == null){
            ZoomFragment zoomFragment = new ZoomFragment();

            zoomFragment.setArguments(getIntent().getExtras());

            Log.d("Fragmenty", "ZoomActivity: getArguments");

            Log.d("Fragmenty", "ZoomActivity: add fragment "+ findViewById(R.id.fragment_container));
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, zoomFragment).commit();

        }
    }

    public int getShownIndex(){

        return getIntent().getExtras().getInt("index", 0);

    }

}
