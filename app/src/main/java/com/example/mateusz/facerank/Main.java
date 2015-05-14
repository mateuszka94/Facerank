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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

//TODO: access token; GET... /me;

public class Main extends Activity {
    CallbackManager callbackManager;
    AccessToken accessToken;
	Random random = new Random();
	int[] usersId;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        printHashKey();
        FacebookSdk.sdkInitialize(getApplicationContext());
        Log.d("FacebookSDK", "OnCreate");
        setContentView(R.layout.activity_main);
        Log.d("FacebookSDK", "after activity main");
        Log.d("FacebookSDK", "Zainicjalizowano ");
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = loginResult.getAccessToken();
                Log.d("AccessToken ",loginResult.getAccessToken()+"");
            }

            @Override
            public void onCancel() {
                Log.d("AccessToken ","onCancel");
            }

            @Override
            public void onError(FacebookException e) {
                Log.d("AccessToken ","onError");
            }
        });

        //przykładowe zapytanie wyświetla dane aktualnie zalogowanego użytkownika:
       /* GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback(){
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        Log.d("AccessToken", object.toString());
                       // Log.d("AccessToken", response.toString());
                    }
                }
        );

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();*/


    }

    @Override
    protected void onResume() {
        super.onResume();

       AppEventsLogger.activateApp(this);
        Log.d("FacebookSDK", "Activate app ");
    }

    @Override
    protected void onPause() {
        super.onPause();

       AppEventsLogger.deactivateApp(this);
        Log.d("FacebookSDK", "deactivate app ");
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
		try {
			for( int i = 0; i < 2; i++ ) {
				ImageView imageView;
				URL imageURL = new URL( "http://graph.facebook.com/" + pictureIds[ i ] + "/picture" );
				Bitmap image = BitmapFactory.decodeStream( imageURL.openConnection().getInputStream() );
				if( i == 0 )
					imageView = left;
				else
					imageView = right;
				Picasso.with( this ).load("http://graph.facebook.com/" + pictureIds[ i ] + "/picture").into(imageView);
			}
		} catch( IOException e ) {
			e.printStackTrace();
		}
	}

    public void printHashKey(){
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.mateusz.facerank",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("MyHashKey", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("MyHashKey", "NameNotFound");

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
