package com.example.mateusz.facerank;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;


public class HighScoreActivity extends FragmentActivity {

    HighScoreList myListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Fragmenty", "MainActivity: OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        Log.d("Fragmenty", "MainActivity: setContent");

        if(findViewById(R.id.container) != null){

            if(getSupportFragmentManager().findFragmentByTag("FirstFragment") == null){
                myListFragment = new HighScoreList();
                getSupportFragmentManager().beginTransaction().add(R.id.container, myListFragment, "FirstFragment").commit();
                Log.d("Fragmenty", "MainActivity: Create myListFragment");
            }

            if(savedInstanceState != null){
                Log.d("Fragmenty", "MainActivity: savedOnstanceState not null");
                return;
            }

        }

        /*if(findViewById(R.id.zoom_fragment) == null || (findViewById(R.id.zoom_fragment).getVisibility() != View.VISIBLE)){
            if(findViewById(R.id.container) != null){
            myListFragment = new MyListFragment();

            getSupportFragmentManager().beginTransaction().add(R.id.container, myListFragment).commit();
            Log.d("Fragmenty", "set first fragment");
            }
        }*/

    }

}
