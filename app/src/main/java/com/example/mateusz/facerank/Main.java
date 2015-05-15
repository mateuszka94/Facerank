package com.example.mateusz.facerank;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

//TODO: access token; GET... /me;

public class Main extends Activity {
	Random random = new Random();
	ArrayList<String> ids;
	ArrayList<Photo> photos;

    ImageView right;
    ImageView left;

    PhotoManager photoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView( R.layout.activity_main );

		left = ( ImageView ) findViewById( R.id.leftImage );
        right = ( ImageView ) findViewById( R.id.rightImage );

        ids = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.profile_id)));
		Log.d("ImageView Main", "" + findViewById( R.id.leftImage ));
		photos = new ArrayList< Photo >();
        photoManager = new PhotoManager();
		photoManager.createPhotos( photos, ids );
		photoManager.loadPicture( left, ids, getApplicationContext() );
		photoManager.loadPicture( right, ids, getApplicationContext() );
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void onClick( View view ){
        //losuj kolejne zdjęcia i je wyświetl
        photoManager.loadPicture( left, ids, getApplicationContext() );
		photoManager.loadPicture( right, ids, getApplicationContext() );
	}
}
