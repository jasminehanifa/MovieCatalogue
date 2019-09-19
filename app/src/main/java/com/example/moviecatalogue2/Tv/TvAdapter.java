package com.example.moviecatalogue2.Tv;

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
import com.example.moviecatalogue2.Model.Tv;
import com.example.moviecatalogue2.R;

import java.util.ArrayList;


import static com.example.moviecatalogue2.Database.DatabaseContract.TvColumns.DESKRIPSI_TV;
import static com.example.moviecatalogue2.Database.DatabaseContract.TvColumns.GAMBAR_TV;
import static com.example.moviecatalogue2.Database.DatabaseContract.TvColumns.JUDUL_TV;
import static com.example.moviecatalogue2.Database.DatabaseContract.TvColumns.RILIS_TV;
import static com.example.moviecatalogue2.Database.DatabaseContract.TvColumns.SKOR_TV;
import static com.example.moviecatalogue2.Database.DatabaseContract.TvColumns.TV_ID;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {
    private Context context;
    private ArrayList<Tv> listMovie;

    public TvAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Tv> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<Tv> listMovie) {
        this.listMovie = listMovie;
    }
    @NonNull
    @Override
    public TvAdapter.TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_movie, parent, false);
        return new TvAdapter.TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvAdapter.TvViewHolder holder, final int position) {
        final Tv movie = listMovie.get(position);
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185"+movie.getMoviePoster())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.poster);

        holder.tvMovieName.setText(movie.getMovieName());
        holder.tvMovieRelease.setText(movie.getMovieRelase());

        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMovieActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(DetailMovieActivity.PARCEL, listMovie.get(position));
                intent.putExtra("Tv", "Tv");
                context.startActivity(intent);
            }
        });

        holder.btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFav(movie);
                Toast.makeText(context, "Favorite: "+movie.getMovieName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class TvViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView tvMovieName, tvMovieRelease, detail;
        Button btn_fav;
        public TvViewHolder(@NonNull View itemView) {
            super(itemView);
            poster          = itemView.findViewById(R.id.ivPoster);
            tvMovieName     = itemView.findViewById(R.id.tvMovieName);
            tvMovieRelease  = itemView.findViewById(R.id.tvRelease);
            detail          = itemView.findViewById(R.id.tvDetail);
            btn_fav         = itemView.findViewById(R.id.btn_fav);

        }
    }

    private void addFav(Tv tv){
        int idMovie    = tv.getId();
        String titleMovie = tv.getMovieName();
        String overView = tv.getMovieDescription();
        String releaseDate = tv.getMovieRelase();
        String score = tv.getMovieScore();
        String imagePoster = tv.getMoviePoster();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TV_ID, idMovie);
        contentValues.put(JUDUL_TV, titleMovie);
        contentValues.put(DESKRIPSI_TV, overView);
        contentValues.put(SKOR_TV, score);
        contentValues.put(RILIS_TV, releaseDate);
        contentValues.put(GAMBAR_TV, imagePoster);
        context.getContentResolver().insert(DatabaseContract.CONTENT_URI2,contentValues);
    }
}
