package com.example.moviecatalogue2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.moviecatalogue2.Database.DatabaseContract;
import com.example.moviecatalogue2.Database.DatabaseHelper;

import static android.provider.BaseColumns._ID;
import static com.example.moviecatalogue2.Database.DatabaseContract.TvColumns.TV_ID;


public class TvHelper {
    private static  String DATABASE_TABLE = DatabaseContract.TvColumns.TABLE_TVSHOW;

    private Context mContext;
    private static DatabaseHelper databaseHelper;
    private static SQLiteDatabase database;


    public TvHelper(Context mContext){
        this.mContext = mContext;
    }

    public void open() throws SQLException {
        databaseHelper = new DatabaseHelper(mContext);
        database = databaseHelper.getWritableDatabase();
    }

    public long insertProvider(ContentValues values) {
        return database.insert(
                DATABASE_TABLE,
                null,
                values
        );
    }

    public void close(){
        databaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE, null,null,null,null,null,_ID+" DESC");
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(
                DATABASE_TABLE,
                null,
                TV_ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null
        );
    }


    public int updateProvider(String id, ContentValues values) {
        return database.update(
                DATABASE_TABLE,
                values,
                TV_ID + " = ?",
                new String[]{id}
        );
    }

    public int deleteProvider(String id) {
        return database.delete(
                DATABASE_TABLE,
                TV_ID + " = ?",
                new String[]{id}
        );
    }


}
