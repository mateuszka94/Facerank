package com.example.mateusz.facerank;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mateusz & Grzegorz on 2015-04-26.
 */
public class PhotoManager {
    private Random random = new Random();
	private ArrayList< Photo > photos;
	private int left;
	private int right;

    public void loadPicture( final ImageView imageView, final ArrayList< Photo > photos, final Context context, final boolean isLeft ) {
		//isLeft==true if imageView is left
		//isLeft==right if imageView is right
		final int index = random.nextInt( photos.size() );
		final Photo photo = photos.get( index );
		Picasso.with( context ).load( "https://graph.facebook.com/" + photo.getId() + "/picture?type=large" )
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
				loadPicture( imageView, photos, context, isLeft );
			}
		} );
	}

	public void createPhotos( ArrayList< Photo > photos, ArrayList< String > ids ) {
		if( !( photos.size() == 0 ) )
			return;
		for( String id : ids )
			photos.add( new Photo( id ) );
		this.photos = photos;
	}

	public void setWinnerLoser( boolean leftWon ) {
		Photo left = photos.get( this.left );
		Photo right = photos.get( this.right );
		left.setAppearances( left.getAppearances() + 1 );
		right.setAppearances( right.getAppearances() + 1 );
		if( leftWon )
			left.setVotes( left.getVotes() + 1 );
		else
			right.setVotes( right.getVotes() + 1 );
	}
}