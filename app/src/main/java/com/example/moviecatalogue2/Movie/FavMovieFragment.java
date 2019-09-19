package com.example.moviecatalogue2.Movie;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.moviecatalogue2.Database.DatabaseContract;
import com.example.moviecatalogue2.R;
import com.facebook.stetho.Stetho;

public class FavMovieFragment extends Fragment {
    private Cursor mList;
    private RecyclerView rvMovie;
    private ProgressBar progressBar;
    private FavMovieAdapter mAdapter;

    public FavMovieFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_fav_movie,container,false);
        progressBar = v.findViewById(R.id.progressBar);
        rvMovie = v.findViewById(R.id.rvFavMovie);
        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new FavMovieAdapter(getContext());
        mAdapter.setListMovies(mList);
        rvMovie.setAdapter(mAdapter);

        new LoadMovieAsync().execute();

        Stetho.initializeWithDefaults(getContext());


        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadMovieAsync().execute();
    }

    private class LoadMovieAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(DatabaseContract.CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor notes) {
            super.onPostExecute(notes);
            progressBar.setVisibility(View.GONE);
            Log.d("kursor", notes.toString());

            mList = notes;
            mAdapter.setListMovies(mList);
            mAdapter.notifyDataSetChanged();

            Log.d("asd", mList.getCount()+"");

        }
    }
}
