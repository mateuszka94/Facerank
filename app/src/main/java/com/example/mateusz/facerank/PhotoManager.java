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
    Random random = new Random();

    public void loadPicture( final ImageView imageView, final ArrayList< String > facebookIDs, final Context context ) {

		int id = random.nextInt( facebookIDs.size() );

		Picasso.with( context ).load( "https://graph.facebook.com/" + id + "/picture?type=large" )
				.resize( 160, 160 ).into( imageView, new Callback() {
			@Override
			public void onSuccess() {
				Log.d( "Picasso_ID", "" + "Załadowany." );
			}

			@Override
			public void onError() {
				Log.d( "Picasso_ID", "" + "Problem." );
				loadPicture( imageView, facebookIDs, context );
			}
		} );
	}

	//public void

	public void createPhotos( ArrayList< Photo > photos, ArrayList< String > ids ) {
		if( !( photos.size() == 0 ) )
			return;
		for( String id : ids )
			photos.add( new Photo( id ) );
	}


}
