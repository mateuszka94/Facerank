package com.example.mateusz.facerank;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class HighScoreActivity extends FragmentActivity {

    HighScoreList myListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        if(findViewById(R.id.container) != null){

            if(getSupportFragmentManager().findFragmentByTag("FirstFragment") == null){
                myListFragment = new HighScoreList();
                getSupportFragmentManager().beginTransaction().add(R.id.container, myListFragment, "FirstFragment").commit();
            }

            if(savedInstanceState != null){
                return;
            }

        }

    }

}
