package com.example.mateusz.facerank;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz & Grzegorz on 2015-05-03.
 */
public class HighScoreList extends ListFragment {

    private static final int REQUEST_CODE = 100;

    OnItemSelectedListener mCallback;
    private List<Photo> myPhotos = new ArrayList<Photo>();
    ListView listView;
    PhotoManager photoManager;

    ArrayAdapter<Photo> connectArrayToListView;
    boolean mDuelPane;
    int mCurCheckPosition = 0;

    private void populatePictureList(){

        myPhotos = photoManager.getPhotos();

    }

    void showPicture(int index){
        mCurCheckPosition = index;
        ZoomFragment zoomFragment = (ZoomFragment)getFragmentManager().findFragmentById(R.id.zoom_fragment);

        if(mDuelPane){
            getListView().setItemChecked(index, true);
            Log.d("Fragmenty", "MyListFragment: In show Picture");

            //put Picture data to ZoomFragment
            //ZoomFragment zoomFragment = (ZoomFragment)getFragmentManager().findFragmentById(R.id.zoom_fragment);
            Log.d("Fragmenty", "MyListFragment: zoom_fragment");

            if(zoomFragment == null || zoomFragment.getShownIndex() != index){
                Log.d("Fragmenty", "MyListFragment: New Instance of ZoomFragment");
                zoomFragment = ZoomFragment.newInstance(index);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.zoom_fragment, zoomFragment);
                Log.d("Fragmenty", "MyListFragment: Replace zoom_fragment");
                fragmentTransaction.commit();
                Log.d("Fragmenty", "MyListFragment: Commit");

            } else {
                Log.d("Fragmenty", "MyListFragment: I am in Portrait mode");
                Intent intent = new Intent();

                intent.setClass(getActivity(), ZoomActivity.class);

                intent.putExtra("index", index);
                startActivityForResult(intent, REQUEST_CODE);

            }
        }else {

            Log.d("Fragmenty", "MyListFragment: I am in Portrait mode");
            Intent intent = new Intent();

            intent.setClass(getActivity(), ZoomActivity.class);

            intent.putExtra("index", index);
            startActivityForResult(intent, REQUEST_CODE);


        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("Fragmenty", "MyListFragment: OnActivityCreated");

        //do the array adapter
        if(myPhotos.size() == 0)
            populatePictureList();

        connectArrayToListView = new MyListAdapter();
        setListAdapter(connectArrayToListView);

        Log.d("Fragmenty", "MyListFragment: SetListAdapter");

        View zoomFragment = getActivity().findViewById(R.id.zoom_fragment);

        mDuelPane = zoomFragment != null && zoomFragment.getVisibility() == View.VISIBLE;

        if(savedInstanceState != null){
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if(mDuelPane){
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showPicture(mCurCheckPosition);
        }

        Log.d("Fragmenty", "MyListFragment: End of ActivityCreated");
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

    private class MyListAdapter extends ArrayAdapter<Photo> {

        public MyListAdapter() {
            super(getActivity().getApplicationContext(), R.layout.item_view, myPhotos);

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
            photoManager.loadPicture(imageView, getContext(), position);
            //TODO: Asynchroniczne ładowanie zdjęć.

            //dodaj opis

            return itemView;
        }


    }
}
