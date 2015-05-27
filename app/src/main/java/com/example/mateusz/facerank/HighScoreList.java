package com.example.mateusz.facerank;

import android.content.Context;
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

        Log.d("Fragmenty", "MyListFragment: OnActivityCreated");

        //do the array adapter
        if(myPictures.size() == 0)
            populatePictureList();


        Log.d("Fragmenty", "MyListFragment: populateList");

        connectArrayToListView = new MyListAdapter();
        Log.d("Fragmenty", "MyListFragment: createMyListadapter");
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

    private void populatePictureList(){
        //myPictures.add(PhotoManager.getInstance().getPhotoClasses().get(0)); // =  PhotoManager.getInstance().getPhotoClasses(); nie mogę tak kopiować zdjęć
        //myPictures.add(new PhotoClass("12341515"));
        //Log.d("DebugMain", PhotoManager.getInstance().getPhotoClasses()+"");
        //PhotoManager.getInstance();
        myPictures.addAll(PhotoManager.getInstance().getPhotoClasses());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Fragmenty", "MyListFragment: onSaveInstance");
        outState.putInt("curChoice", mCurCheckPosition);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d("Fragmenty", "MyListFragment: onListItemClick "+ mDuelPane);
        showPicture(position);
        //connectArrayToListView.notifyDataSetChanged();

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
            Log.d("Fragmenty", "MyListFragment: In show Picture");

            //put Picture data to ZoomFragment
            //ZoomFragment zoomFragment = (ZoomFragment)getFragmentManager().findFragmentById(R.id.zoom_fragment);
            Log.d("Fragmenty", "MyListFragment: zoom_fragment");

            if(zoomFragment == null || zoomFragment.getShownIndex() != index){
                Log.d("Fragmenty", "MyListFragment: New Instance of ZoomFragment");
                zoomFragment = ZoomFragment.newInstance(index);

                fragmentTransaction.replace(R.id.zoom_fragment, zoomFragment);
                Log.d("Fragmenty", "MyListFragment: Replace zoom_fragment");
                fragmentTransaction.commit();
                Log.d("Fragmenty", "MyListFragment: Commit");

            } else {
                Log.d("Fragmenty", "MyListFragment: I am in Portrait mode");
                /*Intent intent = new Intent();

                intent.setClass(getActivity(), ZoomActivity.class);

                intent.putExtra("index", index);
                startActivityForResult(intent, REQUEST_CODE);*/

                if(zoomFragment != null || zoomFragment.getShownIndex() != index)
                    zoomFragment = ZoomFragment.newInstance(index);

                Log.d("Fragmenty", "MyListFragment: before replacing 1");
                fragmentTransaction.replace(R.id.container, zoomFragment).commit();

            }
        }else {

            Log.d("Fragmenty", "MyListFragment: I am in Portrait mode");
            //if(zoomFragment != null || zoomFragment.getShownIndex() != index)
                zoomFragment = ZoomFragment.newInstance(index);

            Log.d("Fragmenty", "MyListFragment: before replacing");
            fragmentTransaction.replace(R.id.container, zoomFragment).commit();


        }

    }

    //Override ArrayAdapter, in layout i have a item_list layout and more :D
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
            photoManager.getInstance().loadPictureN( imageView, progressBar, getActivity().getApplicationContext(), position, "normal" ); //problem z ładowaniem zdjęcia

            TextView textView = (TextView)itemView.findViewById(R.id.item_text);
            textView.setText(photoManager.getInstance().getPhotoByPosition(position).getRating()*100+"%");

            return itemView;
        }


    }

}
