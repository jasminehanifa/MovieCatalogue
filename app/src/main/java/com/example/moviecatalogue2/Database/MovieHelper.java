package com.example.moviecatalogue2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.moviecatalogue2.Database.DatabaseContract;
import com.example.moviecatalogue2.Database.DatabaseHelper;

import static android.provider.BaseColumns._ID;
import static com.example.moviecatalogue2.Database.DatabaseContract.MovieColumns.MOVIE_ID;


public class MovieHelper {
    private static final String DATABASE_TABLE = DatabaseContract.MovieColumns.TABEL;
    private Context mContext;
    private static DatabaseHelper mDatabaseHelper;
    private static SQLiteDatabase mDatabase;


    public MovieHelper(Context mContext){
        this.mContext = mContext;
    }

    public void open() throws SQLException {
        mDatabaseHelper = new DatabaseHelper(mContext);
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public long insertProvider(ContentValues contentValues){
        return mDatabase.insert(DATABASE_TABLE, null, contentValues);
    }

    public void close(){
        mDatabaseHelper.close();
        if (mDatabase.isOpen())
            mDatabase.close();
    }

    public Cursor queryProvider(){
        return mDatabase.query(DATABASE_TABLE, null,null,null,null,null,_ID+" DESC");
    }

    public Cursor queryByIdProvider(String id){
        return mDatabase.query(DATABASE_TABLE,null,MOVIE_ID+"=?",new String[]{id},null,null,null,null);
    }

    public int updateProvider(String id, ContentValues contentValues){
        return mDatabase.update(DATABASE_TABLE, contentValues,MOVIE_ID+"=?",new String[]{id});
    }

    public int deleteProvider(String id){
        return mDatabase.delete(DATABASE_TABLE,MOVIE_ID+"=?",new String[]{id});
    }






}
