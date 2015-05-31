package com.example.mateusz.facerank;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

//TODO: access token; GET... /me;

public class Main extends Activity {
	private Random random = new Random();
	private ArrayList<String> ids;
	private ArrayList<PhotoClass> photoClasses;

    private ImageView right;
    private ImageView left;
	private ProgressBar rightProgress;
	private ProgressBar leftProgress;

    PhotoManager photoManager;

    @Override
    protected void onStop() {
        super.onStop();
        //update bazy danych
    }

    //MySQLiteHelper myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView( R.layout.activity_main );

        //SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();

		left = ( ImageView ) findViewById( R.id.leftImage );
        right = ( ImageView ) findViewById( R.id.rightImage );
		leftProgress = ( ProgressBar ) findViewById( R.id.leftProgress );
		rightProgress = ( ProgressBar ) findViewById( R.id.rightProgress );

        ids = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.profile_id)));
		Log.d("ImageView Main", "" + findViewById( R.id.leftImage ));


        photoManager = PhotoManager.getInstance(this);
		photoManager.createPhotos( ids );

        if(photoManager.getPhotoClasses() != null)
            Log.d("MultiObject", photoManager.getPhotoClasses().size() + "");

		photoManager.loadPicture( left, leftProgress, getApplicationContext(), true );
		photoManager.loadPicture( right, rightProgress, getApplicationContext(), false );

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void onClick( View view ) {
        //losuj kolejne zdjęcia i je wyświetl

        Log.d("MultiObject", "Synchronized size: "+photoManager.myDatabase.synchronize().size());

		leftProgress.setVisibility( View.VISIBLE );
		rightProgress.setVisibility( View.VISIBLE );
		if( view == left )
			photoManager.setWinnerLoser( true );
		else
			photoManager.setWinnerLoser( false );
        photoManager.loadPicture( left, leftProgress, getApplicationContext(), true );
		photoManager.loadPicture( right, rightProgress, getApplicationContext(), false );
	}

    public void highScore(View view){
        PhotoManager.getInstance(this).sortPhotos();
        Intent intent = new Intent(this, HighScoreActivity.class);
        startActivity(intent);
    }



}
