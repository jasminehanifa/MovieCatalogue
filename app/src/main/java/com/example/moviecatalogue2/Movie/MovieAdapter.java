package com.example.moviecatalogue2.Movie;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecatalogue2.Database.DatabaseContract;
import com.example.moviecatalogue2.Detail.DetailMovieActivity;
import com.example.moviecatalogue2.Model.Movie;
import com.example.moviecatalogue2.R;
import com.example.moviecatalogue2.widget.ImageBannerWidget;

import java.util.ArrayList;

import static com.example.moviecatalogue2.Database.DatabaseContract.MovieColumns.DESKRIPSI;
import static com.example.moviecatalogue2.Database.DatabaseContract.MovieColumns.GAMBAR;
import static com.example.moviecatalogue2.Database.DatabaseContract.MovieColumns.JUDUL;
import static com.example.moviecatalogue2.Database.DatabaseContract.MovieColumns.MOVIE_ID;
import static com.example.moviecatalogue2.Database.DatabaseContract.MovieColumns.RILIS;
import static com.example.moviecatalogue2.Database.DatabaseContract.MovieColumns.SKOR;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MoviewViewHolder> {
    private Context context;
    private ArrayList<Movie> listMovie;


    public MovieAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
    }

    @NonNull
    @Override
    public MovieAdapter.MoviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cv_movie, viewGroup, false);
        return new MoviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MoviewViewHolder movieViewHolder, final int i) {
        final Movie movie = listMovie.get(i);
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185"+movie.getMoviePoster())
                .apply(new RequestOptions().override(350, 550))
                .into(movieViewHolder.poster);

        movieViewHolder.tvMovieName.setText(movie.getMovieName());
        movieViewHolder.tvMovieRelease.setText(movie.getMovieRelase());

        movieViewHolder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMovieActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(DetailMovieActivity.PARCEL, listMovie.get(i));
                intent.putExtra("Tv", "Movie");
                context.startActivity(intent);
            }
        });

        movieViewHolder.btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFav(movie);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName thisWidget = new ComponentName(context, ImageBannerWidget.class);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view);
                Toast.makeText(context, "Favorite: "+movie.getMovieName(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class MoviewViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView tvMovieName, tvMovieRelease, detail;
        Button  btn_fav;
        public MoviewViewHolder(@NonNull View itemView) {
            super(itemView);
            poster          = itemView.findViewById(R.id.ivPoster);
            tvMovieName     = itemView.findViewById(R.id.tvMovieName);
            tvMovieRelease  = itemView.findViewById(R.id.tvRelease);
            detail          = itemView.findViewById(R.id.tvDetail);
            btn_fav         = itemView.findViewById(R.id.btn_fav);

        }
    }

    private void addFav(Movie movie){
        int  idMovie = movie.getId();
        String titleMovie = movie.getMovieName();
        String overView = movie.getMovieDescription();
        String releaseDate = movie.getMovieRelase();
        String imagePoster = movie.getMoviePoster();
        String score = movie.getMovieScore();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MOVIE_ID, idMovie);
        contentValues.put(JUDUL, titleMovie);
        contentValues.put(DESKRIPSI, overView);
        contentValues.put(SKOR, score);
        contentValues.put(RILIS, releaseDate);
        contentValues.put(GAMBAR, imagePoster);
        context.getContentResolver().insert(DatabaseContract.CONTENT_URI,contentValues);

    }
}
