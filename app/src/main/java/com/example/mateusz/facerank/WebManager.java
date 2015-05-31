package com.example.mateusz.facerank;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;

/**
 * Created by Mateusz & Grzegorz on 2015-04-26.
 */
public class WebManager extends AsyncTask<String, Void, Bitmap> {

    ImageView bmImage;
    ProgressBar progressBar;

    public WebManager(ImageView bmImage, ProgressBar progressBar) {
        this.bmImage = bmImage;
        this.progressBar = progressBar;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;

    }

    protected void onPostExecute(Bitmap result) {
        //pDlg.dismiss();
        bmImage.setImageBitmap(result);
        progressBar.setVisibility( View.INVISIBLE );
    }
}
