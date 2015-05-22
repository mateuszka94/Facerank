package com.example.mateusz.facerank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;


public class HighScoreActivity extends ActionBarActivity {

    HighScoreList myListFragment;

    private static final int REQUEST_CODE = 100;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            //connectArrayToListView.notifyDataSetChanged();
            //((ArrayAdapter)myListFragment.getListAdapter()).notifyDataSetChanged();
        }
    }

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
