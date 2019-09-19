package com.example.moviecatalogue2.Model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.example.moviecatalogue2.Database.DatabaseContract.MovieColumns.DESKRIPSI;
import static com.example.moviecatalogue2.Database.DatabaseContract.MovieColumns.GAMBAR;
import static com.example.moviecatalogue2.Database.DatabaseContract.MovieColumns.JUDUL;
import static com.example.moviecatalogue2.Database.DatabaseContract.MovieColumns.MOVIE_ID;
import static com.example.moviecatalogue2.Database.DatabaseContract.MovieColumns.RILIS;
import static com.example.moviecatalogue2.Database.DatabaseContract.MovieColumns.SKOR;
import static com.example.moviecatalogue2.Database.DatabaseContract.getColumnInt;
import static com.example.moviecatalogue2.Database.DatabaseContract.getColumnString;

public class Movie implements Parcelable {
    private int    id;
    private String movieName;
    private String movieDescription;
    private String movieScore;
    private String movieRelase;
    private String moviePoster;


    public Movie(int id, String movieName, String movieDescription, String movieScore, String movieRelase, String moviePoster) {
        this.id = id;
        this.movieName = movieName;
        this.movieDescription = movieDescription;
        this.movieScore = movieScore;
        this.movieRelase = movieRelase;
        this.moviePoster = moviePoster;
    }

    public Movie(Cursor cursor){
        this.id = getColumnInt(cursor, MOVIE_ID);
        this.movieName = getColumnString(cursor, JUDUL);
        this.movieDescription = getColumnString(cursor, DESKRIPSI);
        this.movieScore = getColumnString(cursor, SKOR);
        this.movieRelase = getColumnString(cursor, RILIS);
        this.moviePoster = getColumnString(cursor, GAMBAR);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public String getMovieScore() {
        return movieScore;
    }

    public void setMovieScore(String movieScore) {
        this.movieScore = movieScore;
    }

    public String getMovieRelase() {
        return movieRelase;
    }

    public void setMovieRelase(String movieRelase) {
        this.movieRelase = movieRelase;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.movieName);
        dest.writeString(this.movieDescription);
        dest.writeString(this.movieScore);
        dest.writeString(this.movieRelase);
        dest.writeString(this.moviePoster);
    }

    public Movie() {

    }

    protected Movie(Parcel in) {
        this.movieName = in.readString();
        this.movieDescription = in.readString();
        this.movieScore = in.readString();
        this.movieRelase = in.readString();
        this.moviePoster = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
