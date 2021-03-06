package com.example.mateusz.facerank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by Mateusz & Grzegorz on 2015-05-03.
 */
public class ZoomFragment extends Fragment {

    ImageView imageView;
    PhotoManager photoManager;


    public static ZoomFragment newInstance(int index){

        ZoomFragment zoomFragment = new ZoomFragment();

        Bundle args = new Bundle();
        args.putInt("index", index);

        zoomFragment.setArguments(args);
        return zoomFragment;
    }

    public int getShownIndex(){

        return getArguments().getInt("index", 0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //dynamically or using XML ?
        View view =  inflater.inflate(R.layout.zoom_layout, container, false);

        imageView = (ImageView) view.findViewById(R.id.imageView);
		ProgressBar progressBar = ( ProgressBar ) view.findViewById( R.id.progress );
        photoManager.getInstance(getActivity().getApplicationContext()).loadPicture( imageView, progressBar, getActivity(), getShownIndex(), "large" );

        return view;


    }
}
