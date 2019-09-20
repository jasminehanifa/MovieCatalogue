package com.example.filmfavorite.Tv;

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

import com.example.filmfavorite.DatabaseContract;
import com.example.filmfavorite.R;
import com.example.filmfavorite.Tv.TvAdapter;

public class TvFragment extends Fragment{
    private Cursor mList;
    private RecyclerView rvMovie;
    private TvAdapter mAdapter;

    public TvFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_tv,container,false);
        rvMovie = v.findViewById(R.id.rvMovie);
        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new TvAdapter(getContext());
        mAdapter.setListMovies(mList);
        rvMovie.setAdapter(mAdapter);

        new LoadMovieAsync().execute();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class LoadMovieAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(DatabaseContract.CONTENT_URI2,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor notes) {
            super.onPostExecute(notes);
            Log.d("kursor", notes.toString());

            mList = notes;
            mAdapter.setListMovies(mList);
            mAdapter.notifyDataSetChanged();

            Log.d("asd", mList.getCount()+"");

        }
    }
}
