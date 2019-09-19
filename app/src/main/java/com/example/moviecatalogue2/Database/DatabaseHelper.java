package com.example.moviecatalogue2.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.moviecatalogue2.Database.DatabaseContract;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbmovie";
    private static final int DATABASE_VERSION = 10;

    private static final String SQL_CREATE_TABEL = String.format("CREATE TABLE %s"+
            "(%s TEXT,"+
                    "%s TEXT PRIMARY KEY ,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL)",
            DatabaseContract.MovieColumns.TABEL,
            DatabaseContract.MovieColumns._ID,
            DatabaseContract.MovieColumns.MOVIE_ID,
            DatabaseContract.MovieColumns.JUDUL,
            DatabaseContract.MovieColumns.DESKRIPSI,
            DatabaseContract.MovieColumns.SKOR,
            DatabaseContract.MovieColumns.RILIS,
            DatabaseContract.MovieColumns.GAMBAR
    );

    private static final String SQL_CREATE_TABLE_TV = String.format("CREATE TABLE %s"+
                    "(%s TEXT,"+
                    "%s TEXT PRIMARY KEY,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL)",
            DatabaseContract.TvColumns.TABLE_TVSHOW,
            DatabaseContract.TvColumns._ID,
            DatabaseContract.TvColumns.TV_ID,
            DatabaseContract.TvColumns.JUDUL_TV,
            DatabaseContract.TvColumns.DESKRIPSI_TV,
            DatabaseContract.TvColumns.SKOR_TV,
            DatabaseContract.TvColumns.RILIS_TV,
            DatabaseContract.TvColumns.GAMBAR_TV
    );

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABEL);
        db.execSQL(SQL_CREATE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DatabaseContract.MovieColumns.TABEL);
        db.execSQL("DROP TABLE IF EXISTS "+ DatabaseContract.TvColumns.TABLE_TVSHOW);
        onCreate(db);
    }

}
