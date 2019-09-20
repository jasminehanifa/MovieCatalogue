package com.example.filmfavorite.Tv;

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
import com.example.filmfavorite.Model.Tv;
import com.example.filmfavorite.R;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {
    private Cursor mListMovies;
    private Context mContext;

    public TvAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void setListMovies(Cursor mListMovies){
        this.mListMovies = mListMovies;
    }

    @NonNull
    @Override
    public TvAdapter.TvViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cv_movie, viewGroup, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvAdapter.TvViewHolder tvViewHolder, final int i) {
        final Tv tv = getItem(i);
        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w185"+tv.getMoviePoster())
                .apply(new RequestOptions().override(350, 550))
                .into(tvViewHolder.poster);

        tvViewHolder.tvMovieName.setText(tv.getMovieName());
        tvViewHolder.tvMovieRelease.setText(tv.getMovieRelase());

        tvViewHolder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tvViewHolder.itemView.getContext(), DetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(DetailActivity.PARCEL, getItem(i));
                intent.putExtra("Tv", "Tv");
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

    private Tv getItem(int position){
        if (!mListMovies.moveToPosition(position)){
            throw new IllegalStateException("Position invalid");
        }
        return new Tv(mListMovies);
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
}
