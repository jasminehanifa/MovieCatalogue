package com.example.moviecatalogue2.Database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.moviecatalogue2.Database.DatabaseContract;
import com.example.moviecatalogue2.Database.MovieHelper;
import com.example.moviecatalogue2.Database.TvHelper;

import java.util.Objects;

import static com.example.moviecatalogue2.Database.DatabaseContract.AUTHORITY;

public class MovieProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final int TV = 3;
    private static final int TV_ID = 4;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, DatabaseContract.MovieColumns.TABEL, MOVIE);
        URI_MATCHER.addURI(AUTHORITY, DatabaseContract.MovieColumns.TABEL+"/#",MOVIE_ID);

        URI_MATCHER.addURI(AUTHORITY, DatabaseContract.TvColumns.TABLE_TVSHOW, TV);
        URI_MATCHER.addURI(AUTHORITY, DatabaseContract.TvColumns.TABLE_TVSHOW+"/#",TV_ID);

    }

    private MovieHelper movieHelper;
    private TvHelper tvHelper;

    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        tvHelper = new TvHelper(getContext());
        movieHelper.open();
        tvHelper.open();

        return true;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int updated;
        switch (URI_MATCHER.match(uri)){
            case MOVIE_ID:

                updated = movieHelper.updateProvider(uri.getLastPathSegment(),values);

                break;
            case TV_ID:
                updated = tvHelper.updateProvider(uri.getLastPathSegment(), values);
                break;
            default:

                updated = 0;

                break;
        }
        if (updated>0){

            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (URI_MATCHER.match(uri)){
            case MOVIE:

                cursor = movieHelper.queryProvider();

                break;
            case MOVIE_ID:

                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());

                break;
            case TV:
                cursor = tvHelper.queryProvider();
                break;

            case TV_ID:
                cursor = tvHelper.queryByIdProvider(uri.getLastPathSegment());
                break;

            default:

                cursor = null;

                break;
        }
        if (cursor!=null){

            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long added;
        switch (URI_MATCHER.match(uri)){
            case MOVIE:
                added = movieHelper.insertProvider(values);
                break;
            case TV:
                added = tvHelper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }
        if (added>0){
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(DatabaseContract.CONTENT_URI2 +"/"+added);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        switch (URI_MATCHER.match(uri)){
            case MOVIE_ID:
                deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            case TV_ID:
                deleted = tvHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        if (deleted>0){
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

}
