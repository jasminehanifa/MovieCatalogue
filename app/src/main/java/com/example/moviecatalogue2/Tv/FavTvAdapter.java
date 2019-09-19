package com.example.moviecatalogue2.Tv;

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
import com.example.moviecatalogue2.Model.Tv;
import com.example.moviecatalogue2.R;

import static com.example.moviecatalogue2.Database.DatabaseContract.CONTENT_URI2;

public class FavTvAdapter extends RecyclerView.Adapter<FavTvAdapter.FavTvViewHolder> {
    private Cursor mListMovies;
    private Context mContext;

    public FavTvAdapter(Context mContext){
        this.mContext = mContext;
    }




    public void setListMovies(Cursor mListMovies){
        this.mListMovies = mListMovies;
    }

    class FavTvViewHolder extends RecyclerView.ViewHolder {

        ImageView poster;
        TextView tvMovieName, tvMovieRelease, detail;
        Button btn_fav;

        FavTvViewHolder(View itemView) {
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
    public FavTvAdapter.FavTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_movie, parent, false);
        return new FavTvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavTvAdapter.FavTvViewHolder holder, final int position) {
        final Tv tv = getItem(position);

        holder.tvMovieName.setText(tv.getMovieName());
        holder.tvMovieRelease.setText(tv.getMovieRelase());

        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w185"+tv.getMoviePoster())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.poster);

        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailMovieActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(DetailMovieActivity.PARCEL, getItem(position));
                intent.putExtra("Tv", "Tv");
                mContext.startActivity(intent);
            }
        });

        holder.btn_fav.setText("UNFAVORITE");

        holder.btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.getContentResolver().delete(
                        Uri.parse(CONTENT_URI2 + "/" + tv.getId()),
                        null,
                        null
                );
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

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private Tv getItem(int position){
        if (!mListMovies.moveToPosition(position)){
            throw new IllegalStateException("Position invalid");
        }
        return new Tv(mListMovies);
    }



}
