package com.example.moviecatalogue2.Model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.example.moviecatalogue2.Database.DatabaseContract.TvColumns.DESKRIPSI_TV;
import static com.example.moviecatalogue2.Database.DatabaseContract.TvColumns.GAMBAR_TV;
import static com.example.moviecatalogue2.Database.DatabaseContract.TvColumns.JUDUL_TV;
import static com.example.moviecatalogue2.Database.DatabaseContract.TvColumns.RILIS_TV;
import static com.example.moviecatalogue2.Database.DatabaseContract.TvColumns.SKOR_TV;
import static com.example.moviecatalogue2.Database.DatabaseContract.TvColumns.TV_ID;
import static com.example.moviecatalogue2.Database.DatabaseContract.getColumnInt;
import static com.example.moviecatalogue2.Database.DatabaseContract.getColumnString;

public class Tv implements Parcelable {
    private int    id;
    private String movieName;
    private String movieDescription;
    private String movieScore;
    private String movieRelase;
    private String moviePoster;

    public Tv(){

    }

    public Tv(int id, String movieName, String movieDescription, String movieScore, String movieRelase, String moviePoster) {
        this.id = id;
        this.movieName = movieName;
        this.movieDescription = movieDescription;
        this.movieScore = movieScore;
        this.movieRelase = movieRelase;
        this.moviePoster = moviePoster;
    }

    public Tv(Cursor cursor){
        this.id = getColumnInt(cursor, TV_ID);
        this.movieName = getColumnString(cursor, JUDUL_TV);
        this.movieDescription = getColumnString(cursor, DESKRIPSI_TV);
        this.movieScore = getColumnString(cursor, SKOR_TV);
        this.movieRelase = getColumnString(cursor, RILIS_TV);
        this.moviePoster = getColumnString(cursor, GAMBAR_TV);
    }

    public int getId() {
        return id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public void setMovieScore(String movieScore) {
        this.movieScore = movieScore;
    }

    public void setMovieRelase(String movieRelase) {
        this.movieRelase = movieRelase;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public String getMovieScore() {
        return movieScore;
    }

    public String getMovieRelase() {
        return movieRelase;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    protected Tv(Parcel in) {
        id = in.readInt();
        movieName = in.readString();
        movieDescription = in.readString();
        movieScore = in.readString();
        movieRelase = in.readString();
        moviePoster = in.readString();
    }

    public static final Creator<Tv> CREATOR = new Creator<Tv>() {
        @Override
        public Tv createFromParcel(Parcel in) {
            return new Tv(in);
        }

        @Override
        public Tv[] newArray(int size) {
            return new Tv[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(movieName);
        dest.writeString(movieDescription);
        dest.writeString(movieScore);
        dest.writeString(movieRelase);
        dest.writeString(moviePoster);
    }
}
