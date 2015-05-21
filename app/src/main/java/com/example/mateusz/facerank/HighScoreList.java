package com.example.mateusz.facerank;

import android.app.Activity;
import android.content.Context;
import android.graphics.Picture;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz & Grzegorz on 2015-05-03.
 */
public class HighScoreList extends ListFragment {

    OnItemSelectedListener mCallback;
    private List<Photo> myPhotos = new ArrayList<Photo>();
    ListView listView;
    PhotoManager photoManager;

    private void populatePictureList(){

        myPhotos = photoManager.getPhotos();

    }

    public interface OnItemSelectedListener{
        public void onItemSelected(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("Fragmenty", "ListFragment onCreate");
        super.onCreate(savedInstanceState);
        photoManager = new PhotoManager();

        populatePictureList();
        Log.d("Fragmenty", "populatePictureList");

        setListAdapter(new MyListAdapter());

        Log.d("Fragmenty", "set Adapter");
    }

    @Override
    public void onStart() {
        super.onStart();

        if(getFragmentManager().findFragmentById(R.id.list_fragment) != null){
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            mCallback = (OnItemSelectedListener)activity;

        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement OnItemSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mCallback.onItemSelected(position);

        getListView().setItemChecked(position, true);
    }

    private class MyListAdapter extends ArrayAdapter<Picture> {

        public MyListAdapter() {
            super(getActivity().getApplicationContext(), R.layout.item_view, myPictures);

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

            final Photo currentPhoto = myPhotos.get(position);

            Log.d("GetView", "Pozycja z getView " + position);
            //Log.d("GetView", "Aktualny RatingBar " + );

            //fill the view
            ImageView imageView = (ImageView) itemView.findViewById(R.id.item_image);
            photoManager.loadPicture(imageView, getContext(), currentPhoto.getId());
            //TODO: Asynchroniczne ładowanie zdjęć.

            //dodaj opis

            return itemView;
        }


    }
}
