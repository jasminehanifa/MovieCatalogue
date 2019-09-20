package com.example.filmfavorite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.filmfavorite.Model.Movie;
import com.example.filmfavorite.Model.Tv;

public class DetailActivity extends AppCompatActivity {
    public static final String PARCEL = "parcel_movie";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent      intent              = getIntent();
        String      getExtraTv          = intent.getStringExtra("Tv");
        TextView    tvName              = findViewById(R.id.tvNameDetail);
        TextView    tvScore             = findViewById(R.id.tvScoreDetail);
        TextView    tvRelease           = findViewById(R.id.tvReleaseDetail);
        TextView    tvDescription       = findViewById(R.id.tvDescription);
        ImageView   ivPosterDetail      = findViewById(R.id.ivDetailMovie);

        if (getExtraTv.equals("Tv")){
            Tv tv                  = getIntent().getParcelableExtra(PARCEL);
            String      namaTv              = tv.getMovieName();
            String      scoreTv             = tv.getMovieScore();
            String      releaseTv           = tv.getMovieRelase();
            String      descTv              = tv.getMovieDescription();
            String      posterTv            = "https://image.tmdb.org/t/p/w185"+tv.getMoviePoster();

            tvName.setText(namaTv);
            tvScore.setText(scoreTv);
            tvRelease.setText(releaseTv);
            tvDescription.setText(descTv);
            Glide.with(this)
                    .load(posterTv)
                    .apply(new RequestOptions().override(350, 550))
                    .into(ivPosterDetail);
        }

        else if (getExtraTv.equals("Movie")){

            Movie movie               = getIntent().getParcelableExtra(PARCEL);
            String      name                = movie.getMovieName();
            String      score               = movie.getMovieScore();
            String      release             = movie.getMovieRelase();
            String      description         = movie.getMovieDescription();
            String      poster              = "https://image.tmdb.org/t/p/w185"+movie.getMoviePoster();

            tvName.setText(name);
            tvScore.setText(score);
            tvRelease.setText(release);
            tvDescription.setText(description);
            Glide.with(this)
                    .load(poster)
                    .apply(new RequestOptions().override(350, 550))
                    .into(ivPosterDetail);
        }

    }
}
