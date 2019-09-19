package com.example.moviecatalogue2.Movie;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import com.example.moviecatalogue2.Detail.DetailMovieActivity;
import com.example.moviecatalogue2.Model.Movie;
import com.example.moviecatalogue2.R;
import com.example.moviecatalogue2.widget.ImageBannerWidget;

import static com.example.moviecatalogue2.Database.DatabaseContract.CONTENT_URI;


public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.MovieViewHolder> {

    private Cursor mListMovies;

    private Context mContext;

    public FavMovieAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void setListMovies(Cursor mListMovies){
        this.mListMovies = mListMovies;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView poster;
        TextView tvMovieName, tvMovieRelease, detail;
        Button  btn_fav;

        MovieViewHolder(View itemView) {
            super(itemView);
            poster          = itemView.findViewById(R.id.ivPoster);
            tvMovieName     = itemView.findViewById(R.id.tvMovieName);
            tvMovieRelease  = itemView.findViewById(R.id.tvRelease);
            detail          = itemView.findViewById(R.id.tvDetail);
            btn_fav         = itemView.findViewById(R.id.btn_fav);
        }
    }

    @NonNull
    @Override
    public FavMovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavMovieAdapter.MovieViewHolder holder, final int position) {
        final Movie movie = getItem(position);

        holder.tvMovieName.setText(movie.getMovieName());
        holder.tvMovieRelease.setText(movie.getMovieRelase());
        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w185"+movie.getMoviePoster())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.poster);

        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailMovieActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(DetailMovieActivity.PARCEL, getItem(position));
                intent.putExtra("Tv", "Movie");
                mContext.startActivity(intent);
            }
        });

        holder.btn_fav.setText("UNFAVORITE");

        holder.btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.getContentResolver().delete(
                        Uri.parse(CONTENT_URI + "/" + movie.getId()),
                        null,
                        null
                );
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
                ComponentName thisWidget = new ComponentName(mContext, ImageBannerWidget.class);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view);
                Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mListMovies == null){
            return 0;
        }
        return mListMovies.getCount();
    }

    private Movie getItem(int position){
        if (!mListMovies.moveToPosition(position)){
            throw new IllegalStateException("Position invalid");
        }
        return new Movie(mListMovies);
    }
}
