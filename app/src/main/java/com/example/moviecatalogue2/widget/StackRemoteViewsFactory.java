package com.example.moviecatalogue2.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.moviecatalogue2.Database.DatabaseHelper;
import com.example.moviecatalogue2.Model.Movie;
import com.example.moviecatalogue2.R;
import com.example.moviecatalogue2.widget.ImageBannerWidget;

import java.util.ArrayList;
import java.util.List;

import static com.example.moviecatalogue2.Database.DatabaseContract.CONTENT_URI;

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private Cursor cursor;
    private int appWidgetId;



    StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
//        cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onDataSetChanged() {
        if(cursor != null){
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);
        Binder.restoreCallingIdentity(identityToken);

    }

    @Override
    public void onDestroy() {
        if(cursor != null){
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        if(cursor != null){
            return cursor.getCount();
        } else {
            return 0;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Movie movie = movieWidget(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        Bitmap bitmap = null;

        try {
            bitmap  = Glide.with(mContext)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w185" + movie.getMoviePoster())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch(Exception e){
            e.printStackTrace();
        }

        rv.setImageViewBitmap(R.id.imageView, bitmap);


        Bundle extras = new Bundle();
        extras.putInt(ImageBannerWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;

    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private Movie movieWidget(int position) {
        if (!cursor.moveToPosition(position)){
            throw new IllegalStateException("Position invalid!");
        }
        return new Movie(cursor);
    }

}