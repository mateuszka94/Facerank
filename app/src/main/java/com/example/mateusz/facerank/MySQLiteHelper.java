package com.example.mateusz.facerank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Mateusz on 2015-05-30.
 */
public class MySQLiteHelper{

    MySQLiteHelperInner myHelper;

    public MySQLiteHelper(Context context) {
        myHelper = new MySQLiteHelperInner(context);
    }

    public long insert(String url, int appearances, int votes, float rating){

        String[] args = {url};
        SQLiteDatabase db = myHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(myHelper.COLUMN_URL, url);
        contentValues.put(myHelper.COLUMN_APPEARANCES, appearances);
        contentValues.put(myHelper.COLUMN_VOTES, votes);
        contentValues.put(myHelper.COLUMN_RATING, rating);

        long id;

        if(!CheckIsDataAlreadyInDBorNot(myHelper.TABLE_PHOTOS, myHelper.COLUMN_URL, url)){
            id = db.insert(myHelper.TABLE_PHOTOS, null, contentValues);
            Log.d("MultiObject", "insert record");
        }else {
            id = db.update(myHelper.TABLE_PHOTOS, contentValues, myHelper.COLUMN_URL+"=?",args);
            Log.d("MultiObject", "update record");
        }

        //Toast.makeText(myHelper.context, "inserted"+ contentValues.toString(), Toast.LENGTH_LONG).show();

        return id;
    }

    public boolean CheckIsDataAlreadyInDBorNot(String TableName,
                                                      String column, String value) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        String Query = "Select * from " + TableName + " where " + column + " = " + value;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public ArrayList<PhotoClass> synchronize(){

        //update columns (appearances, rating, ...)
        String[] columns = {myHelper.COLUMN_ID, myHelper.COLUMN_URL, myHelper.COLUMN_APPEARANCES, myHelper.COLUMN_VOTES};
        SQLiteDatabase db = myHelper.getWritableDatabase();
        Cursor cursor = db.query(myHelper.TABLE_PHOTOS, columns, null, null, null, null, null);

        ArrayList<PhotoClass> photos = new ArrayList<PhotoClass>();

        while(cursor.moveToNext()){

            PhotoClass photoClass = new PhotoClass(cursor.getString(1));
            photoClass.setAppearances(cursor.getInt(2));
            photoClass.setVotes(cursor.getInt(3));

            photos.add(photoClass);

        }

        Log.d("MultiObject", "Synchronized size: "+photos.size());
        return photos;

    }


    class MySQLiteHelperInner extends SQLiteOpenHelper {
        public static final String TABLE_PHOTOS = "photos";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_APPEARANCES = "appearances";
        public static final String COLUMN_VOTES = "votes";
        public static final String COLUMN_RATING = "rating";

        private static final String DATABASE_NAME = "photos.db";
        private static final int DATABASE_VERSION = 9;
        private static final String DATABASE_CREATE = "CREATE TABLE "+ TABLE_PHOTOS + "( "+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_URL +" TEXT NOT NULL, "+ COLUMN_APPEARANCES + " INTEGER DEFAULT 0, "+ COLUMN_VOTES + " INTEGER DEFAULT 0, "+ COLUMN_RATING +
                " REAL DEFAULT 0"+ ");";

        //CREATE TABLE todo( _id INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT NOT NULL, completed INTEGER DEFAULT 0);

        Context context;



        public MySQLiteHelperInner(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
            Toast.makeText(context,"Database Object Created", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            Log.d("myDatabase", "onCreate");
            db.execSQL(DATABASE_CREATE);
            Toast.makeText(context,"Database Created", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version "+ oldVersion + " to "
                    + newVersion + "which will destroy all old data");

            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_PHOTOS);
            onCreate(db);
        }

    }

}
