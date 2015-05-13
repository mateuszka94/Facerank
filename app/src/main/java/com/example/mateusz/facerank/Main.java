package com.example.mateusz.facerank;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//TODO: access token; GET... /me;

public class Main extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        printHashKey();
        FacebookSdk.sdkInitialize(getApplicationContext());
        Log.d("FacebookSDK", "OnCreate");
        setContentView(R.layout.activity_main);
        Log.d("FacebookSDK", "after activity main");
        Log.d("FacebookSDK", "Zainicjalizowano ");
        /*callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("AccessToken ",loginResult.getAccessToken()+"");
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });*/
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

    public void OnClick(View view){
        //losuj kolejne zdjęcia i je wyświetl
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
