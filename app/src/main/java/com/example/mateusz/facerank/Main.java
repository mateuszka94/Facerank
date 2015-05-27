package com.example.mateusz.facerank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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

    PhotoManager photoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView( R.layout.activity_main );

		left = ( ImageView ) findViewById( R.id.leftImage );
        right = ( ImageView ) findViewById( R.id.rightImage );

        ids = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.profile_id)));
		Log.d("ImageView Main", "" + findViewById( R.id.leftImage ));
		photoClasses = new ArrayList<PhotoClass>();
        photoManager = PhotoManager.getInstance();
		photoManager.createPhotos(photoClasses, ids );
		photoManager.loadPicture( left, getApplicationContext(), true );
		photoManager.loadPicture( right, getApplicationContext(), false );
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
		if( view == left )
			photoManager.setWinnerLoser( true );
		else
			photoManager.setWinnerLoser( false );
            photoManager.loadPicture( left, getApplicationContext(), true );
		    photoManager.loadPicture( right, getApplicationContext(), false );
        //TODO:raz na jakiś czas zdarza się wylosowanie tych samych zdjęć (po lewej i prawej stronie)
	}

    public void highScore(View view){
        PhotoManager.getInstance().sortPhotos();
        Intent intent = new Intent(this, HighScoreActivity.class);
        startActivity(intent);
    }

}
