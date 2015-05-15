package com.example.mateusz.facerank;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mateusz & Grzegorz on 2015-04-26.
 */
public class PhotoManager {
    Random random = new Random();

    public void choosePictures(ImageView leftView, ImageView rightView, ArrayList<String> facebookIDs, Context context) {

        int leftId = random.nextInt(facebookIDs.size());
        int rightId = random.nextInt(facebookIDs.size());

        Picasso.with(context).load("http://graph.facebook.com/" + leftId + "/picture?type=large").into(leftView);
        Picasso.with(context).load("http://graph.facebook.com/" + rightId + "/picture?type=large").into(rightView);
    }

	public void createPhotos( ArrayList< Photo > photos, ArrayList< String > ids ) {
		if( !( photos.size() == 0 ) )
			return;
		for( String id : ids )
			photos.add( new Photo( id ) );
	}


}
