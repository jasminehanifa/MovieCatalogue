package com.example.filmfavorite.Movie;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.filmfavorite.DetailActivity;
import com.example.filmfavorite.Model.Movie;
import com.example.filmfavorite.R;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MoviewViewHolder> {
    private Cursor mListMovies;
    private Context mContext;

    public MovieAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void setListMovies(Cursor mListMovies){
        this.mListMovies = mListMovies;
    }

    @NonNull
    @Override
    public MovieAdapter.MoviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cv_movie, viewGroup, false);
        return new MoviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MoviewViewHolder movieViewHolder, final int i) {
        final Movie movie = getItem(i);
        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w185"+movie.getMoviePoster())
                .apply(new RequestOptions().override(350, 550))
                .into(movieViewHolder.poster);

        movieViewHolder.tvMovieName.setText(movie.getMovieName());
        movieViewHolder.tvMovieRelease.setText(movie.getMovieRelase());

        movieViewHolder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(DetailActivity.PARCEL, getItem(i));
                intent.putExtra("Tv", "Movie");
                mContext.startActivity(intent);
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

}
