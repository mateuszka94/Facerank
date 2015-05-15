package com.example.mateusz.facerank;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

//TODO: access token; GET... /me;

public class Main extends Activity {
	Random random = new Random();
	int[] usersId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

	private int[] choosePictures() {
		int[] pictureIds = new int[ 2 ];
		int i = random.nextInt( usersId.length );
		pictureIds[ 0 ] = usersId[ i ];
		i = random.nextInt( usersId.length );
		while( i == pictureIds[ 0 ] )
			i = random.nextInt( usersId.length );
		return pictureIds;
	}

    public void OnClick( View view ){
        //losuj kolejne zdjęcia i je wyświetl
		int[] pictureIds = choosePictures();
		ImageView left = ( ImageView ) findViewById( R.id.leftImage );
		ImageView right = ( ImageView ) findViewById( R.id.rightImage );
			for( int i = 0; i < 2; i++ ) {
				ImageView imageView;
				if( i == 0 )
					imageView = left;
				else
					imageView = right;
				Picasso.with( this ).load("http://graph.facebook.com/" + pictureIds[ i ] + "/picture?type=large").into(imageView);
			}
	}
}
