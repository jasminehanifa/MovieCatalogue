package com.example.filmfavorite;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.example.moviecatalogue2";


    public static final class MovieColumns implements BaseColumns {
        public static final String TABEL = "movie";
        public static String MOVIE_ID = "movie_id";
        public static String JUDUL = "title";
        public static String DESKRIPSI = "overview";
        public static String SKOR = "score";
        public static String RILIS = "release_date";
        public static String GAMBAR = "image_poster";
    }

    public static final class TvColumns implements BaseColumns {
        public static final String TABLE_TVSHOW = "tvshow";
        public static String TV_ID = "tv_id";
        public static String JUDUL_TV = "title_tv";
        public static String DESKRIPSI_TV = "overview_tv";
        public static String SKOR_TV = "score_tv";
        public static String RILIS_TV = "release_date_tv";
        public static String GAMBAR_TV = "image_poster_tv";
    }

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(MovieColumns.TABEL)
            .build();

    public static final Uri CONTENT_URI2 = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TvColumns.TABLE_TVSHOW)
            .build();

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

}
