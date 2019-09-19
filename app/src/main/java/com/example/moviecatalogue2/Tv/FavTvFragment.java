package com.example.moviecatalogue2.Tv;

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

public class FavTvFragment extends Fragment {
    private Cursor mList;
    private RecyclerView rvMovie;
    private ProgressBar progressBar;
    private FavTvAdapter mAdapter;

    public FavTvFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_fav_tv,container,false);
        progressBar = v.findViewById(R.id.progressBar);
        rvMovie = v.findViewById(R.id.rvFavTv);

        new LoadTvAsync().execute();

        mAdapter = new FavTvAdapter(getContext());
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovie.setHasFixedSize(true);
        mAdapter.setListMovies(mList);
        rvMovie.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadTvAsync().execute();
    }

    private class LoadTvAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(DatabaseContract.CONTENT_URI2,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor notes) {
            super.onPostExecute(notes);
            progressBar.setVisibility(View.GONE);
            try {
                Log.d("kursor", notes.toString());
            }catch (Exception e){
                e.printStackTrace();
            }

            mList = notes;
            mAdapter.setListMovies(mList);
            mAdapter.notifyDataSetChanged();

//            Log.d("wasd", mList.getCount()+"");
            Log.d("wasd", mList+"");


        }
    }
}
