package com.example.mateusz.facerank;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

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

    public static PhotoManager photoManager;

    public static PhotoManager getInstance(){
        Log.d("DebugMain", "singleton");
        if(photoManager == null)
            photoManager = new PhotoManager();

        return  photoManager;
    }

    public ArrayList<PhotoClass> getPhotoClasses(){
        Log.d("DebugMain", photoClasses.toString());
        return photoClasses;
    }

    public void loadPictureN(final ImageView imageView, final Context context, final int index, final String type){
        Log.d( "DebugMain", "loadPicture" );
        final PhotoClass photoClass = photoClasses.get( index );


        Picasso.with( context ).load( "https://graph.facebook.com/" + photoClass.getId() + "/picture?type="+type ).into( imageView, new Callback() {
            @Override
            public void onSuccess() {
                Log.d( "DebugMain", "" + "Załadowany." );
            }

            @Override
            public void onError() {
                Log.d( "Picasso_ID", "" + "Problem: " + index );
				loadPictureN( imageView, context, index, type);
            }
        } );
        Log.d( "DebugMain", "after loadPicture" );


    }

    public void loadPicture( final ImageView imageView, final Context context, final boolean isLeft ) {
		//isLeft==true if imageView is left
		//isLeft==right if imageView is right
		final int index = random.nextInt( photoClasses.size() );
		final PhotoClass photoClass = photoClasses.get( index );
		Picasso.with( context ).load( "https://graph.facebook.com/" + photoClass.getId() + "/picture?type=large" )
				.resize( 160, 160 ).into( imageView, new Callback() {
			@Override
			public void onSuccess() {
				Log.d( "Picasso_ID", "" + "Załadowany." );
				if( isLeft )
					left = index;
				else
					right = index;
			}

			@Override
			public void onError() {
				Log.d( "Picasso_ID", "" + "Problem: " + index );
				loadPicture( imageView, context, isLeft );
			}
		} );
	}

	public void createPhotos( ArrayList<PhotoClass> photoClasses, ArrayList< String > ids ) {
		if( !( photoClasses.size() == 0 ) )
			return;
		for( String id : ids )
			photoClasses.add( new PhotoClass( id ) );
		this.photoClasses = photoClasses;
	}

	public void setWinnerLoser( boolean leftWon ) {
		PhotoClass left = photoClasses.get( this.left );
		PhotoClass right = photoClasses.get( this.right );
		left.setAppearances( left.getAppearances() + 1 );
		right.setAppearances( right.getAppearances() + 1 );
		if( leftWon )
			left.setVotes( left.getVotes() + 1 );
		else
			right.setVotes( right.getVotes() + 1 );
	}

    public void sortPhotos(){

        Collections.sort(photoClasses);
    }
}