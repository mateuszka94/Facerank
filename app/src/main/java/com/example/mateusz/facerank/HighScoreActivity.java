package com.example.mateusz.facerank;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class HighScoreActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        if(findViewById(R.id.fragment_container) != null){

            if(getSupportFragmentManager().findFragmentByTag("FirstFragment") == null){
                HighScoreList highScoreList = new HighScoreList();
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, highScoreList, "FirstFragment").commit();
                Log.d("Fragmenty", "MainActivity: ");
            }

            if(savedInstanceState != null){
                Log.d("Fragmenty", "MainActivity: savedOnstanceState not null");
                return;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_high_score, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
