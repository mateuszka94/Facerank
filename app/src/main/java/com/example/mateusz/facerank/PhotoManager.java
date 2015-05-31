package com.example.mateusz.facerank;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Mateusz & Grzegorz on 2015-04-26.
 */
public class PhotoManager {

    private Random random = new Random();
	private ArrayList<PhotoClass> photoClasses;
	private int left;
	private int right;
    public MySQLiteHelper myDatabase;

    public static PhotoManager photoManager;

    public static PhotoManager getInstance(Context context){

        if(photoManager == null) {
            photoManager = new PhotoManager();
            photoManager.myDatabase = new MySQLiteHelper(context);

            photoManager.photoClasses = photoManager.myDatabase.synchronize(); //dodaj tylko te, które nie istnieją :D
        }

        return  photoManager;
    }

    public PhotoClass getPhotoByPosition(int position){

        return photoClasses.get(position);

    }

    public ArrayList<PhotoClass> getPhotoClasses(){
        return photoClasses;
    }

    public void loadPicture(final ImageView imageView, final ProgressBar progressBar, final Context context, final int index, final String type){

        final PhotoClass photoClass = photoClasses.get( index );

        //new WebManager(imageView, progressBar).execute( "https://graph.facebook.com/" + photoClass.getId() + "/picture?type="+type );

        Picasso.with( context ).load( "https://graph.facebook.com/" + photoClass.getId() + "/picture?type="+type ).into( imageView, new Callback() {
            @Override
            public void onSuccess() {
				progressBar.setVisibility( View.INVISIBLE );
            }

            @Override
            public void onError() {
				loadPicture( imageView, progressBar, context, index, type );
            }
        } );



    }

    public void loadPicture( final ImageView imageView, final ProgressBar progressBar, final Context context, final boolean isLeft ) {

		final int index = random.nextInt( photoClasses.size() );
		final PhotoClass photoClass = photoClasses.get( index );

        new WebManager(imageView, progressBar).execute("https://graph.facebook.com/" + photoClass.getId() + "/picture?type=large");

        if( isLeft )
            left = index;
        else
            right = index;

		/*Picasso.with( context ).load( "https://graph.facebook.com/" + photoClass.getId() + "/picture?type=large" )
				.resize( 160, 160 ).into( imageView, new Callback() {
			@Override
			public void onSuccess() {
				Log.d( "Picasso_ID", "" + "Załadowany." );
				imageView.setVisibility( View.VISIBLE );
				progressBar.setVisibility( View.INVISIBLE );
				if( isLeft )
					left = index;
				else
					right = index;
			}

			@Override
			public void onError() {
				Log.d( "Picasso_ID", "" + "Problem: " + index );
				loadPicture( imageView, progressBar, context, isLeft );
			}
		} );*/
	}

    private boolean idExists(String id){

        for(PhotoClass pc : photoClasses)
            if(id.equals(pc.getId()))
                return true;


        return false;

    }

	public void createPhotos( ArrayList< String > ids ) { //how to do it ?!

        for(String id : ids)
            if(!idExists(id))
                photoClasses.add(new PhotoClass(id));


		this.photoClasses = photoClasses;
	}

	public void setWinnerLoser( boolean leftWon ) {

		PhotoClass left = photoClasses.get( this.left );
		PhotoClass right = photoClasses.get( this.right );

        left.setAppearances( left.getAppearances() + 1 );
		right.setAppearances( right.getAppearances() + 1 );

		if( leftWon ){
			left.setVotes( left.getVotes() + 1 );
        }
		else {
			right.setVotes( right.getVotes() + 1 );
        }

        myDatabase.insert(left.getId(), left.getAppearances(), left.getVotes(), left.getRating());
        myDatabase.insert(right.getId(), right.getAppearances(), right.getVotes(), right.getRating());
	}

    public void sortPhotos(){
        Collections.sort(photoClasses);
    }
}