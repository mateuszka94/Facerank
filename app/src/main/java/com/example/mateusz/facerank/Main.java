package com.example.mateusz.facerank;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

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

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//TODO: access token; GET... /me;

public class Main extends Activity {
    CallbackManager callbackManager;
    AccessToken accessToken;

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
