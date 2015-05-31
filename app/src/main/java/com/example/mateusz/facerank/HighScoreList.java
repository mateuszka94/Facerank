package com.example.mateusz.facerank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mateusz & Grzegorz on 2015-05-03.
 */
public class HighScoreList extends ListFragment {
    private static final int REQUEST_CODE = 100;
    boolean mDuelPane;
    int mCurCheckPosition = 0;
    ArrayAdapter<PhotoClass> connectArrayToListView;
    PhotoManager photoManager;

    private ArrayList<PhotoClass> myPictures = new ArrayList<PhotoClass>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(myPictures.size() == 0)
            populatePictureList();

        connectArrayToListView = new MyListAdapter();
        setListAdapter(connectArrayToListView);

        View zoomFragment = getActivity().findViewById(R.id.zoom_fragment);

        mDuelPane = zoomFragment != null && zoomFragment.getVisibility() == View.VISIBLE;

        if(savedInstanceState != null){
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if(mDuelPane){
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showPicture(mCurCheckPosition);
        }
    }

    private void populatePictureList(){
        myPictures.addAll(PhotoManager.getInstance(getActivity().getApplicationContext()).getPhotoClasses());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showPicture(position);
    }

    @Override
    public void onStart() {
        super.onStart();
        connectArrayToListView.notifyDataSetChanged();
    }

    void showPicture(int index){
        mCurCheckPosition = index;
        ZoomFragment zoomFragment = (ZoomFragment)getFragmentManager().findFragmentById(R.id.zoom_fragment);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        if(mDuelPane){
            getListView().setItemChecked(index, true);

            if(zoomFragment == null || zoomFragment.getShownIndex() != index){

                zoomFragment = ZoomFragment.newInstance(index);

                fragmentTransaction.replace(R.id.zoom_fragment, zoomFragment);
                fragmentTransaction.commit();

            } else {
                fragmentTransaction.replace(R.id.zoom_fragment, zoomFragment);

            }
        }else {

            Intent intent = new Intent(getActivity().getApplicationContext(), ZoomActivity.class);
            intent.putExtra("index", index);
            getActivity().startActivity(intent);

        }

    }

    private class MyListAdapter extends ArrayAdapter<PhotoClass> {

        public MyListAdapter() {
            super(getActivity().getApplicationContext(), R.layout.item_view, myPictures);
            Log.d("DebugMain", "Constructor of MyListAdapter");

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Log.d("DebugMain", "Wykonuję getView");
            //make sure that we have a View to work with
            View itemView = convertView;

            if (itemView == null) {
                //itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                itemView = layoutInflater.inflate(R.layout.item_view, parent, false);

            }

            final PhotoClass currentPicture = myPictures.get(position);


            Log.d("DebugMain", "Pozycja z getView " + position);
            //Log.d("GetView", "Aktualny RatingBar " + );

            //fill the view
            ImageView imageView = (ImageView) itemView.findViewById(R.id.item_image);
			ProgressBar progressBar = ( ProgressBar ) itemView.findViewById( R.id.progress );
			progressBar.setVisibility( View.VISIBLE );
            photoManager.getInstance(getContext()).loadPicture(imageView, progressBar, getActivity().getApplicationContext(), position, "normal"); //problem z ładowaniem zdjęcia

            TextView textView = (TextView)itemView.findViewById(R.id.item_text);
            textView.setText(photoManager.getInstance(getContext()).getPhotoByPosition(position).getRating()*100+"%");

            return itemView;
        }


    }

}
