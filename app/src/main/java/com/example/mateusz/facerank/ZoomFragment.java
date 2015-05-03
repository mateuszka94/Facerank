package com.example.mateusz.facerank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Mateusz on 2015-05-03.
 */
public class ZoomFragment extends Fragment {
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        return inflater.inflate(R.layout.zoom_layout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();

        if(args != null){
            //tu będą moje argumenty:
            //pozycja, rating, ID|Uri, opis
        }
    }

    public void updateZoomView(int position){ //tu będą przekazywane wszystkie argumenty
        //UPDATE
    }

    @Override //in the future
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
