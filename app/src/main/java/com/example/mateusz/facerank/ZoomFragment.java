package com.example.mateusz.facerank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Mateusz & Grzegorz on 2015-05-03.
 */
public class ZoomFragment extends Fragment {

    ImageView imageView;
    TextView textView;


    public static ZoomFragment newInstance(int index){

        ZoomFragment zoomFragment = new ZoomFragment();

        Bundle args = new Bundle();
        args.putInt("index", index);

        zoomFragment.setArguments(args);
        Log.d("Fragmenty", "ZoomFragment: Return from NewInstance");
        return zoomFragment;
    }

    public int getShownIndex(){

        return getArguments().getInt("index", 0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d("Fragmenty", "ZoomFragment: onCreateView");
        //dynamically or using XML ?
        View view =  inflater.inflate(R.layout.zoom_layout, container, false);
        Log.d("Fragmenty", "ZoomFragment: position" + getShownIndex());
        //ZoomFragment should contain myPicture list!
        /*
        * ImageView = findViewById
        * setImage() by Uri from myPicture.get(getShownIndex())
        * */

        imageView = (ImageView)view.findViewById(R.id.imageView);
        PhotoManager.getInstance().loadPictureN(imageView, getActivity(), getShownIndex(), "large");
        imageView = (ImageView) view.findViewById(R.id.imageView);
		
        return view; //using XML


    }
}
